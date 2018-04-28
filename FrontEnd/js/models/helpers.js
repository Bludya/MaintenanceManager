    function loadPage(ctx, url, additionalPartials){
      let partial = './templates' + url + '.hbs';

      ctx.loggedIn = sessionStorage.getItem('loggedIn');
      ctx.roles = sessionStorage.getItem('roles');
      ctx.username = sessionStorage.getItem('name');
      ctx.user_id = sessionStorage.getItem('user_id');

      partials = {
        header: './templates/common/header.hbs',
        footer: './templates/common/footer.hbs',
        notifications: './templates/common/notifications.hbs',
        page: partial
      };

      $.extend(partials, additionalPartials);

      ctx.loadPartials(partials).then(function () {
        this.partial('./templates/common/main.hbs');
      })
    }

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

    function showInfo(message) {
        let infoBox = $('#infoBox');
        infoBox.find('span').text(message);
        infoBox.show();
        setTimeout(() => infoBox.fadeOut(), 3000);
    }

    function showError(message) {
        let errorBox = $('#errorBox');
        errorBox.find('span').text(message);
        errorBox.show();
        setTimeout(() => errorBox.fadeOut(), 3000);
    }

    function checkAccessible(ctx, role){
      if(!sessionStorage.loggedIn){
        showInfo("You need to login first.");
        return false;
      }

      if(role != null && role != ''){
        if(sessionStorage.roles != null && !sessionStorage.roles.includes(role)){
          showError("You don't have access to this.");
          return false;
        }
      }

      return true;
    }

    function activatePopup(){
      let popup = document.getElementById("myPopup");
      let form = document.getElementById("form");

      popup.classList.toggle("show");
      form.classList.toggle("show");
    }

    function showField(attribute){
      let popup = document.getElementById(attribute);
      popup.classList.toggle("show");
    }

    function customLink(href) {
          window.location = href;
    }
