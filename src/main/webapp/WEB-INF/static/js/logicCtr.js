/**
 * Created by huangyawu on 2017/6/28.
 */



app.controller('logicCtr', function ($scope, $rootScope, $http) {

    // 其他操作
    // var codes = getData();
    $rootScope.wyfMessage = "欢迎使用我要发预测系统！！";
    $rootScope.codesCount = 0;
    // $rootScope.wyfMessage=JSON.stringify(codes.data.codes, null, 2);
    // $rootScope.wyfCodes = codes.data.codes;
    // $rootScope.codesCount = codes.data.codes.length;

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
            $rootScope.welfareCode=response.data.data;
            console.log("resp:" + JSON.stringify($rootScope.welfareCode, null, 2));
            $rootScope.wyfCodes = $rootScope.welfareCode.codes;
            console.log("wyfCodes:" + JSON.stringify($rootScope.wyfCodes, null, 2));
            $rootScope.codesCount = $rootScope.wyfCodes.length;
            $rootScope.wyfMessage = "预测请求返回成功";
            $rootScope.isPredict = true;
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
        });
    };

});

