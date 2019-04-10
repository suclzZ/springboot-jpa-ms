layui.define(['layer','app'], function (exports) {
    var $ = layui.$,layer = layui.layer,app = layui.app,
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
            object:{
                //
                objConvert : function(obj){
                    if(obj && $.type(obj) == 'object'){
                        return this.reBuildObj(obj);
                    }
                    return obj
                },
                reBuildObj:function (obj,prefix){
                    var elem = {},prefix = prefix||'';
                    if($.type(obj) == 'object'){
                        for(var p in obj){
                            var _elem = this.reBuildObj(obj[p],prefix+'.'+p);
                            $.extend(elem,_elem);
                        }
                    }else{
                        prefix && (elem[prefix.substring(prefix.indexOf('.')+1)]=obj)
                        //   prefix &&( prefix.indexOf('.')==0?(elem[prefix.substring(1)] = obj): (elem[prefix] = obj));
                    }
                    return elem;
                },
                /**
                 * springmvc list接收数据类型prop[0].ele=格式
                 * {a:'1',b:{name:'zs'},c:[{x:'11',y:'22'}],d:['x','y']} ->
                 * {a:'1',b.name:'zs},c[0].x:'11',c[0].y:'22',d[0]:'x',d[1]:'y'}
                 * @param obj
                 */
                param:function(obj){
                    var param = {};
                    if(typeof obj == 'object'){
                        for(var p in obj){
                            var v = obj[p];
                            if($.type(v) == 'string' ||$.type(v) == 'number'){//{a:1,b:2}
                                param[p] = obj[p];
                            }else if($.type(v) == 'object'){//{a:{name:''},b:{name:''}}
                                for(var p1 in v){
                                    param[p+'.'+p1] = v[p1];
                                }
                            }else if($.type(v) == 'array'){
                                if($.type(v[0])=='string'||$.type(v) == 'number'){
                                    param[p+'['+i+']'] = v[i];
                                }else if($.type(v[0])=='object'){
                                    for(var i in v){
                                        for(var p2 in v[i]){
                                            param[p+'['+i+'].'+p2] = v[i][p2];
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return param;
                }
            },
            msg:{
                alert:function(info){
                    layer.alert(info);
                },
                ok:function (info) {
                    layer.msg(info,{icon:1,offset: 't'});
                },
                warn:function (info) {
                    layer.msg(info,{icon:7,offset: 't'});
                },
                err:function (info) {
                    layer.msg(info,{icon:2});
                },
                confirm:function (info,callback) {
                    layer.confirm(info, {icon: 3, title:'提示'}, function(index){
                        callback && callback.apply(this);
                        layer.close(index);
                    });
                }
            },
            http:{
                ajax:function(options){
                    var that = this;
                    if(!options.url){
                        tool.msg.warn('没有ajax url参数！');
                        return;
                    }
                    if(options.notShowLoading!=true){
                        that._showLoading(0,options.loadingText);//
                    }
                    var contextPath = app.contextPath;
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
                                var errorMessage = responseText||'请求错误：'+jqXHR.status;
                                tool.msg.warn(errorMessage);
                                oldError.apply(_options,[errorMessage]);
                            }
                        }
                    },options);

                    var oldSuccess = _options.success || function(){};
                    var oldError =  _options.error || function(){};

                    _options.complete = function(jqXHR, statusText, responseText){
                        that._hideLoading();//关闭进度显示
                    }

                    _options.success = function(results){
                        that.resultsCheck(results,$.extend({},this,{
                            error:oldError
                        }))&&oldSuccess.apply(_options,[results]);
                    };
                    return $.ajax(_options);
                },
                resultsCheck:function(result,options){
                    if(result==null)return false;
                    var message = result.info,code = result.code, checkFlag = true;
                    switch(code){
                        case '0000'://成功
                            // options.success.apply(options,[result,message]);
                            break;
                        case '9999'://失败
                            tool.msg.err(code+':'+message);
                            checkFlag = false;
                            break;
                        default:
                            tool.msg.warn(message);
                            options.error.apply(options,[message]);
                            checkFlag = false;
                            break;
                    }
                    return checkFlag;
                },
                _showLoading:function(){
                    // layer.load();
                },
                _hideLoading:function () {
                    // layer.close();
                }
            }
        };

    exports('tool', tool);
});  