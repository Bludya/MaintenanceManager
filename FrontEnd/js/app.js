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

    this.get('index.html', function (ctx){
      loadPage(ctx, './templates/welcome/welcome.hbs');
    })

    this.get('#/register', function (ctx){
      loadPage(ctx, './templates/users/register_form.hbs')
    })

    this.get('#/login', function (ctx){
      loadPage(ctx, './templates/users/login_form.hbs');
    })

    this.post('#/register', function (ctx){
      let {username, email, password, repeatPassword} = this.params;
      let english = /^[A-Za-z]*$/;
      let englishAndNumbers = /^[A-Za-z0-9]*$/;
      let emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/

      if(!english.test(username)){
        auth.showError('Username should be english alphabet only.')
        return;
      }

      if(username.length < 3){
        auth.showError('Username should be at least 3 characters long.')
        return;
      }

      if(!englishAndNumbers.test(password)){
        auth.showError('Password should be only english alphabet and letters.')
        return;
      }

      if(!emailRegex.test(email)){
        auth.showError('Email is invalid.')
        return;
      }

      if(password.length < 6){
        auth.showError('Password should be at least 6 characters long.')
        return;
      }

      if(password !== repeatPassword){
        auth.showError('Passwords do not match.')
        return;
      }


      auth.register(
        username,
        email,
        password,
        repeatPassword,
        function thenFunc(data) {
          ctx.redirect('#/login');
        });
    })

    this.post('#/login', function (ctx){
      let {email, password,remember} = this.params;
      let emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      let englishAndNumbers = /^[A-Za-z0-9]*$/;

      if(!emailRegex.test(email)){
        auth.showError('Email is invalid.')
        return;
      }

      if(!englishAndNumbers.test(password) || password.length < 6){
        auth.showError('Password is invalid.')
        return;
      }
      auth.saveSession("9f40a532-6834-46d1-a979-5131d6c7dd9d", "valio", "ADMIN");
      ctx.redirect("#/profile");
      // auth.login(email, password, remember)
      //   .always(function (data){
      //
      //   })
    })

    this.get('#/profile', function (ctx) {
      requester.post(
        "users",
        "profile",
        {"id": sessionStorage.user_id},
        function(user){
          loadPage(ctx, './templates/users/profile.hbs')
        }
      );
    })

    this.get('#/logout', function (ctx){
      auth.logout(
        function thenFunc(data){
          ctx.redirect('#');
          setTimeout(() => auth.showInfo("Logout successful!"), 500);
        },
        function failFInc(){
          ctx.redirect('#');
        }
      );
    })

    this.get('#/users', function (ctx){
      requester.post(
        'users',
        'all',
        "",
        function thenFunc(users){
          for(let i = 0; i < users.length; i++){
            let user = users[i];
            user.rank = i+1;
            user.isNotMe = user.id !== sessionStorage.getItem('user_id');
          }

          ctx.users = users;
          loadPage(ctx, './templates/users/all.hbs')
        }
      )
    })

    this.post('#/users', function (ctx){
      let {searchWord} = this.params;
      requester.post(
        'users',
        'all',
        {'searchWord': searchWord},
        function thenFUnc(users){

          for(let i = 0; i < users.length; i++){
            let user = users[i];
            user.rank = i+1;
            user.isNotMe = user.id !== sessionStorage.getItem('user_id');
          }

          ctx.users = users;
          loadPage(ctx, './templates/users/all.hbs')
        }
      )
    })

    this.get('#/users/activate/:id', function (ctx){
      requester.post(
        'users',
        'activate',
        {'id': ctx.params.id},
        function thenFunc(data){
          ctx.redirect('#/users');
        }
      )
    })

    this.get('#/users/deactivate/:id', function (ctx){
      requester.post(
        'users',
        'deactivate',
        {'id': ctx.params.id},
        function (data){
          ctx.redirect('#/users');
        })
    })

    this.get('#/users/delete/:id', function (ctx){
      requester.post(
        'users',
        'delete',
        {'id': ctx.params.id},
        function (data){
          ctx.redirect('#/users');
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
        'logs',
        link,
        function thenFunc(data){
          let pageNumber = data['page']['number'] + 1;
          let totalPages = data['page']['totalPages'];
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
          ctx.logs = data['_embedded']['logs'];

          loadPage(ctx, './templates/logs/logs.hbs', {pagination: './templates/common/pagination.hbs'});
        }
      )
    })

    this.get('#/projects', function(ctx){
      let projectId = ctx.params.projectId;

      if(!projectId){
        requester.get(
          'projects',
          '',
          function thenFUnc(projects){
            console.log(projects);
            ctx.projects = projects;
            loadPage(ctx, './templates/projects/showProjects.hbs', {projectPreview: './templates/projects/projectPreview.hbs'});
          }
        )
      }else {
        requester.get(
          'projects',
          '?project=' + projectId,
          function thenFUnc(project){
            console.log(project);
            ctx.project = project;
            loadPage(ctx, './templates/projects/projectInfo.hbs');
          }
        )
      }
    })

    function loadPage(ctx, templateUrl, additionalPartials){
      ctx.loggedIn = sessionStorage.getItem('loggedIn');
      ctx.role = sessionStorage.getItem('role');
      ctx.username = sessionStorage.getItem('username');
      ctx.user_id = sessionStorage.getItem('user_id');

      partials = {
        header: './templates/common/header.hbs',
        footer: './templates/common/footer.hbs',
        notifications: './templates/common/notifications.hbs',
        page: templateUrl
      };

      $.extend(partials, additionalPartials);

      ctx.loadPartials(partials).then(function () {
        this.partial('./templates/common/main.hbs')
      })
    }
  });

  function calcTime(dateIsoFormat) {
    let diff = new Date - (new Date(dateIsoFormat));
    diff = Math.floor(diff / 60000);
    if (diff < 1) return 'less than a minute';
    if (diff < 60) return diff + ' minute' + pluralize(diff);
    diff = Math.floor(diff / 60);
    if (diff < 24) return diff + ' hour' + pluralize(diff);
    diff = Math.floor(diff / 24);
    if (diff < 30) return diff + ' day' + pluralize(diff);
    diff = Math.floor(diff / 30);
    if (diff < 12) return diff + ' month' + pluralize(diff);
    diff = Math.floor(diff / 12);
    return diff + ' year' + pluralize(diff);
    function pluralize(value) {
        if (value !== 1) return 's';
        else return '';
    }
}
  app.run();
});
