angular.module('mainApp').controller('productController', function productController($http) {
    var vm = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/products'
        }).then(function success(response) {
            vm.products = response.data;
            console.log(vm.products);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }

    vm.appName = 'Products';
    refreshData();
});