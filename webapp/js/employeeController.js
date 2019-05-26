angular.module('mainApp').controller('employeeController', function employeeController($http, $scope, $window) {
    var object = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/employees'
        }).then(function success(response) {
            object.employees = response.data;
            console.log(object.employees);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }

    object.saveEmployee = function(employee) {
      $scope.setServiceCookie('emp', employee);
        $window.location.href = 'http://localhost:8080/employeeReview.html';
    };

    object.appName = 'Employees';
    refreshData();
});