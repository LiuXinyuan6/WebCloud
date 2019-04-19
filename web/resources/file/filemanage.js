 //页面加载完成后，直接发送一个Ajax请求，要到分页数据
    $(function () {
        //页面进来就去第一页
        to_page(1);

    });

function to_page(pn) {
    $.ajax({
        url: "http://localhost:8080/rsf/admin/FileManagePaging",
        data: "pn=" + pn,
        type: "GET",
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

//把文件大小设置成正常格式(上传文件的大小以bit的形式存入数据库)
function fielSize(size){
    if (size<1024){
        size=size+"Byte";
    }else if (size>=1024&&size<=1024*1024){
        size=(size/1024).toFixed(2)+"Kb";
    }else if (size >= 1024*1024&&size<=1204*1024*1024){
        size=(size/(1024*1024)).toFixed(2)+"Mb";
    }
    return size;
}

//timestampToString用来拼接时间格式的
function add(m) {
    return m<10?'0'+m:m
}
//把从数据库拿到的时间戳解析成正确格式(上传文件时间以时间戳的形式存入数据库)
function timestampToString(timestamp) {
    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = time.getMonth()+1;
    var day = time.getDate();
    return year+'-'+add(month)+'-'+add(day);
}

//构建显示信息表格
function build_information_table(result) {
    //清空数据表
    $("#information_table tbody").empty();
    var information = result.extend.pageInfo.list;
    //把上传文件的大小以正常格式显示在页面上

    $.each(information, function (index, item) {
        var srcf_size = fielSize(item.srcf_size);

        var srcf_date = timestampToString(item.srcf_date);

        //var srcf_date=new Date(parseInt(item.srcf_date)).toLocaleString().substr(0,10);
        var ckeckBoxTd = $("<td><input type='checkbox' class='check_item' /></td>");
        var srcf_UserIdTd = $("<td></td>").append(item.user_id);
        var srcf_NameTd = $("<td></td>").append(item.srcf_name);
        var srcf_SizeTd = $("<td></td>").append(srcf_size);
        var srcf_DateTd = $("<td></td>").append(srcf_date);
        var srcf_StatusTd = $("<td></td>").append(item.srcf_status);
        var srcf_Describe = $("<td></td>").append(item.srcf_describe);
        var srcf_IdTd = $("<td></td>").append(item.srcf_id);

        /**
         * 构建button按钮元素
         * <button class="btn btn-primary btn-sm">
         * <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
         *  编辑
         * </button>
         * <button class="btn btn-danger btn-sm">
         * <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
         *  删除
         * </button>
         *
         */
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm check_btn").append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("审核"));
        //给editBtn添加一个自定义属性，用来获取相应的文件ID
        editBtn.attr("information-id",item.srcf_id);
        var deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn").append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        //给deleteBtn添加一个自定属性，用来获取相应的文件ID
        deleteBtn.attr("resource-id",item.srcf_id);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(deleteBtn);

        //append()方法执行完以后还是返回原来的元素
        $("<tr></tr>").append(ckeckBoxTd).append(srcf_IdTd).append(srcf_NameTd).append(srcf_SizeTd).append(srcf_DateTd).append(srcf_Describe).append(srcf_StatusTd).append(srcf_UserIdTd).append(btnTd).appendTo("#information_table tbody");
    });
}
var currentPage;
//解析显示分页信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area ").append("当前" + result.extend.pageInfo.pageNum + "页，总共" + result.extend.pageInfo.pages + "页，总共" + result.extend.pageInfo.total + "条记录");

    currentPage = result.extend.pageInfo.pageNum;
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

//模态框显示要审核数据的信息
$(document).on("click",".check_btn",function () {
    getInformationById($(this).attr("information-id"));
    //把当前数据的id传递给审核通过按钮
    $("#pass_check_btn").attr("information-id",$(this).attr("information-id"));

    $("#InformationCheckModal").modal({
        backdrop:"static"
    });
});

//单个删除按钮
$(document).on("click",".delete_btn",function () {
    var FileName = $(this).parents("tr").find("td:eq(2)").text();
    var resourceId = $(this).attr("resource-id");
    //confirm确认删除返回true,否则返回false
    if(confirm("确认删除【"+FileName+"】吗？")){
        $.ajax({
            url:"http://localhost:8080/rsf/admin/deleteoneresource/" + resourceId,
            type:"GET",
            success:function (result) {
                alert(result.msg);
                to_page(currentPage);
            }
        })
    }

    //alert(FileName);
});

//根据id查询数据(审核数据时调用显示在模态框中)
function getInformationById(id) {
    $.ajax({
        url:"http://localhost:8080/rsf/admin/getInformationById/" + id,
        type:"GET",
        success:function (result) {
            //console.log(result);
            var cloudResourceFile = result.extend.information;
            var srcf_size = fielSize(cloudResourceFile.srcf_size)
            var srcf_date = new Date(parseInt(cloudResourceFile.srcf_date)).toLocaleString().substr(0,10)
            $("#information_FileId_static").text(cloudResourceFile.srcf_id);
            $("#information_FileName_static").text(cloudResourceFile.srcf_name);
            $("#information_FileType_static").text(cloudResourceFile.srcf_type);
            $("#information_FileSize_static").text(srcf_size);
            $("#information_FileDate_static").text(srcf_date);
            $("#information_FileStatus_static").text(cloudResourceFile.srcf_status);
            $("#information_FileDescribe_static").text(cloudResourceFile.srcf_describe);

        }

    });
}
//模态框中审核数据
$("#pass_check_btn").click(function () {
    //
    $.ajax({
        url:"http://localhost:8080/rsf/admin/passcheck/" + $(this).attr("information-id"),
        type:"GET",
        success:function (result) {
            alert(result.msg);
            $("#InformationCheckModal").modal("hide");
            to_page(currentPage);
        }

    });
});

//全选/全不选 实现
$("#check_all").click(function () {
    //attr获取checked是undefined
    //源生的dom属性通过prop获取属性值；attr用来获取自定义属性的值
    $(this).prop("checked");
    $(".check_item").prop("checked",$(this).prop("checked"));
});

//check_item如果页面上的数据全被选中，全选框打对勾
$(document).on("click",".check_item",function () {
    //判断当前元素是否为页面所显示的条数
    var flag = $(".check_item:checked").length==$(".check_item").length;
    $("#check_all").prop("checked",flag);
})

//点击全部删除，就批量删除
$("#information_delete_all_btn").click(function () {
    var FileNames = "";
    var deleteId_str = "";
    $.each($(".check_item:checked"),function () {
        FileNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
        deleteId_str += $(this).parents("tr").find("td:eq(1)").text() + "-";
    });
    //截去FileNames中多余的 “,”
    FileNames = FileNames.substring(0,FileNames.length-1);
    deleteId_str = deleteId_str.substring(0,deleteId_str.length-1);
    if (confirm("确认删除【"+FileNames+"】吗？")){
        //确认就发送ajax请求
        $.ajax({
            url:"http://localhost:8080/rsf/admin/deleteoneresource/" + deleteId_str,
            type:"GET",
            success:function (result) {
                alert(result.msg);
                to_page(currentPage);
            }
        });
    }
});
