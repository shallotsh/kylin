/**
 * Created by huangyawu on 2017/6/28.
 */



app.controller('logicCtr', function ($scope, $rootScope, $http) {

    init();

    $scope.predict = function () {
        var paramArray = [];
        paramArray.push($scope.input_1);
        paramArray.push($scope.input_2);
        paramArray.push($scope.input_3);
        paramArray.push($scope.input_4);

        console.log("radio:"+ $rootScope.quibinary_first);

        var args = {
            "riddles": paramArray,
            "targetCodeType": $rootScope.quibinary_first
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
            handleResponse(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("预测请求失败!")
        });
    };

    $scope.transfer2Group = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }

        $http({
            method:"POST",
            url:"/api/welfare/transfer/codes",
            data: JSON.stringify($rootScope.welfareCode),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("转换请求失败!")
        });

    }

    $scope.transfer2Direct = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }

        $http({
            method:"POST",
            url:"/api/welfare/transfer/codes",
            data: JSON.stringify($rootScope.welfareCode),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("转换请求失败!")
        });

    }

    // 杀码
    $scope.killCode = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }

        var args = {
            "welfareCode": $rootScope.welfareCode,
            "sumValue": $scope.wyf_sum_tail,
            "boldCode": $scope.wyf_bold,
            "gossip": $scope.wyf_gossip,
            "range": $scope.wyf_range,
            "ternaryLocation": $scope.wyf_locate_three,
            "fishMan":$scope.wyf_fish_man,
            "huBits":$scope.wyf_bit_hu,
            "hBits":$scope.wyf_bit_h,
            "dBits":$scope.wyf_bit_d,
            "uBits":$scope.wyf_bit_u,
            "dipolar": $scope.wyf_dipolar ? 1 : 0,
            "oneEnd": $scope.wyf_one_end ? 1 : 0,
            "bigSum": $scope.wyf_big_sum ? 1 : 0,
            "oddEven": $scope.wyf_all_odd_even ? 1 : 0
        };

        // console.log(JSON.stringify($rootScope.welfareCode, null, 2));
        console.log(JSON.stringify(args, null ,2));

        $http({
            method:"POST",
            url:"/api/welfare/filter/codes",
            data: JSON.stringify(args),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("杀码请求失败!")
        });

    };

    $scope.exportCode = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }

        $http({
            method:"POST",
            url:"/api/welfare/export/codes",
            data: JSON.stringify($rootScope.welfareCode),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleDownloadResp(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("导出请求失败!")
        });
    }

    $scope.reset = function () {
        $scope.input_1 = undefined;
        $scope.input_2 = undefined;
        $scope.input_3 = undefined;
        $scope.input_4 = undefined;
        $scope.wyf_sum_tail =undefined;
        $scope.wyf_bold = undefined;
        $scope.wyf_gossip = undefined;
        $scope.wyf_range = undefined;
        $scope.wyf_locate_three = undefined;
        $scope.wyf_fish_man = undefined;
        $scope.wyf_bit_hu = undefined;
        $scope.wyf_bit_h = undefined;
        $scope.wyf_bit_d = undefined;
        $scope.wyf_bit_u = undefined;
        init();
    }

    function handleDownloadResp(response) {
        if(response == null){
            console.log("request error.");
            return;
        }

        if(response.data != null && response.data.data.error == undefined){
            console.log("success:" + JSON.stringify(response.data, null, 2));
            window.open("/api/welfare/download?fileName="+response.data.data)
        }else{
            console.log("failed:" + JSON.stringify(response.data, null, 2));

            alert(response.data.message)
        }
    }


    function handleResponse (response){
        $rootScope.welfareCode=response.data.data;
        // console.log("resp:" + JSON.stringify($rootScope.welfareCode, null, 2));
        $rootScope.wyfCodes = $rootScope.welfareCode.codes;
        console.log("wyfCodes:" + JSON.stringify($rootScope.wyfCodes, null, 2));
        $rootScope.codesCount = $rootScope.wyfCodes.length;
        $rootScope.wyfMessage = "预测请求返回成功";
        $rootScope.isPredict = true;
        if($rootScope.welfareCode.codeTypeEnum == "DIRECT"){
            $rootScope.group = false;
            $rootScope.direct = true;
        }else{
            $rootScope.group = true;
            $rootScope.direct = false;
        }
        $rootScope.do_kill = true;
        $rootScope.do_export = true;
    }

    function handleException (comment) {
        alert(comment);
    }

    function init(){
        $rootScope.welfareCode = undefined;
        $rootScope.wyfMessage = "欢迎使用我要发预测系统！！";
        $rootScope.codesCount = 0;
        $rootScope.quibinary_first = 3;
        $rootScope.direct = true;
        $rootScope.group = true;
        $rootScope.do_export = false;
        $rootScope.do_kill = false;
        $rootScope.com_select = false;
    }


});

