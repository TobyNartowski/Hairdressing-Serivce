angular.module('mainApp').controller('registerController', function registerController($scope, $http, $window) {
    var object = this;

    object.addPerson = function(person) {
        if(person.login.length <= 3) {
            object.errorMsg = 'Wprowadzony login jest zbyt krótki';
        } else {
            $http.post('api/clients', person).then(function () {
                $window.location.href = 'http://localhost:8080/register_succes.html';
            }, function (response) {
                object.errorMsg = 'Wprowadzone dane są niepoprawne'
            });
        }
    }
});