$(function(){
    var test="/edit/test?time"+ new Date().getTime();
    var reWorld="/edit/reWorld?time"+new Date().getTime();
    $('#myself').on('click',function(){//
        $(window.parent.document).find('#contentIframe').attr('src',test);
        $(this).addClass('layui-this').siblings().removeClass('layui-this');
    })

    $('#rePass').on('click',function(){//
        $(window.parent.document).find('#contentIframe').attr('src',reWorld);
        $(this).addClass('layui-this').siblings().removeClass('layui-this');
    })


})