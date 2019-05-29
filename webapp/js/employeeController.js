angular.module('mainApp').controller('employeeController', function employeeController($http, $scope, $window) {
    var object = this;

    function refreshData() {
        $http.get('api/employees').then(function (response) {
            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].firstName === 'ADMIN' && response.data[i].lastName === 'ADMIN') {
                    response.data[i] = null;
                }
            }
            object.employees = response.data;
        });
    }

    object.saveEmployee = function(employee) {
        $scope.setServiceCookie('emp', employee);
        setTimeout(() => {
            $window.location.href = 'http://localhost:8080/employeeReview.html';
        }, 1000);
    };

    object.appName = 'Employees';
    refreshData();
});