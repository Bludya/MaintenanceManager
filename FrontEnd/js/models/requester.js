let requester = (() => {
    const serverBaseUrl = "http://localhost:8080/"

    // Creates request object to kinvey
    function makeRequest(method, module, endpoint) {
        return req = {
            method,
            url: serverBaseUrl + module + '/' + endpoint
        };
    }

    // Function to return GET promise
    function get (module, endpoint) {
        return $.ajax(makeRequest('GET', module, endpoint));
    }

    // Function to return POST promise
    function post (module, endpoint, data) {
        let req = makeRequest('POST', module, endpoint);
        req.data = data;
        return $.ajax(req);
    }

    // Function to return PUT promise
    function update (module, endpoint, data) {
        let req = makeRequest('PUT', module, endpoint);
        req.data = data;
        return $.ajax(req);
    }

    // Function to return DELETE promise
    function remove (module, endpoint) {
        return $.ajax(makeRequest('DELETE', module, endpoint));
    }

    return {
        get,
        post,
        update,
        remove
    }
})()
