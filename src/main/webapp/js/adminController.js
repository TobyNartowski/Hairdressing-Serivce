angular.module('mainApp').controller('adminController', function adminController($http, $scope, $window) {
    var object = this;

    object.getData = function () {
        $http.get('api/products').then(function (response) {
            object.product = response.data;
        });
        $http.get('api/employees').then(function (response) {
            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].firstName === 'ADMIN' && response.data[i].lastName === 'ADMIN') {
                    response.data[i] = null;
                }
            }
            object.employee = response.data;
        });
        $http.get('api/services').then(function (response) {
            object.service = response.data;
        });
        $http.get('api/reviews').then(function (response) {
            object.review = response.data;
        })
        object.generateDiagram();
    };


    object.getSingleItem = function(url,id) {
        itemID = $scope.getCookie(id);
        $http.get('api/' + url + '/' +itemID).then(function (response) {
            object.singleItem = response.data;
            $scope.removeServiceCookie(id);
        });
    };
    object.save = function(url) {
        $http.post('api/' + url, object.singleItem);

        $window.location.href = 'http://localhost:8080/indexAdmin.html';
    };

    object.setNeed = function() {
      object.singleItem.need = 1;
      object.save('services');
    };

    object.update = function(url) {
        $http.put('api/' + url, object.singleItem);

        $window.location.href = 'http://localhost:8080/indexAdmin.html';
    };

    object.deleteEmployee = function(id) {
        $http.delete('api/employees/' + id).then(function () {
            $window.location.href = 'http://localhost:8080/indexAdmin.html';
        });

    };
    object.getProduct = function() {
      $http.get('api/products').then(function (response) {
          object.products = response.data;
      })
    };

    object.addProduct = function(product) {
        object.singleItem.products[object.singleItem.products.length] = product;
      $http.put('api/services', object.singleItem);
    };

    object.open = function(key, service, page) {
        $scope.setServiceCookie(key, service);
        setTimeout(()=> {
            $window.location.href = 'http://localhost:8080/modify' + page +'.html';
        },500)
    };
    object.generateDiagram = function() {
        $http.get('api/employees/monthly-reservations').then(function (response) {
            object.diagramData = response.data;
        });

    }
});