function loginCtrl($scope) {

    // var vm = this;
    // // var User = $resource('');
    // vm.user = new User();
    //
    // var errormsg = '';
    //
    // vm.addProduct = function(user) {
    //     console.log(vm.user.__proto__);
    //     vm.user.$save(function(data) {
    //         refresh();
    //         vm.user = new User();
    //     });
    // };
    //
    // vm.loadData = function(id) {
    //     vm.details = User.get({UserID: id});
    // };

   $scope.submit = function () {
       let username = $scope.username;
       let password = $scope.password;

       if(username == 'admin@wp.pl' && password == 'admin') {
             window.location.href = 'http://localhost:8080/';

       } else {
            errormsg = 'Wrong login or password !';
       }
   };
    // var refresh = function refreshData() {
    //     vm.users = User.query(
    //         function success(data, headers) {
    //             console.log('Pobrano dane: ' +  data);
    //             console.log(headers('Content-Type'));
    //         },
    //         function error(reponse) {
    //             console.log(response.status); //np. 404
    //         });
    // }

}