angular.module('mainApp').controller('servicesController', function servicesController($scope, $http, $window) {
    var object = this;

    function refreshData() {
        $http.get('api/services').then(function (response) {
            object.services = response.data;
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