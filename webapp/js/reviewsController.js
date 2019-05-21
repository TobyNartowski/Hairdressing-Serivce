angular.module('mainApp').controller('reviewsController', function reviewsController($http) {
    var vm = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/reviews'
        }).then(function success(response) {
            vm.reviews = response.data;
            console.log(vm.reviews);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }
    refreshData();
});