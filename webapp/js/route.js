app.config(function ($routeProvider) {
    // fix
    $routeProvider
        .when('/', {
            templateUrl: 'index.html'
        })
        .when('/login', {
            templateUrl: 'login.html'
        })
        .when('/register', {
            templateUrl: 'register.html'
        })
        .otherwise({
            redirectTo: 'index.html'
        });
});