angular.module('mainApp').controller('registerController', function registerController($scope, $http, $window) {
    var object = this;

    object.addPerson = function(person) {
        $http.post('api/clients', person).then(function () {
            $window.location.href = 'http://localhost:8080/register_succes.html';
        });
    }
});