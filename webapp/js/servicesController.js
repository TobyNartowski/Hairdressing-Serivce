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
            console.log('API error ' + response.status + ' ' + response);
        });
    }

    object.makeReservation = function(name, service) {
        if($scope.getCookie('username').length > 1) {

            console.log(name);
            name = name.replace(/\W/g, '');
            $scope.setServiceCookie('service', name);
            $scope.setServiceCookie('serviceId', service);

            $window.location.href = 'http://localhost:8080/serviceDate.html';
        } else {
            let message = angular.element(document.querySelector('.errorText'));
            message.addClass('errorTextActive');
            setTimeout(function()
                {
                    message.removeClass('errorTextActive');
                }
                , 800);
        }
    };

    getService = function() {
        object.serviceName =  $scope.getCookie('service');
    };

    refreshData();
    getService();
});