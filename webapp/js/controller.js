let app = angular.module('mainApp', ['ngRoute']);


app.controller('loginCtrl', function ($scope, $location) {

   $scope.submit = function () {
       let username = $scope.username;
       let password = $scope.password;

       if(username == 'admin@wp.pl' && password == 'admin') {
             window.location.href = 'http://localhost:8080/';

       } else {
            $scope.errormsg = 'Wrong login or password !';
       }
   }
});