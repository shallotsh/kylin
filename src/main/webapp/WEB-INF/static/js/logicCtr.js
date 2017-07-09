/**
 * Created by huangyawu on 2017/6/28.
 */



app.controller('logicCtr', function ($scope, $rootScope, $http) {
    $scope.predict = function () {
        console.log('input_1: ' + $scope.input_1 + ', type:' + typeof $scope.input_1);
        console.log('input_2: ' + $scope.input_2);
        console.log('input_3: ' + $scope.input_3);
        console.log('input_4: ' + $scope.input_4);
        $http({
            method:'POST',
            url:"/api/welfare/predictor/codes",
            params:{
                'seq1':$scope.input_1,
                'seq2':$scope.input_2,
                'seq3':$scope.input_3,
                'seq4':$scope.input_4,
                'type':2
            },
            headers:{
                'Content-Type': 'application/json; charset=UTF-8'
            }
        }).then(function success(response) {
            console.log(response.data);
        }, function fail() {
            console.log(response.data);
        });
    };
});


app.factory('')