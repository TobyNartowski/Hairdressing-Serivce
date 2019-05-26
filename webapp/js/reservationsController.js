angular.module('mainApp').controller('reservationsController', function reservationsController($http, $scope, $window, $cookies) {

    var object = this;

    getId = function() {
        object.isLogged = $scope.getCookie('id') > 1 ? 1 : 0;
        if(object.isLogged == 1) {
            object.getData();
            object.getHistory();
        }
        else object.dataPresent = 0;
    };

    object.getData = function() {
           object.id = $scope.getCookie('id');
           $http({
               method: 'GET',
               url: 'api/clients/' + object.id + '/reservations?done=false'
           }).then(function success(response) {
               object.reservations = response.data;
               console.log(object.reservations);

               if(object.reservations[0] == null || object.reservations[0] == undefined) {
                   object.dataPresent = 0;
               } else {
                   object.dataPresent = object.reservations[0].id > 1 ? 1 : 0;
               }
           }, function error(response) {
               console.log('API error ' + response.status);
               object.dataPresent = 0;
           });
    };

    object.getHistory = function () {
        object.id = $scope.getCookie('id');
        $http({
            method: 'GET',
            url: 'api/clients/' + object.id + '/reservations?done=true'
        }).then(function success(response) {
            object.history = response.data;
            console.log(object.history);

            if(object.history[0] == null || object.history[0] == undefined) {
                object.historyPresent = 0;
            } else {
                object.historyPresent = object.history[0].id > 1 ? 1 : 0;
            }
        }, function error(response) {
            console.log('API error ' + response.status);
            object.historyPresent = 0;
        });
    };

    object.createReview = function (reservation) {
        console.log(reservation);
        setReviewCookies(reservation);

        $scope.removeServiceCookie('id');
        $window.location.href = 'http://localhost:8080/review.html';
    };

    object.init = function() {
        object.review = getReviewCookies();
        console.log(object.review);
        $scope.setServiceCookie('id', object.review.id);
    };

    object.sendOpinion = function() {
        var review = {};
        review.title = object.review.title;
        review.content = object.review.content;
        review.date = Date.now();
        $http({
            method : 'POST',
            url : 'api/reviews?reservation=' + object.reservationID,
            data: review
        }).then(function success(response) {
            $window.location.href = 'http://localhost:8080/index.html';
        }, function error(response) {
            console.log(response);
        });
    };

    object.checkInput = function() {
        if(object.review.content == undefined || object.review.title == undefined || object.review.content == null || object.review.title == null) {
            object.errormsg = 'Musisz wypełnić wszystkie pola !';
            setTimeout(() => {
                object.errormsg = '';
            }, 1000)
        } else {
            object.sendOpinion();
        }
    };

    getId();

    function setReviewCookies(reservation) {
        $scope.setServiceCookie('date', reservation.workDate.date);
        $scope.setServiceCookie('employeefn', reservation.workDate.employees[0].firstName);
        $scope.setServiceCookie('employeeln', reservation.workDate.employees[0].lastName);
        $scope.setServiceCookie('servicen', reservation.service.name);
        $scope.setServiceCookie('reservationid', reservation.id);
        $scope.setServiceCookie('idclient', reservation.client.id);
    }

    function getReviewCookies() {
        var review = {};
        review.date = $scope.getCookie('date');
        review.employeeFirstName = $scope.getCookie('employeefn');
        review.employeeLastName = $scope.getCookie('employeeln');
        review.service = $scope.getCookie('servicen');
        object.reservationID = $scope.getCookie('reservationid');
        review.id = $scope.getCookie('idclient');

        $scope.removeServiceCookie('date');
        $scope.removeServiceCookie('employeefn');
        $scope.removeServiceCookie('employeeln');
        $scope.removeServiceCookie('servicen');
        $scope.removeServiceCookie('reservationid');
        $scope.removeServiceCookie('idclient');
        return review;
    }

});