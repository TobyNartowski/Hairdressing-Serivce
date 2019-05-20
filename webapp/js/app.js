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
    $rootScope.getCookie = function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        return parts.pop().split(";").shift();
    }
});

