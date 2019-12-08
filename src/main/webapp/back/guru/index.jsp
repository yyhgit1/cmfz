<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        //初始化表格 并查询所有
        $("#guru").jqGrid({
            styleUI: "Bootstrap",
            autowidth: true,
            height: 500,
            url: "${pageContext.request.contextPath}/guru/findAllf",
            datatype: "json",
            colNames: ["编号", "姓名", "状态", "昵称", "头像"],
            colModel: [
                {name: "id", hidden: true},
                {name: "name", align: "center", editable: true, editrules: {required: true}},
                {
                    name: "status", align: "center", editable: true, formatter: function (data) {
                        if (data == "展示") {
                            return "展示";
                        } else if (data == "不展示") return "不展示";
                    }, editable: true, edittype: "select", editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name: "nick_name", align: "center", editable: true, editoptions: {required: true}},
                {
                    name: "photo", align: "center", editable: true,
                    edittype: "file", editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, options, row) {
                        return "<img style='width:200px;height: 100px;' src='" + row.photo + "'/>";
                    }
                }
            ],
            pager: "#pager",
            mtype: "post",
            viewrecords: true,
            rowNum: 2,
            rowList: [2, 4, 6],
            multiselect: false,
            editurl: "${pageContext.request.contextPath}/guru/edit",
            toolbar: [true, 'top'],
        }).navGrid(
            '#pager',//参数1: 分页工具栏id
            {edit: true, add: true, del: true, edittext: "编辑", addtext: "添加", deltext: "删除"},   //参数2:开启工具栏编辑按钮
            {
                closeAfterEdit: true, reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    //console.log(response);
                    var guruID = response.responseJSON.id;
                    console.log(guruID);
                    var status = response.responseJSON.status;
                    if (status == "editok") {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/guru/upload",
                            datatype: "json",
                            type: "post",
                            data: {id: guruID},
                            // 指定的上传input框的id
                            fileElementId: "photo",
                            success: function (data, status) {
                                $("#guru").trigger("reloadGrid");//上传完成后刷新table表格
                            }
                        });
                        return postData;
                    }
                }
            },//编辑面板的配置
            {    //开启添加完成模态框自动关闭
                closeAfterAdd: true, reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    var guruID = response.responseJSON.id;
                    var status = response.responseJSON.status;
                    if (status == "addok") {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/guru/upload",
                            datatype: "json",
                            type: "post",
                            data: {id: guruID},
                            // 指定的上传input框的id
                            fileElementId: "photo",
                            success: function (data, status) {
                                $("#guru").trigger("reloadGrid");//上传完成后刷新table表格
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
        <h4><strong>上师列表</strong></h4>
    </div>
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/guru/list.jsp');">上师信息</a>
        </li>
        <li>
    </ul>
    <%--上师列表--%>
    <table id="guru"></table>
    <%--分页工具栏--%>
    <div id="pager"></div>
</div>
