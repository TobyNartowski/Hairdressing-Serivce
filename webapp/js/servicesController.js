angular.module('mainApp').controller('servicesController', function servicesController($scope, $http, $window) {
    var object = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/services'
        }).then(function success(response) {
            object.services = response.data;
            console.log(object.services);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }

    object.makeReservation = function(name) {
        console.log(name);
        name = name.replace(/\W/g, '');
        $scope.setServiceCookie('service', name);

        $window.location.href = 'http://localhost:8080/serviceDate.html';
    };

    getService = function() {
        object.serviceName =  $scope.getCookie('service');
    };

    refreshData();
    getService();
});