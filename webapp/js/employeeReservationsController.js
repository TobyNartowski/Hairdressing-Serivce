angular.module('mainApp').controller('employeeReservationsController', function employeeReservationsController($http, $scope) {
    var object = this;

    object.getEmployeeData = function () {
        $http.get('api/employees/' + $scope.getCookie('id') + '/todays-reservations').then(function (response) {
            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].done === true) {
                    response.data[i] = null;
                }
            }
            object.employee = response.data;
        });
    };
    object.getEmployeeData();
});