layui.define(['layer'], function (exports) {
    var $ = layui.$,layer = layui.layer,
        tool = {
        array:{
            /**
             * 移除数组的值
             * @param arr   数组
             * @param val   删除的值
             */
            removeByValue: function (arr, val) {
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] == val) {
                        arr.splice(i, 1);
                        break;
                    }
                }
            }
        },
        string:{
            /**
             * 转义字符，防止xxs
             * @param str
             * @returns {string}
             */
            stringEncode:function (str){
                var div=document.createElement('div');
                if(div.innerText){
                    div.innerText=str;
                }else{
                    div.textContent=str;//Support firefox
                }
                return div.innerHTML;
            }
        },
        ajax:{
            send:function(options){
                var that = this;
                if(!options.url){
                    layer.msg('没有ajax url参数！',{icon:2});
                    return;
                }
                if(options.notShowLoading!=true){
                    _showLoading(0,options.loadingText);//
                }
                var contextPath = "/";
                if(contextPath&&options.url.substring(0,1)=='/'
                    &&options.url.indexOf(contextPath)==-1){
                    options.url = contextPath+options.url.substring(1);
                }

                var _options = $.extend({},{
                    dataType:'json',
                    type:'POST',
                    contentType:'application/x-www-form-urlencoded;charset=UTF-8',//配置提交的contentType
                    headers:{'Authorization':''},
                    error:function(jqXHR,statusText,responseText){
                        if(statusText=='error'){
                            layer.msg(errorMessage,{icon:2});
                            oldError.apply(_options,[errorMessage]);
                        }
                    }
                },options);

                var oldSuccess = _options.success || function(){};
                var oldError =  _options.error || function(){};

                _options.complete = function(jqXHR, statusText, responseText){
                    _hideLoading();//关闭进度显示
                }

                _options.success = function(results){
                    that.resultsCheck(results,$.extend({},this,{
                        error:oldError
                    }))&&oldSuccess.apply(_options,[results]);
                };

                return $.ajax(_options);
            },
            resultsCheck:function(results,options){
                if(results==null)return false;
                if(results.hasError){
                    layer.msg(results.errorMsg,{icon:2});
                    options.error.apply(options,[results.errorMsg]);
                    return false;
                }

                var message = results.message;
                var checkFlag = true;
                if(message&&message.code){
                    var code = message.code;
                    switch(code){
                        case '0000'://成功

                            break;
                        case '9999'://失败
                            layer.msg(code+':'+message.info,{icon:2});
                            checkFlag = false;
                            // window.location.href = '';//刷新页面
                            return;
                        default:
                            layer.msg(message.info,{icon:2});
                            options.error.apply(options,[message.info]);
                            checkFlag = false;
                            return;
                    }
                }
                return checkFlag;
            }
        }
    };

    exports('tool', tool);
});  