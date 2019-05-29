angular.module('mainApp').controller('adminController', function adminController($http, $scope) {
    var object = this;

    object.getEmployeeData = function () {

        $http({
            method: 'GET',
            url: 'api/employees'
        }).then(function success(response) {

            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].firstName === 'ADMIN' && response.data[i].lastName === 'ADMIN') {
                    response.data[i] = null;
                }
            }
            object.employee = response.data;

        }, function error(response) {
            console.log('API error ' + response.status);
        });

    };
    object.getEmployeeData();
});