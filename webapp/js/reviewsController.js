angular.module('mainApp').controller('reviewsController', function reviewsController($http, $scope) {
    var object = this;
    object.dataLoaded = false;

    object.refreshData = function() {
        $http.get('api/reservations?done=true').then(function (response) {
            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].review == null) {
                    response.data[i] = null;
                }
                object.reviews = response.data;
            }
        });
        object.dataLoaded = true;
    };

    object.getEmployeeData = function () {
        $http({
            method : 'GET',
            url : 'api/employees/' + object.emp + '/reservations?done=true'
        }).then(function success(response) {
            review = {content: 'Oczekujemy na ocene', title: 'Oczekujemy na ocene'};

            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].review == null) {
                    response.data[i].review = review;
                }
            }
            object.employee = response.data;

            if(object.employee[0] == null || object.employee[0] == undefined || object.employee.length == 0) {
                object.dataPresent = 0;
            } else {
                object.dataPresent = object.employee[0].id > 1 ? 1 : 0;
            }
        }, function error(response) {
            object.dataPresent = 0;
            console.log('API error ' + response.status);
        });

    };

    object.getEmployee = function () {
        object.emp = $scope.getCookie('emp');

        if($scope.getCookie('emp').length < 1) {
            object.emp = $scope.getCookie('id');
        } else {
            setTimeout(()=> {
                $scope.removeServiceCookie('emp');
            },5000);
        }
        object.getEmployeeData();
    }


});