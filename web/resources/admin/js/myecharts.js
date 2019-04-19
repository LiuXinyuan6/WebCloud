//制作图表
window.onload=function () {
    //控制单选按钮的显示与隐藏
    var month=new Date().getMonth()+1;//获得当前月份
    var length;
    var label;
    var radio;
    var radioValue;
    if(month>8||month===1){//上学期
        var up=$('#up');//获得上学期的div
        up.show();//显示上学期的div
        length=up.children("label").length;//通过label的个数获得月份个数
        for(var i=9;i<length+9;i++){//进行循环
            label=up.children().eq(i-9);
            radio=label.children().first();
            radioValue=radio.attr("value");//获得按钮的值
            if(month===1){  //如果月份为1，则改写成13
                month=13;
            }
            if(radioValue==1){//如果是1月，则把值改为13（传输后台的数据仍然为1），用==进行比较，因为类型可能不匹配
                radioValue=13;
            }
            if(radioValue<=month){  //控制显示的列表，只显示当月之前的列表，其它隐藏
                label.show();
            }else{
                label.hide();
            }
            if(radioValue==month){
                radio.attr("checked",true);
            }
        }
    }else{ //下学期
        var down=$('#down');
        down.show();//显示上学期的div
        length=down.children("label").length;//通过label的个数获得月份个数
        if(month ===8){
            month=7;
        }
        for(var j=2;j<length+2;j++){
            label=down.children('label').eq(j-2);
            radio=label.children().first();
            radioValue=radio.attr("value");//获得按钮的值
            if(radioValue<=month){
                $(label).show();
            }else{
                $(label).hide();
            }
            if(radioValue == month){
                radio.attr("checked",true);
            }
        }
    }
    //选中当月对应的单选按钮
    sumChart(month);
};
function sumChart(monthValue) {
    $.ajax(
        {
            type:"POST",
            url:"/json/admin/monthChart",
            data:{month:monthValue},
            datatype: "json",
            success:function (cloudChart){
                var myChart = echarts.init(document.getElementById('sum-echarts'));
                //统计出勤总人数以及各年级出勤人数
                var option = {
                    title: {
                        text: monthValue+'月出勤情况:'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            crossStyle: {
                                color: '#999'
                            }
                        },
                        formatter: function (params, ticket, callback) {
                            var htmlStr = '';
                            for(var i=0;i<params.length;i++){//length=3
                                var param = params[i];
                                var xName = param.name;//x轴的名称
                                var seriesName = param.seriesName;//图例名称
                                var value = param.value;//y轴值
                                var color = param.color;//图例颜色
                                var num=xName.split('.')[1];
                                if(i===0){
                                    htmlStr += xName + '<br/>';//x轴的名称
                                }
                                htmlStr +='<div>';
                                //为了保证和原来的效果一样，这里自己实现了一个点的效果
                                htmlStr += '<span style="margin-right:5px;display:inline-block;width:10px;height:10px;border-radius:5px;background-color:'+color+';"></span>';
                                //圆点后面显示的文本
                                htmlStr += seriesName + ':' + value + '<br/>';

                                for(var j = 1; j <= cloudChart.date.length; j++){//length=31 30 29 28
                                    if(i === 1 && j == num && cloudChart.firstMap[j] != null){
                                        for(var k = 1;k <= cloudChart.firstMap[j].length;k++){
                                            htmlStr +=''+cloudChart.firstMap[j][k-1];
                                            if(k%3===0){
                                                htmlStr +='<br/>';
                                            }else if(k!==cloudChart.firstMap[j].length){
                                                htmlStr +=','
                                            }
                                        }

                                    }else if (i===2 && j==num && cloudChart.secondMap[j]!=null){
                                        for(var k=1;k<=cloudChart.secondMap[j].length;k++){
                                            htmlStr +=''+cloudChart.secondMap[j][k-1];
                                            if(k%3===0){
                                                htmlStr +='<br/>';
                                            }else if(k!==cloudChart.secondMap[j].length){
                                                htmlStr +=','
                                            }
                                        }
                                    }

                                }
                                htmlStr += '</div>';
                            }
                            return htmlStr;
                        }
                    },
                    legend: {
                        data:['总人数','大一','大二']
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: cloudChart.date ,
                            axisPointer: {
                                type: 'shadow'
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: '人数',
                            min: 0,
                            max: 35,
                            interval: 5,
                            axisLabel: {
                                formatter: '{value}'
                            }
                        },
                        {
                            type: 'value',
                            min: 0,
                            max: 35,
                            interval: 5,
                            axisLabel: {
                                formatter: '{value} '
                            }
                        }
                    ],
                    series: [
                        {
                            name:'总人数',
                            type:'line',
                            yAxisIndex: 1,
                            data:cloudChart.sum
                        },
                        {
                            name:'大一',
                            type:'bar',
                            data:cloudChart.first
                        },
                        {
                            name:'大二',
                            type:'bar',
                            data:cloudChart.second
                        }
                    ]
                };
                myChart.setOption(option);
            },
            error: function(error){
                alert("ajax error : "+error);
            }
        });
}
$('label').click(function () {
    var month=$(this).children().first().attr("value");
    sumChart(month);
});


