window.onload=function exportSign(){
    var month=new Date().getMonth()+1;//获得当前月
    var length;
    var name;
    var button_class;
    var value;
    if(month>8||month===1){//下学期
        $(".col_3_down").hide();//隐藏下学期的列表
        length=$(".col_3_up").children("div").length-1;//获得月份个数,长度减去1是因为有一个div用来清除浮动了
        for(var i=9;i<length+9;i++){//进行循环
            name="#month_"+i;   //拼接月份div的id
            button_class=name+" .exportButton";   //拼接当月下的button的class
            value=$(button_class)[0].value;   //获得第一个按钮的值，即月份
            if(month===1){  //如果月份为1，则改写成13
                month=13;
            }
            if(value==1){//如果是1月，则把值改为13（传输后台的数据仍然为1），用==进行比较，因为类型可能不匹配
                value=13;
            }
            if(value<=month){  //控制显示的列表，只显示当月之前的列表，其它隐藏
                $(name).show();
            }else{
                $(name).hide();
            }
        }
    }else{ //上学期
        $(".col_3_up").hide();
        length=$(".col_3_down").children("div").length-1;
        for(var j=2;j<length+2;j++){
            name="#month_"+j;
            button_class=name+" .exportButton";
            value=$(button_class)[0].value;
            if(value<=month){
                $(name).show();
            }else{
                $(name).hide();
            }
        }
    }
};

