angular.module('mainApp').controller('workdatesController', function workdatesController($scope, $http, $window) {
    var object = this;

    function refreshData() {
        $http({
            method : 'GET',
            url : 'api/work-dates'
        }).then(function success(response) {
            object.dates = response.data;
            console.log(object.dates);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    }

    object.saveServiceInto = function(date, firstName, lastName) {
        console.log(date + ' Pracownik :' + firstName + lastName);
        $scope.setServiceCookie('date', date);
        $scope.setServiceCookie('employeeFirstName', firstName);
        $scope.setServiceCookie('employeeLastName', lastName);

        // $http({
        //     method : 'POST',
        //     url : 'api/reservations',
        //     data: object.Reservation
        // }).then(function success(response) {
            $window.location.href = 'http://localhost:8080/serviceChange.html';
        //
        // }, function error(response) {
        //     console.log(response);
        // });


    };
    refreshData();
});
angular.module('mainApp').controller('workDatesInfo', function workDatesInfo($scope, $http, $window) {
    let object = this;

    object.getComleteInfo = function () {
        object.date = $scope.getCookie('date');
        object.employeeFirstName = $scope.getCookie('employeeFirstName');
        object.employeeLastName = $scope.getCookie('employeeLastName');
        object.serviceName = $scope.getCookie('service');
        object.firstName = $scope.getCookie('firstName');
        object.lastName = $scope.getCookie('lastName');
        object.username = $scope.getCookie('username');
    };
    object.getComleteInfo();

    object.saveService = function () {
        $scope.removeAllServiceCookies();
        // $http({
        //     method : 'PUT',
        //     url : 'api/reservations/1?done=false',
        //     // data:
        // }).then(function success(response) {
        //     $window.location.href = 'http://localhost:8080/serviceAccepted.html';
        // }, function error(response) {
        //     console.log(response);
        // });
    };



});