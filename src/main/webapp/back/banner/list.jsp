<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        //初始化表格 并查询所有
        $("#banner").jqGrid({
            styleUI: "Bootstrap",
            autowidth: true,
            height: 500,
            url: "${pageContext.request.contextPath}/banner/findAll",
            datatype: "json",
            colNames: ["编号", "标题", "状态", "描述", "创建时间", "图片", "超链接"],
            colModel: [
                {name: "id", hidden: true},
                {name: "title", align: "center", editable: true, editrules: {required: true}},
                {
                    name: "status", align: "center", editable: true, formatter: function (data) {
                        if (data == "1") {
                            return "展示";
                        } else return "冻结";
                    }, editable: true, edittype: "select", editoptions: {value: "1:展示;2:冻结"}
                },
                {name: "description", align: "center", editable: true, editoptions: {required: true}},
                {name: "date", align: "center"},
                {
                    name: "url", align: "center", editable: true,
                    edittype: "file", editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, options, row) {
                        return "<img style='width:200px;height: 100px;' src='" + row.url + "'/>"
                    }
                },
                {name: 'href', align: "center", editable: true, editoptions: {required: true}}
            ],
            pager: "#pager",
            mtype: "post",
            viewrecords: true,
            rowNum: 2,
            rowList: [2, 4, 6],
            multiselect: true,
            editurl: "${pageContext.request.contextPath}/banner/edit",
            toolbar: [true, 'top'],
        }).navGrid(
            '#pager',//参数1: 分页工具栏id
            {edit: true, add: true, del: true, edittext: "编辑", addtext: "添加", deltext: "删除"},   //参数2:开启工具栏编辑按钮
            {
                closeAfterEdit: true, reloadAfterSubmit: true,
            },//编辑面板的配置
            {    //开启添加完成模态框自动关闭
                closeAfterAdd: true, reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    var bannerID = response.responseJSON.id;
                    var status = response.responseJSON.status;
                    if (status == "addok") {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            datatype: "json",
                            type: "post",
                            data: {id: bannerID},
                            // 指定的上传input框的id
                            fileElementId: "url",
                            success: function (data, status) {
                                $("#banner").trigger("reloadGrid");//上传完成后刷新table表格
                            }
                        });
                        return postData;
                    }
                }
            },//添加面板的配置
            {},//删除的配置
            {}//搜索的配置
        );
    });
</script>

<div class="col-sm-10">
    <div class="page-header" style="margin-top: 0px;">
        <h4><strong>轮播图列表</strong></h4>
    </div>
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/banner/list.jsp');">轮播图信息</a>
        </li>
        <li>
        <li class="">
            <a id="export" href="${pageContext.request.contextPath}/banner/export">导出轮播图信息</a></li>
        <li>
        <li class="">
            <a href="#">导出轮播图模板</a></li>
        <li>
        <li class="">
            <a href="#">导入轮播图信息</a></li>
        <li>
    </ul>
    <%--轮播图列表--%>
    <table id="banner"></table>
    <%--分页工具栏--%>
    <div id="pager"></div>
</div>
