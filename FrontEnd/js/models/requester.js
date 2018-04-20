let requester = (() => {
    const serverBaseUrl = "http://localhost:8000"
    const unauthorizedMessage = "You are unauthorized to access this url.";
    const internalServerError = "Server side error occured. Message is sent to the admin."
    // Creates request object to kinvey
    function makeRequest(method, url, data) {
        let req = {
            type: method,
            url: serverBaseUrl + url,
            data: data
        };

        if(sessionStorage.token != 'undefined'){
          req.headers =  {'Authorization':sessionStorage.token}
        }

        return req;
    }

    function defaultFailFunc(data, a, b){
      let error = data['error'];
      let errorMessage = "Unknown error.";

      if(data.responseJSON.status == 403){
        errorMessage = unauthorizedMessage;
      }else if (data.responseJSON.status ==500) {
        errorMessage = internalServerError;
      }

      showError(errorMessage);
    }

    // Function to return GET promise
    function get (url, thenFunc, failFunc) {
          if(failFunc == null){
            failFunc = defaultFailFunc;
          }

          $.ajax(makeRequest('GET', url))
            .then(thenFunc)
            .fail(failFunc);
    }

    function post (url, data, thenFunc, failFunc) {
        let req = makeRequest('POST', url, data);
        $.ajax(req)
          .then(thenFunc)
          .fail(failFunc);
    }

    function loginPost (data, thenFunc, failFunc) {
        let req = makeRequest('POST', '/login', JSON.stringify(data));
        req.contentType = 'text/plain';
        $.ajax(req)
          .then(thenFunc)
          .fail(failFunc);
    }

    function update (url, data, thenFunc, failFunc) {
        let req = makeRequest('PUT', url, data);
        $.ajax(req)
          .then(thenFunc)
          .fail(failFunc);
    }

    function remove (url, thenFunc, failFunc) {
        $.ajax(makeRequest('DELETE', url))
          .then(thenFunc)
          .fail(failFunc);
    }

    return {
        get,
        post,
        loginPost,
        update,
        remove
    }
})()
