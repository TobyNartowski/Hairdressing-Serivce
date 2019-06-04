'use strict';
var app = angular.module('mainApp', []);
angular.module("mainApp").requires.push('ngCookies');
angular.module("mainApp").requires.push('ngSanitize');


app.controller('loginCtrl', loginCtrl);

app.run(function ($rootScope, $cookies, $http, $compile, $interpolate) {
    $rootScope.currentDate = new Date();

    $rootScope.setCookie = function (username, firstName, lastName) {
        $cookies.put('username', username);
        $cookies.put('firstName', firstName);
        $cookies.put('lastName', lastName);
    };
    $rootScope.removeCookies = function () {
        $cookies.remove('id');
        $rootScope.removeAllServiceCookies();
    };
    $rootScope.removeAllServiceCookies = function () {
        $cookies.remove('username');
        $cookies.remove('firstName');
        $cookies.remove('lastName');
        $cookies.remove('date');
        $cookies.remove('employeeFirstName');
        $cookies.remove('employeeLastName');
        $cookies.remove('service');
        $cookies.remove('serviceId');
    };
    $rootScope.getCookie = function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        return parts.pop().split(";").shift();
    };

    $rootScope.setServiceCookie = function (key, value) {
        $cookies.put(key, value);
    };
    $rootScope.removeServiceCookie = function (key) {
        $cookies.remove(key);
    };
    $rootScope.Footer = $interpolate("<footer class=\"footer\" style=\"margin-top: -7px!important;\">\n" +
        "        <center>\n" +
        "            <p><img class=\"logoTile\" src=\"/img/beaute_white.png\"></p>\n" +
        "            <p class=\"footerTitle\">Najlepszy salon usługowy</p>\n" +
        "            <p class=\"footerInfoText\"><strong>Maximus mauris sceleri sque, at rutrum nulla dictum.</strong></p>\n" +
        "        </center>\n" +
        "        <div class=\"footerTiles\">\n" +
        "            <h3>Nasz Salon</h3>\n" +
        "            <p>Salon usługowy</p>\n" +
        "            <p>Kielce ul. Wiosna 2</p>\n" +
        "            <p>21-005</p>\n" +
        "        </div>\n" +
        "        <div class=\"footerTiles\">\n" +
        "            <h3>Godziny otwarcia:</h3>\n" +
        "            <p>Pn: 9:00 - 15:30</p>\n" +
        "            <p>Wt-Pt: 9:00 - 17:00</p>\n" +
        "            <p>So: 8:00 - 17:00</p>\n" +
        "        </div>\n" +
        "        <div class=\"footerTiles\">\n" +
        "            <h3>Kontakt </h3>\n" +
        "            <p>tel: +48(22) 621 3453</p>\n" +
        "            <p>email: rusembpol@mid.ru</p>\n" +
        "        </div>\n" +
        "        <div style=\"clear: both\"></div>\n" +
        "        <center><h3 class=\"copyRight\">Copyright © Beauté </h3></center>\n" +
        "        <p class=\"flaticon bottomFooter\">Icons made by <a href=\"https://www.flaticon.com/authors/smashicons\" title=\"Smashicons\">Smashicons</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></p>\n" +
        "        <p>Bulma by <a href=\"https://jgthms.com\">Jeremy Thomas</a>. The source code is licensed\n" +
        "        <a href=\"http://opensource.org/licenses/mit-license.php\">MIT</a>. The website content\n" +
        "        is licensed <a href=\"http://creativecommons.org/licenses/by-nc-sa/4.0/\">CC BY NC SA 4.0</a>.\n" +
        "          \n" +
        "    </p>\n" +
        "</footer>")($rootScope);
});




