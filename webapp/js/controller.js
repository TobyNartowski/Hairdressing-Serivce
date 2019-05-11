let app = angular.module('mainApp', ['ngRoute']);

app.controller('loginCtrl', function ($scope, $location) {
   $scope.submit = function () {
       var username = $scope.username;
       var password = $scope.password;

       // if($scope.username == 'admin@wp.pl' && $scope.password == 'admin') {
       //
       // }
   }
});