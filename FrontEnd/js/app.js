$(() => {
  const app = Sammy('body', function (){
    this.use('Handlebars', 'hbs');

    this.get('index.html', function (ctx){
      loadPage(ctx, './templates/welcome/welcome.hbs');
    })

    this.get('#/register', function (ctx){
      loadPage(ctx, './templates/welcome/register_form.hbs')
    })

    this.get('#/login', function (ctx){
      loadPage(ctx, './templates/welcome/login_form.hbs');
    })

    this.post('#/register', function (ctx){
      let {username, email, password, repeatPassword} = this.params;
      let english = /^[A-Za-z]*$/;
      let englishAndLetters = /^[A-Za-z0-9]*$/;

      if(!english.test(username)){
        auth.showError('Username should be english alphabet only.')
        return;
      }

      if(username.length < 3){
        auth.showError('Username should be at least 3 characters long.')
        return;
      }

      if(!englishAndLetters.test(password)){
        auth.showError('Password should be only english alphabet and letters.')
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

      auth.register(username, email, password, repeatPassword)
        .then(function (data){
          ctx.redirect('#/login');
          setTimeout(() => auth.showInfo("Registration successful!"), 500);
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.post('#/login', function (ctx){
      let {username, password} = this.params;
      let english = /^[A-Za-z]*$/;
      let englishAndLetters = /^[A-Za-z0-9]*$/;

      if(!english.test(username)){
        auth.showError('Username should be english alphabet only.')
        return;
      }

      if(username.length < 3){
        auth.showError('Username should be at least 3 characters long.')
        return;
      }

      if(!englishAndLetters.test(password)){
        auth.showError('Password should be only english alphabet and letters.')
        return;
      }

      if(password.length < 6){
        auth.showError('Password should be at least 6 characters long.')
        return;
      }

      auth.login(username, password)
        .then(function (data){
          auth.saveSession(data);
          ctx.redirect('#/catalog');
          setTimeout(() => auth.showInfo("Login successful!"), 500);
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.get('#/logout', function (ctx){
      console.log(sessionStorage.getItem('authtoken'));
      auth.logout()
        .then(function (data){
          ctx.redirect('#');
          setTimeout(() => auth.showInfo("Logout successful!"), 500);
        }).catch(function (err){
          ctx.redirect('#');
        })
    })

    this.get('#/catalog', function (ctx){
      requester.get('appdata', 'posts?query={}&sort={"_kmd.ect": -1}', 'Kinvey')
        .then(function (posts){

          for (let i = 0; i < posts.length; i++) {
            let post = posts[i];
            post.timeCreated = calcTime(post._kmd.ect);
            post.rank = i+1;
            post.isAuthor = post.author === sessionStorage.getItem("username");
          }

          ctx.posts = posts;

          ctx.loggedIn = sessionStorage.getItem('loggedIn');
          ctx.username = sessionStorage.getItem('username');
          ctx.authtoken = sessionStorage.getItem('authtoken');

          ctx.loadPartials({
            header: './templates/common/header.hbs',
            footer: './templates/common/footer.hbs',
            notifications: './templates/common/notifications.hbs',
            menu: './templates/common/menu.hbs',
            post: './templates/catalog/post.hbs',
            page: './templates/catalog/catalog.hbs'
          }).then(function () {
            this.partial('./templates/common/main.hbs')
          })
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.get('#/submitPost', function (ctx){
      loadPage(ctx, './templates/catalog/submit.hbs')
    })

    this.post('#/submitPost', function (ctx){
      let {url, title, imageUrl, description} = ctx.params;

      if(url.length < 1){
        auth.showError('URL is mandatory.')
        return;
      }

      if(title.length < 1){
        auth.showError('Title is mandatory.')
        return;
      }

      if(!url.startsWith('http')){
        auth.showError('Invalid URL')
        return;
      }

      let post = {author: sessionStorage.getItem('username'), url, title, imageUrl, description};

      requester.post('appdata', 'posts', 'Kinvey', post)
        .then(function (){
          ctx.redirect('#/catalog');

        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.get('#/editPost/:id', function (ctx){
      ctx._id = ctx.params.id;
      requester.get('appdata', 'posts/' + ctx._id, 'Kinvey')
        .then(function (data){
          ctx.url = data.url;
          ctx.title = data.title;
          ctx.imageUrl = data.imageUrl;
          ctx.description = data.description;
          loadPage(ctx, './templates/catalog/editPost.hbs')
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.post('#/updatePost/:id', function (ctx){
      let data = {
        author: sessionStorage.getItem('username'),
        url: ctx.params.url,
        title: ctx.params.title,
        imageUrl: ctx.params.imageUrl,
        description: ctx.params.description
      }

      console.log(data);
      let id = ctx.params.id;
      requester.update('appdata', 'posts/' + id, 'Kinvey', data)
        .then(function (){
          auth.showInfo('Post updated.')
          ctx.redirect('#/catalog');
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.get('#/deletePost/:id', function (ctx){
      let id = ctx.params.id;
      requester.remove('appdata', 'posts/' + id, 'Kinvey')
        .then(function (){
          auth.showInfo('Post deleted.')
          ctx.redirect('#/catalog');
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.get('#/viewComments/:id', function (ctx){
      ctx.id = ctx.params.id;
      requester.get('appdata', 'posts/' + ctx.id, 'Kinvey')
        .then(function (data){
          ctx.url = data.url;
          ctx.title = data.title;
          ctx.imageUrl = data.imageUrl;
          ctx.description = data.description;

          requester.get('appdata', `comments?query={"postId":"${ctx.id}"}&sort={"_kmd.ect": -1}`, 'Kinvey')
            .then(function (data) {
              ctx.comments = data;
              ctx.detailed = true;

              for(let comment of data){
                comment.timeCreated = calcTime(comment._kmd.ect);
                comment.commentId = comment._id;
                comment.isAuthor = comment.author == sessionStorage.getItem('username');
              }

              ctx.loadPartials({
                header: './templates/common/header.hbs',
                footer: './templates/common/footer.hbs',
                notifications: './templates/common/notifications.hbs',
                menu: './templates/common/menu.hbs',
                post: './templates/catalog/post.hbs',
                comment: './templates/comments/comment.hbs',
                page: './templates/comments/viewComments.hbs'
              }).then(function () {
                this.partial('./templates/common/main.hbs');
              })
            }).catch(function (err){
              auth.showError(JSON.parse(err.responseText).description);
            })
        })
    })

    this.get('#/myPosts', function (ctx){
      requester.get('appdata', `posts?query={"author":"${sessionStorage.getItem('username')}"}&sort={"_kmd.ect": -1}`, 'Kinvey')
        .then(function (posts){

          for (let i = 0; i < posts.length; i++) {
            let post = posts[i];
            post.timeCreated = calcTime(post._kmd.ect);
            post.rank = i+1;
            post.isAuthor = post.author === sessionStorage.getItem("username");
          }

          ctx.posts = posts;

          ctx.loggedIn = sessionStorage.getItem('loggedIn');
          ctx.username = sessionStorage.getItem('username');
          ctx.authtoken = sessionStorage.getItem('authtoken');

          ctx.loadPartials({
            header: './templates/common/header.hbs',
            footer: './templates/common/footer.hbs',
            notifications: './templates/common/notifications.hbs',
            menu: './templates/common/menu.hbs',
            post: './templates/catalog/post.hbs',
            page: './templates/catalog/catalog.hbs'
          }).then(function () {
            this.partial('./templates/common/main.hbs')
          })
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.post('#/comment/:id', function(ctx){
      let content = ctx.params.content;

      if(content.length < 1){
        auth.showError('Too short content.')
        return;
      }

      let comment = {postId: ctx.params.id, author: sessionStorage.getItem('username'), content};

      requester.post('appdata', 'comments', 'Kinvey', comment)
        .then(function (){
          ctx.redirect('#/viewComments/' + ctx.params.id);
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    this.post('#/deleteComment/:id', function (ctx){
      let id = ctx.params.id;

      requester.remove('appdata', 'comments/' + id, 'Kinvey')
        .then(function (){
          auth.showInfo('Comment deleted.');
          ctx.redirect('#/viewComments/' + id);
        }).catch(function (err){
          auth.showError(JSON.parse(err.responseText).description);
        })
    })

    function loadPage(ctx, templateUrl, additionalPartials){
      ctx.loggedIn = sessionStorage.getItem('loggedIn');
      ctx.username = sessionStorage.getItem('username');
      ctx.authtoken = sessionStorage.getItem('authtoken');

      partials = {
        header: './templates/common/header.hbs',
        footer: './templates/common/footer.hbs',
        menu: './templates/common/menu.hbs',
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
