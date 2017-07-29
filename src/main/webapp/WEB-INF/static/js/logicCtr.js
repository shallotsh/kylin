/**
 * Created by huangyawu on 2017/6/28.
 */



app.controller('logicCtr', function ($scope, $rootScope, $http) {

    init();
    $rootScope.cacheQueue = new Array();

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
            url:"/api/welfare/codes/predict",
            data: JSON.stringify(args),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
            $rootScope.wyfMessage = predictFormat($rootScope.wyfCodes.length);
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
            url:"/api/welfare/codes/transfer",
            data: JSON.stringify($rootScope.welfareCode),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
            $rootScope.wyfMessage = "转码成功, 共计 " + $rootScope.codesCount + " 注.";
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
            url:"/api/welfare/codes/transfer",
            data: JSON.stringify($rootScope.welfareCode),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
            $rootScope.wyfMessage = "转码成功, 共计 " + $rootScope.codesCount + " 注.";
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("转换请求失败!");
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
        var count = $rootScope.wyfCodes.length;

        $http({
            method:"POST",
            url:"/api/welfare/codes/filter",
            data: JSON.stringify(args),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
            $rootScope.wyfMessage = filterFormat(count, $rootScope.wyfCodes.length);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("杀码请求失败!");
        });

    };

    $scope.exportCode = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }

        var data = deepCopy($rootScope.welfareCode);

        console.log("exportData:" + JSON.stringify(data, null, 2));

        $http({
            method:"POST",
            url:"/api/welfare/codes/export",
            data: JSON.stringify(data),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleDownloadResp(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("导出请求失败!");
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

    $scope.selectQueue = function (index) {
        console.log("被选中:" + index);
        console.log("选中:" + JSON.stringify($rootScope.cacheQueue[index], null, 2));
        console.log("队列:" + JSON.stringify($rootScope.cacheQueue, null, 2));

        $rootScope.welfareCode = $rootScope.cacheQueue[index];
        $rootScope.wyfCodes =  $rootScope.cacheQueue[index].codes;
        $rootScope.codesCount = $rootScope.wyfCodes.length;
    }

    $scope.delQueue = function (index) {
        console.log("删除队列:" + index);
        console.log(this);

        $rootScope.cacheQueue.splice(index, 1);
    }

    $scope.doCache = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }
        if($rootScope.cache.isCache) {
            console.log("已取消");
            $scope.do_cache = "暂存";
            $rootScope.query_cache = false;
            $rootScope.cache.isCache = false;
            $rootScope.cache.welfareCode = undefined;
        }else{
            console.log("已暂存");
            $rootScope.query_cache = true;
            $scope.do_cache = "取消";
            $rootScope.cache.isCache = true;
            $rootScope.cache.welfareCode = deepCopy($rootScope.welfareCode);
        }

    }

    $scope.add2Queue = function () {
        if(!$rootScope.isPredict){
            handleException("请先完成预测");
            return;
        }

        var obj =  deepCopy($rootScope.welfareCode);

        $rootScope.cacheQueue.push(obj);
        console.log("push:" + JSON.stringify($rootScope.cacheQueue, null, 2));
    }

    $scope.compSelect = function () {
        if($rootScope.cacheQueue.length == 0){
            handleException("预测队列为空，无法执行综合选码");
        }

        var args = {
            "welfareCodes": $rootScope.cacheQueue
        }

        var queueCount = $rootScope.cacheQueue.length;
        $http({
            method:"POST",
            url:"/api/welfare/codes/select",
            data:JSON.stringify(args),
            headers:{
                "Content-Type":"application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
            $rootScope.wyfMessage = compFormat(queueCount, $rootScope.codesCount);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("综合选码执行失败!");
        });
    }

    $scope.minus = function () {
        if(!$rootScope.cache.isCache){
            handleException("请先暂存一组预测码");
            return;
        }

        var args = {
            "subtractor": deepCopy($rootScope.welfareCode),
            "minuend": deepCopy($rootScope.cache.welfareCode)
        }

        $http({
            method:"POST",
            url:"/api/welfare/codes/minus",
            data:JSON.stringify(args),
            headers:{
                "Content-Type":"application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("取余失败!");
        });

    }

    $scope.oneKey = function () {
        var paramArray = [];
        paramArray.push($scope.input_1);
        paramArray.push($scope.input_2);
        paramArray.push($scope.input_3);
        paramArray.push($scope.input_4);

        var filter = {
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

        var args = {
            "riddles": paramArray,
            "targetCodeType": $rootScope.quibinary_first,
            "filterParam":filter
        };

        console.log(JSON.stringify(args));

        $http({
            method:"POST",
            url:"/api/strategy/key",
            data: JSON.stringify(args),
            headers:{
                "Content-Type": "application/json; charset=UTF-8"
            }
        }).then(function success(response) {
            handleResponse(response);
            $rootScope.wyfMessage = oneKeyFormat($rootScope.wyfCodes.length);
        }, function fail(response) {
            console.log("resp:" + JSON.stringify(response.data, null, 2));
            alert("一键预测失败!")
        });

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
        $rootScope.wyfCodes = undefined;
        $rootScope.isPredict = false;
        $rootScope.wyfMessage = "欢迎使用我要发预测系统！！";
        $rootScope.codesCount = 0;
        $rootScope.quibinary_first = 3;
        $rootScope.direct = true;
        $rootScope.group = true;
        $rootScope.do_export = false;
        $rootScope.do_kill = false;
        $rootScope.com_select = false;
        $rootScope.query_cache = false;
        $scope.do_cache = "暂存";

        $rootScope.cache = {};
        $rootScope.cache.isCache = false;
        $rootScope.cache.welfareCode = {};
    }

    function deepCopy(source) {
        var result = {};
        for (var key in source){

            if(source[key] instanceof Array){
                result[key] = source[key].slice(0);
                continue;
            }

            result[key] = typeof source[key] === 'object' ? deepCopy(source[key]) : source[key];
        }

        return result;
    }

    function test() {
        $rootScope.isPredict = true;
        var obj =  {
            "type":"code",
            "data":[
                {
                    "id":6,
                    "name":"gary"
                },
                {
                    "id":7,
                    "name":"tom"
                }
            ]
        };


        $rootScope.welfareCode = obj;
        // console.log(JSON.stringify(obj, null, 2));
    }

    function filterFormat(total, remainder) {
        return "总计 " + total + " 注, 杀码 " + (total - remainder) + " 注, 余 " + remainder + " 注.";
    }

    function oneKeyFormat(total){
        return "一键执行预测，共计 " + total + " 注3D码!"
    }

    function predictFormat(total){
        return "本次预测共计 " + total + " 注3D码!"
    }

    function compFormat(queueCount, total){
        return "参与综合选码队列：" + queueCount + ", 共选出 " + total + " 注3D码.";
    }

});

