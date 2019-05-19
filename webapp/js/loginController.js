function loginCtrl($scope, $window, $timeout, $cookies) {
    this.username = '';
    this.password = '';
    this.errormsg = '';
    var object = this;

    //todo pobierac dane z $resource

    this.submit = function () {
        if(this.username == 'admin@wp.pl' && this.password == 'admin') {
            $window.location.href = 'http://localhost:8080/';

            $cookies.put('username', this.username);
        } else {
            this.errormsg = 'Wrong login or password';

            $timeout(function () {
                object.errormsg = '';
            }, 3000);

        }
    };

    function createCookie(name, value, days) {
        var expires;

        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toGMTString();
        } else {
            expires = "";
        }
        document.cookie = encodeURIComponent(name) + "=" + encodeURIComponent(value) + expires + "; path=/";
    }
    function readCookie(name) {
        var nameEQ = encodeURIComponent(name) + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ')
                c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0)
                return decodeURIComponent(c.substring(nameEQ.length, c.length));
        }
    }
}