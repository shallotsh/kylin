/**
 * Created by huangyawu on 2017/6/28.
 */



app.controller('logicCtr', function ($scope, $rootScope, $http) {

    // 其他操作
    $rootScope.wyfMessage=JSON.stringify({"key":"HelloWorld"}, null, 2)

    $scope.predict = function () {
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
            $rootScope.wyfCode=response.data;
            $rootScope.isPredict = true;
            $rootScope.wyfMessage = JSON.stringify($rootScope.wyfCode, null, 2);
            console.log("resp:" + JSON.stringify($rootScope.wyfCode, null, 2));
        }, function fail(response) {
            $rootScope.wyfCode=response.data;
            console.log("resp:" + JSON.stringify($rootScope.wyfCode, null, 2));
            $rootScope.wyfMessage=JSON.stringify($rootScope.wyfCode, null, 2);
        });
    };
});


app.factory('')