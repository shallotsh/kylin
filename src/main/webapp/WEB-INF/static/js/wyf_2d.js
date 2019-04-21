var global_config = {
    isPredict: false,
    canKill: false,
    canExport: false,
    queueIndex: 1
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
        config: global_config,
        cacheQueue: new Array(),
        compItems: [],
        drawNotice: null,
        drawNoticeOverview: ''
    },
    created: function(){
        this.export_format = 0;
    },
    mounted: function(){
        axios.get("/api/3d/draw/notice?", {
            params: {
                name: '3d',
                issueCount: 1
            }
        }).then(function (resp) {
            this.drawNotice = resp.data.data;
            var latestDrawRet = this.drawNotice.result[0];
            var desc = "开奖期数: 【" + latestDrawRet.code
                + " 】（" + latestDrawRet.date + "），中奖号码: 【"
                + latestDrawRet.red + "】";

            app.drawNoticeOverview = desc;

        }).catch(function (reason) {
            console.log("3d resp error:" + JSON.stringify(reason));
        });
    },
    methods:{
        doPermutate: function () {

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
            this.config.isPredict = false;
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

        addQueue: function () {
            if(!this.config.isPredict){
                this.handleException("请先完成预测");
                return;
            }

            var obj =  deepCopy(this.wCodes);

            this.cacheQueue.push(obj);

            console.log('入队成功.');
        },



        delQueue: function(cursor){
            this.cacheQueue.splice(cursor, 1);
            console.log('delete item from queue, index='+cursor);
        },

        selectQueue: function(cursor){
            console.log('cursor=' + cursor);
            this.wCodes = this.cacheQueue[cursor];
            var count = 0;

            for(var idx in this.compItems){
                count += this.cacheQueue[this.compItems[idx]].length;
                console.log('idx=' + idx + ',cpm[idx]='+this.compItems[idx] + ',cursor='+ cursor);
                if(this.compItems[idx] != cursor){
                    this.wCodes = this.wCodes.concat(this.cacheQueue[this.compItems[idx]]);
                }
            }
            this.wyfMessage = '已选择队列【' + this.compItems + '】共计 ' + count + ' 注二码.';
            console.log('Item(index='+this.compItems+' in the cache queue was selected.');
        },

        compSelect: function () {
            if(this.cacheQueue.length < 1){
                this.handleException("预测队列为空，请先添加预测码到队列");
                return;
            }

            var selectedQueues = new Array();

            if(this.compItems.length < 1){

                for(var idx=0; idx < this.cacheQueue.length; idx ++){
                    selectedQueues.push({index: idx, wCodes: this.cacheQueue[idx]});
                }

            }else {
                for (var idx in this.compItems) {
                    selectedQueues.push({index: idx, wCodes: this.cacheQueue[idx]});
                }
            }

            var args = {
                "xCodePairs": selectedQueues
                // "arrayIndexes": this.compItems
            };
            console.log("compArgs:" + JSON.stringify(args));
            this.wyfMessage = "正在计算...";
            axios({
                method: 'post',
                url: '/api/2d/comp/select',
                data: JSON.stringify(args),
                headers:{
                    "Content-Type": "application/json; charset=UTF-8"
                }
            }).then(function(response) {
                app.handle2DCodeResponse(response.data.data, '综合选码');
            })
                .catch(function(error){
                    console.log(error)
                });



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

            console.log('args' + JSON.stringify(args));

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
            if(!this.config.isPredict){
                this.handleException("请至少先完成一次预测");
                return;
            }

            var args = {
                wCodes: this.wCodes,
                freqSeted: this.freqSeted
            };

            // console.log('canshu:' + JSON.stringify(args, null, 2));
            axios({
                method:"POST",
                url:"/api/2d/codes/export",
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
                var codeString = code.codes.join("");
                if(this.freqSeted){
                    codeString = '[' + code.freq + ']' + codeString;
                }
                printCodes.push(codeString);
            }
            return printCodes;
        },
        drawNoticeDesc: function(){

            if(this.drawNotice == null
                || this.drawNotice.state != 0
                || this.drawNotice.result.length <= 0) {
                console.log("参数问题");
                return "";
            }

            var latestDrawRet = this.drawNotice.result[0];
            var desc = "开奖期数: 【" + latestDrawRet.code
                + " 】（" + latestDrawRet.date + "），中奖号码: 【"
                + latestDrawRet.red + "】";

            console.log("desc : " + desc);
            return desc;
        }
    }
});

function deepCopy(source) {
    if(source instanceof  Array){
        return source.slice(0);
    }
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
