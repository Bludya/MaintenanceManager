let requester = (() = > {
    const serverBaseUrl = "http://localhost:8000/"

    // Creates request object to kinvey
    function makeRequest(method, module, endpoint)
{
    return req = {
        method,
        dataType: 'json',
        url: serverBaseUrl + module + '/' + endpoint
    };
}

// Function to return GET promise
function get(module, endpoint, thenFunc, failFunc) {
    $.ajax(makeRequest('GET', module, endpoint))
        .then(thenFunc)
        .fail(failFunc);
}

// Function to return POST promise
function post(module, endpoint, data, thenFunc, failFunc) {
    let req = makeRequest('POST', module, endpoint);
    req.data = data;
    $.ajax(req)
        .then(thenFunc)
        .fail(failFunc);
}

// Function to return PUT promise
function update(module, endpoint, data, thenFunc, failFunc) {
    let req = makeRequest('PUT', module, endpoint);
    req.data = data;
    $.ajax(req)
        .then(thenFunc)
        .fail(failFunc);
}

// Function to return DELETE promise
function remove(module, endpoint, thenFunc, failFunc) {
    $.ajax(makeRequest('DELETE', module, endpoint))
        .then(thenFunc)
        .fail(failFunc);
}

return {
    get,
    post,
    update,
    remove
}
})
()
