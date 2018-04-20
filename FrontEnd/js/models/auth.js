let auth = (() => {
    function saveSession(data) {
        sessionStorage.setItem('token', data['Authorization']);
        sessionStorage.setItem('name', data['name']);
        sessionStorage.setItem('roles', data['roles']);
        sessionStorage.setItem('loggedIn', true);
    }

    // user/login
    function login(username, password, funcThen, funcFail) {
        let userData = {
            'username': username,
            'password': password,
        };

        requester.loginPost(userData, funcThen, funcFail);
    }

    // user/register
    function register(username, email, password, repeatPassword, funcThen, funcFail) {
        let userData = {
          username: username,
          email: email,
          password: password,
          repeatPassword: repeatPassword};

        requester.post('/users/register', userData, funcThen, funcFail);
    }

    // user/logout
    function logout() {
        sessionStorage.clear();
    }

    function handleError(reason) {
        showError(reason.responseJSON.description);
    }

    $(document).ajaxStart(() => $('#loadingBox').show());
    $(document).ajaxStop(() => $('#loadingBox').hide());

    return {
        login,
        register,
        logout,
        saveSession,
        handleError
    }
})();
