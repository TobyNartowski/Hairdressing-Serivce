angular.module('mainApp').controller('servicesController', function servicesController($scope, $http, $window) {
    var object = this;

    function refreshData() {
        $http.get('api/services').then(function (response) {
            object.services = response.data;
            object.servicesLength = object.services.length;
            object.begin = 0;
        });
    }

    object.increment = function() {
      object.begin += 1;
      console.log(object.begin);
    };

    object.makeReservation = function(service) {
        if($scope.getCookie('username').length > 1) {

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
        $http.get('api/services/' + $scope.getCookie('serviceId')).then(function (response) {
            object.serviceName = response.data.name;
            object.serviceDuration = response.data.duration;
            object.servicePrice = response.data.price;
        });
    };

    refreshData();
    getService();
});