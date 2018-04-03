let auth = (() => {
    function saveSession(userInfo) {
        let userAuth = userInfo._kmd.authtoken;
        sessionStorage.setItem('authtoken', userAuth);
        let userId = userInfo._id;
        sessionStorage.setItem('userId', userId);
        let username = userInfo.username;
        sessionStorage.setItem('username', username);
        sessionStorage.setItem('loggedIn', true);
    }

    // user/login
    function login(email, password, remember) {
        let userData = {
            email: email,
            password: password,
            remember: remember
        };

        return requester.post('users', 'login', userData);
    }

    // user/register
    function register(username, email, password, repeatPassword) {
        let userData = {
          username: username,
          email: email,
          password: password,
          repeatPassword: repeatPassword};

        return requester.post('users', 'register', userData);
    }

    // user/logout
    function logout() {
        let logoutData = {
            authtoken: sessionStorage.getItem('authtoken')
        };

        sessionStorage.clear();

        return requester.post('user', 'logout', logoutData);
    }

    function handleError(reason) {
        showError(reason.responseJSON.description);
    }

    $(document).ajaxStart(() => $('#loadingBox').show());
    $(document).ajaxStop(() => $('#loadingBox').hide());

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

    return {
        login,
        register,
        logout,
        saveSession,
        showInfo,
        showError,
        handleError
    }
})();
