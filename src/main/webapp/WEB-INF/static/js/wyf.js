var global_config = {
    isPredict: false,
    canKill: false,
    canExport: false,
    isP5:false,
    config3: {
        isGroup: true,
        isDirect: true
    }
}


var app = new Vue({
    el:'#app',
    data:{
        sequence1:null,
        sequence2:null,
        sequence3:null,
        sequence4:null,
        sumValue:null,
        boldCode:null,
        wyfMessage:'这一行是统计数据展示区域',
        codesCount: 0,
        wyfCodes:[],
        config:global_config,
        welfareCode: null
    },
    methods:{
        doPermutate: function () {
            var paramArray = [];
            paramArray.push(this.sequence1);
            paramArray.push(this.sequence2);
            paramArray.push(this.sequence3);
            paramArray.push(this.sequence4);
            console.log('input:'+ JSON.stringify(paramArray));
            var args = {
                "riddles": paramArray,
                "targetCodeType": 3
            };
            axios({
              method: 'post',
              url: '/api/welfare/codes/predict',
              data: args
            }).then(function(response) {
                    console.log(response.data.data);
                    app.handleThreeCodeResponse(response.data.data);
                })
                .catch(function(error){
                    console.log(error)
                });

        },
        handleThreeCodeResponse:function (data) {
            if(!data){
                this.wyfMessage='远程服务返回数据为空';
                console.log('请求数据为空');
                return;
            }
            this.welfareCode = data;
            this.wyfCodes = this.welfareCode.codes;
            this.config.isPredict=true;
            this.config.canKill=true;
            this.config.canExport=true;
            if(this.welfareCode.codeTypeEnum == "DIRECT"){
                this.config.config3.isGroup = false;
                this.config.config3.isDirect = true;
                this.wyfMessage = "本次直选预测3D码: " + this.welfareCode.w3DCodes.length + " 注";
            }else {
                this.config.config3.isGroup = true;
                this.config.config3.isDirect = false;
                this.wyfMessage = "本次组选预测3D码: " + this.welfareCode.w3DCodes.length + " 注";
            }
        },

        handleFiveCodeResponse: function (data) {

        },

        transfer2Direct: function () {
            if(!this.config.isPredict){
                this.handleException("请先完成预测");
                return;
            }

            if(this.config.config3.isDirect){
                this.handleException("已经是直选");
                return;
            }

            axios({
                method:"POST",
                url:"/api/welfare/codes/transfer",
                data: app.welfareCode,
                headers:{
                    "Content-Type": "application/json; charset=UTF-8"
                }
            }).then(function (response) {
                console.log("转码成功返回");
                app.handleThreeCodeResponse(response.data.data);
                console.log("转码完成");
                app.wyfMessage = "转码成功, 共计 " + app.welfareCode.w3DCodes.length + " 注.";
            }).catch(function(error){
                console.log("resp:" + JSON.stringify(error, null, 2));
                app.handleException("转换请求失败!");
            });

        },
        killCode: function () {
            if(!this.config.isPredict){
                this.handleException("请先完成预测");
                return;
            }

            var args = {
                "welfareCode": this.welfareCode,
                "sumValue": this.sumValue,
                "boldCode": this.boldCode
            };

            // console.log(JSON.stringify($rootScope.welfareCode, null, 2));
            console.log(JSON.stringify(args, null ,2));
            var count = this.wyfCodes.length;

            axios({
                method:"POST",
                url:"/api/welfare/codes/filter",
                data: JSON.stringify(args),
                headers:{
                    "Content-Type": "application/json; charset=UTF-8"
                }
            }).then(function(response) {
                app.handleThreeCodeResponse(response.data.data);
                app.wyfMessage = "总计 " + count + " 注, 杀码 " + (count - app.welfareCode.w3DCodes.length) + " 注, 余 " + app.welfareCode.w3DCodes.length + " 注.";
            }).catch(function(response) {
                console.log("resp:" + JSON.stringify(response.data, null, 2));
                app.handleException("杀码请求失败!");
            });

        },
        handleException: function (msg) {
            alert(msg);
        }
    }
});

