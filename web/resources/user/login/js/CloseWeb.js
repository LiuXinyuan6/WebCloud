var beginnTime=0;
var differTime=0;
var xmlhttp=new XMLHttpRequest();
window.onunload=function () {
    var clear="/json/clear?time"+ new Date().getTime();
    var close="close";
    differTime=new Date().getTime()-beginnTime;
    if(differTime<=5){
        // console.log("close");
        $.ajax({
            type:"POST",
            url:clear,
            dataType:"application/json",
            data:{"word":close},
            success:function (msg) {
                console.log(msg);
            },

            error:function (err) {
                console.log(err);
                alert(xmlhttp.readyState);
            }
        })
    }else {
        console.log("flush");
    }
};

window.onbeforeunload=function () {
    beginnTime=new Date().getTime();
};