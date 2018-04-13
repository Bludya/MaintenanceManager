$(() => {
  const app = Sammy('body', function (){
    this.use('Handlebars', 'hbs');

    Handlebars.registerHelper('if_equal', function(a, b, opts) {
      if (a == b) {
          return opts.fn(this)
      } else {
          return opts.inverse(this)
      }
    });

    Handlebars.registerHelper('ifContains', function(a, b, opts) {
      if (a == null) {
        return opts.inverse(this)
      }

      if(!a.includes(b)){
        return opts.inverse(this)
      }

      return opts.fn(this)
    });


    this.get('index.html', function (ctx){
      let url = '/welcome/welcome';

      if(sessionStorage.loggedIn){
        ctx.redirect('#/profile')
        return;
      }

      loadPage(ctx, url);
    })

    this.get('#/register', function (ctx){
      loadPage(ctx, '/users/register')
    })

    this.get('#/login', function (ctx){

      if(sessionStorage.loggedIn){
        ctx.redirect("#/profile");
        return;
      }

      loadPage(ctx, '/users/login');
    })

    this.post('#/register', function (ctx){
      let {username, email, password, repeatPassword} = this.params;
      let english = /^[A-Za-z]*$/;
      let englishAndNumbers = /^[A-Za-z0-9]*$/;
      let emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/

      if(!english.test(username)){
        showError('Username should be english alphabet only.')
        return;
      }

      if(username.length < 2){
        showError('Username should be at least 2 characters long.')
        return;
      }

      if(!emailRegex.test(email)){
        showError('Email is invalid.')
        return;
      }

      if(password.length < 3){
        showError('Password should be at least 3 characters long.')
        return;
      }

      if(password !== repeatPassword){
        showError('Passwords do not match.')
        return;
      }

      auth.register(
        username,
        email,
        password,
        repeatPassword,
        function thenFunc(data) {
          ctx.redirect('#/login');
        })
    })

    this.post('#/login', function (ctx){
      let {username, password} = this.params;
      let englishAndNumbers = /^[A-Za-z0-9]*$/;

      if(sessionStorage.loggedIn){
        ctx.redirect("#/profile");
        return;
      }

      if(!englishAndNumbers.test(username) || username.length < 1){
        showError('Email is invalid.')
        return;
      }

      if(!englishAndNumbers.test(password) || password.length < 1){
        showError('Password is invalid.')
        return;
      }

      auth.login(
        username,
        password,
        function (data){
          auth.saveSession(data);
          ctx.redirect("#/profile");
        },
        function(xfr, error){
            showError('Invalid username or password.')
        }
      )
    })

    this.get('#/profile', function (ctx) {

      if(!checkAccessible(ctx)){
        ctx.redirect("#/")
      }

      requester.get(
        "/users/profile",
        function(user){
          loadPage(ctx, '/users/profile')
        }
    );
    })

    this.get('#/logout', function (ctx){
        auth.logout();
        ctx.redirect('#');
    })

    this.get('#/users', function (ctx){
      if(!checkAccessible(ctx, "ADMIN")){
        return;
      }

      requester.get(
        '/users/all',
        function thenFunc(users){
          for(let i = 0; i < users.length; i++){
            let user = users[i];
            user.rank = i+1;
            user.isNotMe = user.id !== sessionStorage.getItem('user_id');
          }

          ctx.users = users;
          loadPage(ctx, '/users/all')
        }
      )
    })

    this.post('#/users', function (ctx){
      if(!checkAccessible(ctx, "ADMIN")){
        return;
      }

      let {searchWord} = this.params;

      let url = '/users/find/';

      if(searchWord == ""){
        url = "/users/all";
      }

      requester.get(
        url + searchWord,
        function thenFUnc(users){
          console.log("efwefwef");
          for(let i = 0; i < users.length; i++){
            let user = users[i];
            user.rank = i+1;
            user.isNotMe = user.id !== sessionStorage.getItem('user_id');
          }

          ctx.users = users;
          loadPage(ctx, '/users/all')
        },
        function (){
          console.log("ewfweg");
        }
      )
    })

    this.get('#/users/activate/:username', function (ctx){
      if(!checkAccessible(ctx, "ADMIN")){
        return;
      }
      let username = ctx.params.username;
      requester.get(
        '/users/activate/' + username,
        function thenFunc(data){
          let row =  $('#'+username);
          let link =row.find('#activate');
          let active = row.find('#active');
          link.attr("href", "#/users/deactivate/" + username);
          link.text('Deactivate');
          link.attr("id", "deactivate");
          active.text('true');
          //ctx.redirect('#/users');
        }
      )
    })

    this.get('#/users/deactivate/:username', function (ctx){
      if(!checkAccessible(ctx, "ADMIN")){
        return;
      }
      let username = ctx.params.username

      requester.get(
        '/users/deactivate/' + username,
        function (data){
          let row =  $('#'+username);
          let link =row.find('#deactivate');
          let active = row.find('#active');
          link.attr("href", "#/users/activate/" + username);
          link.text('Activate');
          link.attr("id", "activate");
          active.text('false');
          // ctx.redirect('#/users');
        })
    })

    this.get('#/users/delete/:username', function (ctx){
      if(!checkAccessible(ctx, "ADMIN")){
        return;
      }
      let username = ctx.params.username;
      requester.get(
        '/users/delete/' + username,
        function (data){
          $('#'+username).remove();
          // ctx.redirect('#/users');
        })
    })

    this.get('#/logs', function (ctx){

      let page = ctx.params.page;
      if(!page){
        page = 0;
      }
      page -= 1;

      let link = "?page=" + page + "&size=10";

      requester.get(
        '/logs/all' + link,
        function thenFunc(data){
          let pageNumber = data['number'] + 1;
          let totalPages = data['totalPages'];
          let notFirstPage = pageNumber != 1;
          let notLastPage = pageNumber != totalPages;

          if(!notFirstPage){
            ctx.first = 1;
            ctx.second = 2;
            ctx.third = 3;
          }else if (!notLastPage) {
            ctx.first = pageNumber-2;
            ctx.second = pageNumber-1;
            ctx.third = pageNumber;
          }else {
            ctx.first = pageNumber-1;
            ctx.second = pageNumber;
            ctx.third = pageNumber+1;
          }

          ctx.prevPage = pageNumber - 1;
          ctx.nextPage = pageNumber + 1;
          ctx.notFirstPage = notFirstPage;
          ctx.notLastPage = notLastPage;
          ctx.logs = data['content'];

          loadPage(ctx, '/logs/logs', {pagination: './templates/common/pagination.hbs'});
        }
      )
    })

    this.get('#/projects', function(ctx){
      let projectId = ctx.params.projectId;
      console.log(projectId);
      if(!projectId){
        requester.get(
          '/projects/all',
          function thenFUnc(projects){
            console.log(projects);
            ctx.projects = projects;
            loadPage(ctx, '/projects/showProjects', {projectPreview: './templates/projects/projectPreview.hbs'});
          }
        )
      }else {
        requester.get(
          '/projects?project=' + projectId,
          function thenFUnc(project){
            console.log(project);
            ctx.project = project;
            loadPage(ctx, '/projects/projectInfo');
          }
        )
      }
    })

  });
  app.run();
});
