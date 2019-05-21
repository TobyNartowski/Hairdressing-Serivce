angular.module('mainApp').controller('employeeController', function employeeController($http) {
    var vm = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/employees'
        }).then(function success(response) {
            vm.employees = response.data;
            console.log(vm.employees);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }

    vm.appName = 'Employees';
    refreshData();
});