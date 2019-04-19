function aaaa() {

if (b()){
    return false;
}
    var xmlhttp=new XMLHttpRequest();
    var xhr=document.getElementById('msg');
    $(document).ready(function () {
        $("#newPassword").click(function () {
            $.ajax({
                url:"/json/register",
                type:"POST",
                datatype:"application/json",
                data:{"user_name":$("#user_name").val()},
                success:function (data) {

                    var str=data.result;
                    if(str=="success"){
                        xhr.innerText='';
                    }else {
                        xhr.innerText="该用户名不可用!";
                    }

                },
                error:function(){
                    alert(xmlhttp.readyState);

                }



            })
        });
    });
}
function b() {

    var user_name=document.getElementById('user_name');
    var xhr=document.getElementById('msg');
    var name = $("#user_name").val();
    if (name.length>12){
        xhr.innerText='用户名长度需在12个字符内！';
        user_name.innerText='';
        return false;

    }

}
