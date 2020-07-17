angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http({
        method : "POST",
        url : "http://localhost:8080/facebox/check"
    }).
        then(function(response) {
            $scope.greeting = response.data;
        });
});