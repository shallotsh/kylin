/**
 * Created by huangyawu on 2017/6/28.
 */



app.controller('logicCtr', function ($scope, $rootScope, $http) {
    $scope.predict = function () {
        // console.log('input_1: ' + $scope.input_1 + ', type:' + typeof $scope.input_1);
        // console.log('input_2: ' + $scope.input_2);
        // console.log('input_3: ' + $scope.input_3);
        // console.log('input_4: ' + $scope.input_4);
        var paramArray = [];
        paramArray.push($scope.input_1);
        paramArray.push($scope.input_2);
        paramArray.push($scope.input_3);
        paramArray.push($scope.input_4);

        var args = {
            "riddles": paramArray,
            "targetCodeType":2
        };

        console.log(JSON.stringify(args));

        $http({
            method:"POST",
            url:"/api/welfare/predictor/codes",
            data: JSON.stringify(args),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            console.log("resp:" + response.data);
        }, function fail(response) {
            console.log("resp:" + response.data);
        });
    };
});


app.factory('')