function loginCtrl($scope, $window, $timeout, $http) {
    this.errormsg = '';
    var object = this;

    this.getData = function () {
        $http({
            method : 'GET',
            url : 'api/clients'
        }).then(function success(response) {
            object.people = response.data;
            console.log(object.people);
        }, function error(response) {
            console.log('API error ' + response.status);
        });
    };

    this.submit = function () {
        object.found = null;

        let needle = object.username;
        let bigNeedle = object.password;
        for (let i = 0; i < object.people.length; i++){
            if (object.people[i].login == needle && object.people[i].hash == bigNeedle){
                object.found = object.people[i];
            }
        }

        if(object.found != null) {
            $window.location.href = 'http://localhost:8080/';
            $scope.setCookie(this.username, object.found.firstName, object.found.lastName);

        } else {
            this.errormsg = 'Wrong login or password';

            $timeout(function () {
                object.errormsg = '';
            }, 3000);

        }
    };
    this.getData();
}