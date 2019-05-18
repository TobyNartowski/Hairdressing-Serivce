'use strict';
var app = angular.module('mainApp', []);

app.controller('loginCtrl', loginCtrl);

$scope.errormsg = '';
loginCtrl.$inject = ['$scope', '$location', '$http', '$resource'];

