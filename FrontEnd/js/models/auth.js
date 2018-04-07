let auth = (() => {
    function saveSession(id, username, role) {
        sessionStorage.setItem('user_id', id);
        sessionStorage.setItem('username', username);
        sessionStorage.setItem('role', role);
        sessionStorage.setItem('loggedIn', true);
    }

    // user/login
    function login(email, password, remember, funcThen, funcFail) {
        let userData = {
            email: email,
            password: password,
            remember: remember
        };

        requester.post('users', 'login', userData, funcThen, funcFail);
    }

    // user/register
    function register(username, email, password, repeatPassword, funcThen, funcFail) {
        let userData = {
          username: username,
          email: email,
          password: password,
          repeatPassword: repeatPassword};

        return requester.post('users', 'register', userData, funcThen, funcFail);
    }

    // user/logout
    function logout(funcThen, funcFail) {
        let logoutData = {
            authtoken: sessionStorage.getItem('authtoken')
        };

        sessionStorage.clear();

        return requester.post('users', 'logout', logoutData, funcThen, funcFail);
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
