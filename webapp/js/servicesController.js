angular.module('mainApp').controller('servicesController', function servicesController($http) {
    var vm = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/services'
        }).then(function success(response) {
            vm.services = response.data;
            console.log(vm.services);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }

    vm.appName = 'Reviews';
    refreshData();
});