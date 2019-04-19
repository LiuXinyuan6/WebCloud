//页面加载完成后，直接发送一个Ajax请求，要到分页数据
$(document).ready(function () {
    //页面进来就去第一页
    to_page(1);
});

function to_page(pn) {
    $.ajax({
        url: "/json/rsf/page",
        data: "pn=" + pn,
        type: "POST",
        async:"true",
        dataType:"json",
        success: function (result) {
            //console.log(result);
            //1.解析并显示员工信息
            build_information_table(result);
            //2.解析并显示分页信息
            build_page_info(result);
            //3.解析并显示分页条
            build_page_nav(result);
        }
    });
}
function build_information_table(result) {
    //日期格式转换
    var parserDate = function (date) {
        if (date!=null) {
            return new Date(date).toLocaleDateString();
        } else {
            return new Date().toLocaleDateString();
        }
    };
    //解析文件大小，显示带标注的数值
    var fielSize = function(size){
        if (size<1024){
            size=size+"Byte";
        }else if (size>=1024&&size<=1024*1024){
            size=(size/1024).toFixed(2)+"Kb";
        }else if (size >= 1024*1024&&size<=1204*1024*1024){
            size=(size/(1024*1024)).toFixed(2)+"Mb";
        }
        return size;
    }
    $("#information_table").empty();
    var information = result.extend.pageInfo.list;
    $.each(information, function (index, item) {
        var srcf_NameTd  = $("<h1 class=\"package-no\" style=\"font-size: 12px\"></h1>").append(item.srcf_name);
        var	NameTd = $(" <div class=\"top-part\"></div>").append(srcf_NameTd);
        var srcf_type= $("<li></li>").append("文件类型:"+item.srcf_type);
        var	srcf_date= $("<li></li>").append("文件上传日期:"+ parserDate(item.srcf_date));
        var srcf_size =	$("<li></li>").append("文件大小:"+fielSize(item.srcf_size));
        var UL = $(" <ul></ul>").append(srcf_type).append(srcf_date).append(srcf_size);
        var packageList = $("    <div class=\"package-list\" ></div>").append(UL);
        var bottomH3 = $("<h3></h3>").append("文件描述");
        var bottomH4 = $("<h4></h4>").append(item.srcf_describe);
        var bottomA = $(" <a class=\"price-btn text-uppercase\" id=\"cleck\">获取</a>").attr("href","/rsf/download/"+item.srcf_id);
        var bottomPart = $("<div class=\"bottom-part\"></div>").append(bottomH3).append(bottomH4).append(bottomA);
        var container = $("<div class=\"col-lg-3 col-md-6 single-price\"></div>").append(NameTd).append(packageList).append(bottomPart);
        $("#information_table").append(container);
    });
}

//解析显示分页信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area ").append("当前" + result.extend.pageInfo.pageNum + "页，总共" + result.extend.pageInfo.pages + "页，总共" + result.extend.pageInfo.total + "条记录");
}

//解析显示分页条
function build_page_nav(result) {
    $("#page_nav_area").empty();
    //page_nav_area
    var ul = $("<ul></ul>").addClass("pagination");
    //构建元素
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
    if (result.extend.pageInfo.hasPreviousPage == false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页事件
        firstPageLi.click(function () {
            to_page(1);
        })
        prePageLi.click(function () {
            to_page(result.extend.pageInfo.pageNum - 1);
        })

    }

    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
    if (result.extend.pageInfo.hasNextPage == false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页事件
        lastPageLi.click(function () {
            to_page(result.extend.pageInfo.pages);
        })
        nextPageLi.click(function () {
            to_page(result.extend.pageInfo.pageNum + 1);
        })
    }
    //添加首页和前一页的提示
    ul.append(firstPageLi).append(prePageLi);
    //遍历页码
    $.each(result.extend.pageInfo.navigatepageNums, function (index, item) {
        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.pageInfo.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            to_page(item)
        });
        ul.append(numLi);
    });
    //添加下一页和末页的提示
    ul.append(nextPageLi).append(lastPageLi);
    //把ul加入到nav中
    var navEle = $("<nav></nav>").append(ul);
    navEle.appendTo("#page_nav_area");
}

$("#emp_add_Modal_btn").click(function () {
    $("#empAddModal").modal({
        backdrop: "static"
    });
});