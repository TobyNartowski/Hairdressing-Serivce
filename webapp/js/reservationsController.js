angular.module('mainApp').controller('reservationsController', function reservationsController($http, $scope, $window, $cookies) {

    var object = this;

    getId = function() {
        object.isLogged = $scope.getCookie('id') > 1 ? 1 : 0;
        if(object.isLogged == 1) {
            object.getData();
            object.getHistory();
        }
        else {
            object.dataPresent = 0;
            object.historyPresent = 0;
        }
    };

    object.getData = function() {
           object.id = $scope.getCookie('id');
           $http({
               method: 'GET',
               url: 'api/clients/' + object.id + '/reservations?done=false'
           }).then(function success(response) {
               object.reservations = response.data;

               if(object.reservations[0] == null || object.reservations[0] == undefined) {
                   object.dataPresent = 0;
               } else {
                   object.dataPresent = object.reservations[0].id >= 1 ? 1 : 0;
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

            if(object.history[0] == null || object.history[0] == undefined) {
                object.historyPresent = 0;
            } else {
                object.historyPresent = object.history[0].id >= 1 ? 1 : 0;
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


        $http.post('api/reviews?reservation=' + object.reservationID, review).then(function () {
            $window.location.href = 'http://localhost:8080/index.html';
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

    object.setService = function(reservation) {
        $scope.setServiceCookie('sid', reservation);

        setTimeout(() =>{
            $window.location.href = 'http://localhost:8080/modifyReservation.html';
        }, 700);
    };

    object.getServiceToModyfication = function() {
      $http.get('api/reservations/' + $scope.getCookie('sid')).then(function (response) {
          object.reservationToModify = response.data;
      });
      $http.get('api/work-dates').then(function (response) {
          object.newDate = response.data;
      });

      setTimeout(() => {
          $scope.removeServiceCookie('sid');
      }, 1000);
    };

    object.deleteService = function(reservation) {
      $http.delete('api/reservations/' + reservation.id).then(function () {
          $window.location.href = 'http://localhost:8080/serviceDeleted.html';
      }, function () {
          console.log('API ERROR');
      })
    };

    object.changeReservationDate = function (reservation, workdate) {
        $http.delete('api/reservations/' + reservation.id).then(function () {
            $http.post('api/reservations?client_id=' + reservation.client.id + '&service_id=' + reservation.service.id +
                '&workDate_id=' + workdate,
                {}).then(function () {
                $window.location.href = 'http://localhost:8080/serviceAltered.html';
            });
        });
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