angular.module('mainApp').controller('productController', function productController($http) {
    var object = this;

    function refreshData() {
        $http.get('api/products').then(function (response) {
            object.products = response.data;
        });
    }

    refreshData();
});