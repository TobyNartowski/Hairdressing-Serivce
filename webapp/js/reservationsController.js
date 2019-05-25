angular.module('mainApp').controller('reservationsController', function reservationsController($http, $scope) {
    var object = this;

    getId = function() {
        object.isLogged = $scope.getCookie('id') > 1 ? 1 : 0;
        if(object.isLogged == 1) object.getData();
        else object.dataPresent = 0;
    };

    object.getData = function() {
           object.id = $scope.getCookie('id');
           $http({
               method: 'GET',
               url: 'api/clients/' + object.id + '/reservations'
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
    getId();

});