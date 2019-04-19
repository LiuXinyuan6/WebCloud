function checkPassword() {
    var xhr=document.getElementById('limit');
    var pwd=document.getElementById('newPassword');
    var newPass = $("#newPassword").val();
    if(checkPass(newPass)<2||newPass.length>16){
        var s="密码长度需在8-16位内，且包含字母和数字!";
        xhr.innerHTML=s;
        pwd.value='';
        return false;
    }
    var s='';
    xhr.innerHTML=s;
    return true;
}

function checkPass(pass){
    if(pass.length < 8){
        return 0;
    }
    var str = 0;
    if(pass.match(/([a-z])+/)){
        str++;
    }
    if(pass.match(/([0-9])+/)){
        str++;
    }
    if(pass.match(/([A-Z])+/)){
        str++;
    }
    if(pass.match(/[^a-zA-Z0-9]+/)){
        str++;
    }
    return str;
}
