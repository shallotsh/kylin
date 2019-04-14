var global_config = {
    isPredict: false,
    canKill: false,
    canExport: false
}


var app = new Vue({
    el:'#app',
    data:{
        sequence1:'',
        sequence2:'',
        sequence3:'',
        sequence4:'',
        boldCodeSeq: null,
        gossipCodeSeq: null,
        inverseCodeSeq: null,
        wCodes: null,
        wyfMessage:'这一行是统计数据展示区域',
        config:global_config,

        sumValue:null,
        codesCount: 0,
        welfareCode: null,
        p3Code:null
    },
    created: function(){
        this.export_format = 0;
    },
    methods:{
        doPermutate: function () {

            alert("暂未开放该功能");
            return;

            var paramArray = [];
            paramArray.push(this.sequence1);
            paramArray.push(this.sequence2);
            paramArray.push(this.sequence3);
            paramArray.push(this.sequence4);

            var args = {
                "sequences": paramArray
            };
            console.log("sequences:" + JSON.stringify(args));
            this.wyfMessage = "正在计算...";
            axios({
              method: 'post',
              url: '/api/2d/shuffle',
              data: args
            }).then(function(response) {
                    app.handle2DCodeResponse(response.data.data, '定位2D组码');
                })
                .catch(function(error){
                    console.log(error)
                });

        },

        handle2DCodeResponse: function (data, msg) {
            this.wCodes = data.wCodes;
            if(data.freqSeted) {
                this.freqSeted = data.freqSeted;
            }
            this.config.isPredict = true;
            this.wyfMessage =  msg + " : "  + this.wCodes.length + " 注" ;
        },

        resetInput: function () {
            this.sequence1 ='',
            this.sequence2 ='',
            this.sequence3 ='',
            this.sequence4 ='',
            this.boldCodeSeq = null,
            this.gossipCodeSeq = null,
            this.inverseCodeSeq = null,
            this.wyfMessage = '这一行是统计数据展示区域',
            this.wCodes = null

        },

        invertSelCode: function () {

        },

        addQueue: function () {

        },

        compSelect: function () {

        },

        handleDownload: function(data) {
            console.log("downloads:"+data);
            if(data == null){
                console.log("request error.");
                return;
            }
            window.location = "/api/welfare/download?fileName=" + data;
        },

        killCode: function () {
            if(!this.config.isPredict){
                this.handleException("请先完成预测");
                return;
            }

            var args = {
                "wCodes": this.wCodes,
                "boldCodeSeq": this.boldCodeSeq,
                "inverseCodeSeq": this.inverseCodeSeq,
                "gossipCodeSeq": this.gossipCodeSeq
            };

            var count = this.wCodes.length;

            axios({
                method:"POST",
                url:"/api/2d/kill/code",
                data: args,
                headers:{
                    "Content-Type": "application/json; charset=UTF-8"
                }
            }).then(function(response) {
                app.handle2DCodeResponse(response.data.data, "定位2码杀码");
                app.wyfMessage = "总计 " + count + " 注, 杀码 " + (count - app.wCodes.length) + " 注, 余 " + app.wCodes.length + " 注.";
            }).catch(function(response) {
                console.log("resp:" + JSON.stringify(response.data, null, 2));
                app.handleException("杀码请求失败!");
            });

        },

        exportCodes: function(){
            if(!this.config.isP5){
                this.handleException("请先完成排5");
                return;
            }

            var args = {
                wCodes: this.welfareCode ,
                randomCount: this.boldCodeFive,
                randomKill: this.isRandomKill,
                freqSeted : this.freqSeted,
                exportFormat: this.export_format,
                deletedCodesPair: this.deletedCodesPair
            };

            // console.log('canshu:' + JSON.stringify(args, null, 2));
            axios({
                method:"POST",
                url:"/api/p5/codes/export",
                data: JSON.stringify(args),
                headers:{
                    "Content-Type": "application/json; charset=UTF-8"
                }
            }).then(function(response) {
                app.handleDownload(response.data.data);
            }).catch(function(reason) {
                console.log(reason);
                app.handleException("导出请求失败!");
            });
        },

        exportCodesHalfPage: function(){
            if(!this.config.isP5){
                this.handleException("请先完成排5");
                return;
            }

            var args = {
                wCodes: this.welfareCode
            };

            // console.log(JSON.stringify($rootScope.welfareCode, null, 2));
            axios({
                method:"POST",
                url:"/api/p5/codes/export/half",
                data: JSON.stringify(args),
                headers:{
                    "Content-Type": "application/json; charset=UTF-8"
                }
            }).then(function(response) {
                app.handleDownload(response.data.data);
            }).catch(function(reason) {
                console.log(reason);
                app.handleException("导出请求失败!");
            });
        },

        handleException: function (msg) {
            alert(msg);
        }
    },

    computed: {
        wyfCodes: function(){
            var printCodes = [];
            for( idx in this.wCodes){
                code = this.wCodes[idx];
                // code.codes.reverse();
                var codeString = code.codes.join("") + '-' + code.codes.reduce(function (preValue,curValue,index,array) {
                    return preValue + curValue;
                },0)%10;
                if(this.freqSeted){
                    codeString = '[' + code.freq + ']' + codeString;
                }
                printCodes.push(codeString);
            }
            return printCodes;
        }
    }
});

