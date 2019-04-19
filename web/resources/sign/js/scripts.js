
function sumitFun() {
    if (confirm("确认打卡")) {
        sumitFrom.submit();
        return true;

    } else {
        return false;
    }


}

var timer; //定时器对象
function initClock() {
    showTime();
    timer = window.setInterval("showTime()", 1000);
}

function showTime() {

    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth();
    var day = d.getDate();
    var hours = d.getHours();
    var minutes = d.getMinutes();
    var seconds = d.getSeconds();
    month++;
    month = month < 10 ? "0" + month : month;
    day = day < 10 ? "0" + day : day;
    hours = hours < 10 ? "0" + hours : hours;
    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;

    var time = year + "年" + month + "月" + day + "日  " + hours + ":" + minutes + ":" + seconds;
    document.getElementById("box").innerHTML = time;
}

showTime();