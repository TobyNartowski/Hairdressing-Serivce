angular.module('mainApp').controller('workdatesController', function workdatesController($scope, $http, $window) {
    var object = this;

    function refreshData() {
        $http.get('api/work-dates').then(function (response) {
            object.dates = response.data;
        });
    }

    object.saveServiceInto = function(date, firstName, lastName, workId) {
        console.log(date + ' Pracownik :' + firstName + lastName + workId);
        $scope.setServiceCookie('date', date);
        $scope.setServiceCookie('employeeFirstName', firstName);
        $scope.setServiceCookie('employeeLastName', lastName);
        $scope.setServiceCookie('workDateId', workId);

        $window.location.href = 'http://localhost:8080/serviceChange.html';
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
        object.workDateId = $scope.getCookie('workDateId');
        object.clientId = $scope.getCookie('id');
        object.serviceId = $scope.getCookie('serviceId');
    };
    object.getComleteInfo();

    object.saveService = function () {
        clearCookies();

        $http.post('api/reservations?client_id=' + object.clientId + '&service_id=' + object.serviceId +
            '&workDate_id=' + object.workDateId,
            {}).then(function () {
                object.clientId = null;
            $window.location.href = 'http://localhost:8080/serviceAccepted.html';
        });
    };

    object.removeService = function(page) {
        $scope.removeServiceCookie('serviceName');
        $scope.removeServiceCookie('serviceDate');
        $scope.removeServiceCookie('serviceEmployeeF');
        $scope.removeServiceCookie('serviceEmployeeL');

        $window.location.href = 'http://localhost:8080/' + page +'.html';
    };

    object.logout = function () {
        $scope.removeCookies();
        setTimeout(()=>{
            object.removeService('index');
        }, 500);
    };

    function clearCookies() {
        $scope.removeServiceCookie('serviceId');
        $scope.removeServiceCookie('service');
        $scope.removeServiceCookie('date');
        $scope.removeServiceCookie('employeeFirstName');
        $scope.removeServiceCookie('employeeLastName');
        $scope.removeServiceCookie('workDateId');
    }
});