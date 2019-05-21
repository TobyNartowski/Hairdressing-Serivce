angular.module('mainApp').controller('registerController', function registerController($scope, $http, $window) {
    var object = this;

    object.addPerson = function(person) {
        console.log(person);
        $http({
            method : 'POST',
            url : 'api/clients',
            data: person
        }).then(function success(response) {
            person = {};
            $window.location.href = 'http://localhost:8080/register_succes.html';
        }, function error(response) {
            console.log(response);
        });
    }
});