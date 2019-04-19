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
        // xhr.innerText='用户名长度需在12个字符内！';
        user_name.value='';
        alert("用户名长度需在12个字符内！");
        return false;

    }

}

function aaa() {
    if (b()){
        return false;
    }
    document.getElementsByName('');
    var xmlhttp=new XMLHttpRequest();
    var xhr=document.getElementById('msg');
    $(document).ready(function () {
            $.ajax({
                url:"/json/test",
                type:"POST",
                datatype:"application/json",
                data:{"user_name":$("#user_name").val(),"user_id":$("#sId").val()},
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
}

//查询密码是否正确
function findPass() {
    var xmlhttp=new XMLHttpRequest();
    var xhr=document.getElementById('msg');
    $(document).ready(function () {
            $.ajax({
                url:"/json/findPass",
                type:"POST",
                datatype:"application/json",
                data:{"user_name":$("#user_name").val(),"user_pwd":$("#oldPass").val()},
                success:function (data) {
                    var str=data.result;
                    if(str=="notsuccess"){
                        xhr.innerText="密码输入错误!";
                    }else {
                        xhr.innerText='';
                        $("#showButton").show();
                        $("#showNew").show();

                    }

                },
                error:function(){
                    alert(xmlhttp.readyState);

                }




                })
    });
}

