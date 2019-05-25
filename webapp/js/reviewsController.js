angular.module('mainApp').controller('reviewsController', function reviewsController($http, $scope) {
    var object = this;
    object.dataLoaded =false;

    object.refreshData = function() {
        $http({
            method : 'GET',
            url : 'api/reservations/1?done=true'
        }).then(function success(response) {
            object.reviews = response.data;
            console.log(object.reviews);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
        object.dataLoaded = true;
    };

    this.getEmployeeData = function () {

        object.found = null;
        
        console.log(object.emp);
        console.log(object.reviews);
        for (let i = 0; i < object.reviews.length; i++){
            let employee = object.reviews[i].workDate.employees[0].firstName;
            employee += object.reviews[i].workDate.employees[0].lastName;
            console.log(employee);
            if (employee == object.employee){
                object.found = object.reviews[i];
                console.log('fouind ' + employee + 'czyli' + object.employee);
            }
        }

    };

    object.getEmployee = function () {
        object.refreshData();
        object.emp = $scope.getCookie('emp');
        object.employee = object.emp;
        object.emp = object.emp.replace('_', ' ');

        $scope.removeServiceCookie('emp');
        return object.emp;


    }
});