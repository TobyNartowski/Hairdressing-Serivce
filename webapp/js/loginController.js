function loginCtrl($scope, $window, $timeout) {
    this.username = '';
    this.password = '';
    this.errormsg = '';
    var object = this;

    //todo pobierac dane z $resource

    this.submit = function () {
        if(this.username == 'admin@wp.pl' && this.password == 'admin') {
            $window.location.href = 'http://localhost:8080/';
            $scope.setCookie(this.username, 'Piter', 'P');

        } else {
            this.errormsg = 'Wrong login or password';

            $timeout(function () {
                object.errormsg = '';
            }, 3000);

        }
    };
}