//用来向后台传输月份，以及将返回的数据进行拼接导出
$(".exportButton").click(function(){
    var value=this.value;
    $.ajax({
        //提交数据的类型 POST GET
        type:"POST",
        //提交的网址
        url:"/json/admin/export",
        //提交的数据
        data:{month:this.value},
        //返回数据的格式
        datatype: "json",
        //成功返回之后调用的函数
        success:function (data){
            var year=new Date().getFullYear();//2019
            var month=value;//点击的月份
            var days; //声明数组，存放具体天数,数组中的0主要为了占位，循环时匹配姓名列
            if(!!window.ActiveXObject || "ActiveXObject" in window){//ie浏览器
                var ie_data ="<table style='border-spacing 0;border-collapse: collapse;border-bottom: 0.5px solid #D4D4D4;border-right: 0.5px solid #D4D4D4;font-family: 宋体,serif;font-size: 14px' align='center'>" +
                    "<tr style='width:89px;height:24px;border-bottom: 0.5px solid #D4D4D4;border-right: 0.5px solid #D4D4D4;'><td>姓名</td>";
                year=new Date().getFullYear();//2019
                month=value;//月份是从0开始，所以需要+1
                days=[0]; //声明数组，存放具体天数,数组中的0主要为了占位，循环时匹配姓名列

                if((["1","3","5","7","8","10","12"].indexOf(month))!==-1){//31天
                    for(var i=1;i<=31;i++){
                        ie_data+="<td>"+month+"-"+i+"</td>" ;//,3.20
                        days.push(month+"-"+i);//将具体日期放在数组中 3,20
                    }
                    ie_data+="</tr>"; //换行，准备输出数据
                }else if((["4","6","9","11"].indexOf(month))!==-1){//30天
                    for(var i=1;i<=30;i++){
                        ie_data+="<td>"+month+"-"+i+"</td>" ;
                        days.push(month+"-"+i);
                    }
                    ie_data+="</tr>";
                }else if( year%4===0 && year%100!==0 || year%400===0){//闰年2月29天
                    for(var i=1;i<=29;i++){
                        ie_data+="<td>"+month+"-"+i+"</td>" ;
                        days.push(month+"-"+i);
                    }
                    ie_data+="</tr>";
                }else{ //平年2月28天
                    for(var i=1;i<=28;i++){
                        ie_data+="<td>"+month+"-"+i+"</td>" ;
                        days.push(month+"-"+i);
                    }
                    ie_data+="</tr>";
                }

                //将数据添加进表格，判断签到日期是否匹配
                //增加\t为了不让表格显示科学计数法或者其他格式
                for(var i = 0 ; i < data.length ; i++ ){//遍历次数为总人数
                    var isName=true;    //用来标识每一行的姓名列，isName=true说明是每一行的第一列
                    ie_data+="<tr style='width 89px;height:24px;border-bottom: 0.5px solid #D4D4D4;border-right: 0.5px solid #D4D4D4;'>";
                    for(var k=0; k<days.length; k++){//遍历次数为每个月的天数
                        var isWrite=false;  //用来标识是否对表格进行了写入
                        for(var item in data[i]){//遍历次数为每个人的数据长度，jsonData数据类型：张三，3-1，3-2 ......
                            if(isName===true){  //第一次进入循环直接将姓名进行拼接
                                ie_data+="<td>"+data[i][item]+"</td>";//写入名字
                                isName=false;
                                isWrite=true;
                                break;
                            }else{      //从第二次开始检查日期是否匹配，如果匹配则写入内容跳出循环
                                if(days[k]===data[i][item]){
                                    ie_data+="<td>"+"√" + "</td>";//打上对勾
                                    isWrite=true;
                                    break;
                                }else{  //如果不匹配进行下一次比较，直至完成
                                    isWrite=false;
                                }
                            }
                        }
                        if(isWrite===false){ //完成所有匹配后判断是否写入内容，如果没有则用空白填充
                            ie_data+="<td>"+" " +"</td>";//空白填充
                        }
                    }
                    ie_data+="</tr>";
                }
                for(var l=0;l<50;l++){//优化表格，无实质作用
                    ie_data+="<tr style='width 89px;height:24px;border-bottom: 0.5px solid #D4D4D4;border-right: 0.5px solid #D4D4D4;'>";
                    ie_data+="<td>"+" " +"</td></tr>";
                }
                //ie浏览器下的excel提交方式（拼接成html格式的表格代码）
                var tableHtml='<html><head><meta charset="UTF-8"></head><body>';
                tableHtml += ie_data;  //将拼接的表格数据拼接至html中
                tableHtml += '</body></html>';
                var excelBlob = new Blob([tableHtml], {type: 'application/vnd.ms-excel'});
                var fileName = month+"月份签到表.xls";
                window.navigator.msSaveOrOpenBlob(excelBlob,fileName);

            }else{//其它浏览器
                //列标题，逗号隔开，每一个逗号就是隔开一个单元格
                var str = "姓名";
                //将日期添加进字符串
                //首先获得月份，判断当月有多少天
                year=new Date().getFullYear();//2019
                month=value;
                days=[0]; //声明数组，存放具体天数,数组中的0主要为了占位，循环时匹配姓名列

                if((["1","3","5","7","8","10","12"].indexOf(month))!==-1){//31天
                    for(var i=1;i<=31;i++){
                        str+=","+month+"-"+i; //,3-20
                        days.push(month+"-"+i);//将具体日期放在数组中 3-20
                    }
                    str+="\n"; //换行，准备输出数据
                }else if((["4","6","9","11"].indexOf(month))!==-1){//30天
                    for(var i=1;i<=30;i++){
                        str+=","+month+"-"+i;
                        days.push(month+"-"+i);
                    }
                    str+="\n";
                }else if( year%4===0 && year%100!==0 || year%400===0){//闰年2月29天
                    for(var i=1;i<=29;i++){
                        str+=","+month+"-"+i;
                        days.push(month+"-"+i);
                    }
                    str+="\n";
                }else{ //平年2月28天
                    for(var i=1;i<=28;i++){
                        str+=","+month+"-"+i;
                        days.push(month+"-"+i);
                    }
                    str+="\n";
                }
                //将数据添加进表格，判断签到日期是否匹配
                //增加\t为了不让表格显示科学计数法或者其他格式
                for(var i = 0 ; i < data.length ; i++ ){//遍历次数为总人数
                    var isName=true;    //用来标识每一行的姓名列，isName=true说明是每一行的第一列
                    for(var k=0; k<days.length; k++){//遍历次数为每个月的天数
                        var isWrite=false;  //用来标识是否对表格进行了写入
                        for(var item in data[i]){//遍历次数为每个人的数据长度，jsonData数据类型：张三，3-1，3-2 ......
                            if(isName===true){  //第一次进入循环直接将姓名进行拼接
                                str+=data[i][item]+"\t,";//写入名字
                                isName=false;
                                isWrite=true;
                                break;
                            }else{      //从第二次开始检查日期是否匹配，如果匹配则写入内容跳出循环
                                if(days[k]===data[i][item]){
                                    str+="√" + "\t,";//打上对勾
                                    isWrite=true;
                                    break;
                                }else{  //如果不匹配进行下一次比较，直至完成
                                    isWrite=false;
                                }
                            }
                        }
                        if(isWrite===false){ //完成所有匹配后判断是否写入内容，如果没有则用空白填充
                            str+=" " +"\t,";//空白填充
                        }
                    }
                    str+='\n';
                }
                //encodeURIComponent解决中文乱码
                var uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
                //通过创建a标签实现
                var link = document.createElement("a");
                link.href = uri;
                //对下载的文件命名
                link.download = month+ "月份签到表.csv";
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }

        },
        //调用出错执行的函数
        error: function(error){
            alert("ajax error : "+error);
        }
    });
});