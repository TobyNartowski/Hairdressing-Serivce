'use strict';
var app = angular.module('mainApp', []);
angular.module("mainApp").requires.push('ngCookies');

app.controller('loginCtrl', loginCtrl);

app.run(function ($rootScope, $cookies) {
    $rootScope.setCookie = function (username, firstName, lastName) {
        $cookies.put('username', username);
        $cookies.put('firstName', firstName);
        $cookies.put('lastName', lastName);
    };
    $rootScope.removeCookies = function () {
        $cookies.remove('username');
        $cookies.remove('firstName');
        $cookies.remove('lastName');
    };
    $rootScope.removeAllServiceCookies = function () {
        $cookies.remove('username');
        $cookies.remove('firstName');
        $cookies.remove('lastName');
        $cookies.remove('date');
        $cookies.remove('employeeFirstName');
        $cookies.remove('employeeLastName');
        $cookies.remove('service');
    };
    $rootScope.getCookie = function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        return parts.pop().split(";").shift();
    };

    $rootScope.setServiceCookie = function (key, value) {
        $cookies.put(key, value);
    };
    $rootScope.removeServiceCookie = function (key) {
        $cookies.remove(key);
    }
});

app.filter('getUser', ['decoration', function(decoration) {

    function decorateFilter(input) {
        return decoration.symbol + input + decoration.symbol;
    }
    decorateFilter.$stateful = true;

    return decorateFilter;
}]);

