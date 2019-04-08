/**
 * 菜单数据格式menu-data
 *  [
 *      {id:'',text:'',style:'',cls:'',link:'',children:[]}
 *  ]
 */
layui.define(['jquery','tool'],function(exports){
    var $ = layui.jquery,tool = layui.tool;
    var menus = [ ];
    var Menu = function(){
        this.render();
    };
    Menu.prototype.render = function(data){
        tool.ajax.send({
            url:'../data/menu.json',
            method:'get',
            async:'false',
            success:function(res){
                $('.layui-nav.layui-nav-tree').html(build(res));
            },
            complete:function(){

            }
        })
    }
    function build(data){
        var menuHtml = [];
        if( $.isArray(data)){
            data.forEach(function(e,i){
                menuHtml.push(' <li class="layui-nav-item">');
                menuHtml.push('   <a href="javascript:;"><i class="'+e.cls+'"></i><em>'+e.text+'</em><span class="layui-nav-more"></span></a>');//<span class="layui-nav-more">
                if(e.children && $.isArray(e.children)){
                    menuHtml.push('<dl class="layui-nav-child">');
                    e.children.forEach(function (c,j) {
                        menuHtml.push('<dd><a href="javascript:;" data-url="'+c.link+'" data-id="'+c.id+'" data-text="'+c.text+'"><span class="l-line"></span>'+c.text+'</a></dd>');
                    });
                    menuHtml.push('</dl>');
                }
                menuHtml.push(' </li>');
            })
        }
        return menuHtml.join('');
    }
    exports('menu',new Menu)
});