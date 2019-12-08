<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    function update(id) {
        $.post("${pageContext.request.contextPath}/user/update", {id: id}, function (result) {
            alert(result.status);
            $('#user').trigger('reloadGrid');
        }, "JSON");
    };
    $(function () {
        //初始化表格 并查询所有
        $("#user").jqGrid({
            styleUI: "Bootstrap",
            autowidth: true,
            height: 500,
            url: "${pageContext.request.contextPath}/user/findAll",
            datatype: "json",
            colNames: ["编号", "姓名", "电话", "密码", "盐", "状态", "昵称", "头像", "性别", "签名", "地址", "注册时间", "最后登录时间", "操作"],
            colModel: [
                {name: "id", editable: true},
                {name: "name", align: "center", editable: true, editrules: {required: true}},
                {name: "phone", align: "center", editable: true, editrules: {required: true}},
                {name: "password", align: "center", editable: true, editrules: {required: true}},
                {name: "salt", hidden: true},
                {
                    name: "status", align: "center", editable: true, formatter: function (data) {
                        if (data == "正常") {
                            return "正常";
                        } else if (data == "冻结") return "冻结";
                    }, editable: true, edittype: "select", editoptions: {value: "正常:正常;冻结:冻结"}
                },
                {name: "nick_name", align: "center", editable: true, editoptions: {required: true}},
                {
                    name: "photo", align: "center", editable: true,
                    edittype: "file", editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, options, row) {
                        return "<img style='width:200px;height: 100px;' src='" + row.photo + "'/>";
                    }
                },
                {
                    name: "sex", align: "center", editable: true, formatter: function (data) {
                        if (data == "男") {
                            return "男";
                        } else if (data == "女") return "女";
                    }, editable: true, edittype: "select", editoptions: {value: "男:男;女:女"}
                },
                {name: "sign", align: "center", editable: true, editoptions: {required: true}},
                {name: "location", align: "center", editable: true, editoptions: {required: true}},
                {name: "rigest_date", align: "center"},
                {name: "last_login", align: "center"},
                {
                    name: "option", alignL: "center",
                    formatter: function (value, options, row) {
                        return "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + row.id + "')\">修改</button>";
                    }
                }
            ],
            pager: "#pager",
            mtype: "post",
            viewrecords: true,
            rowNum: 2,
            rowList: [2, 4, 6],
            multiselect: false,
            editurl: "${pageContext.request.contextPath}/user/edit",
            toolbar: [true, 'top'],
        }).navGrid(
            '#pager',//参数1: 分页工具栏id
            {edit: false, add: false, del: false},   //参数2:开启工具栏编辑按钮
            {
                closeAfterEdit: true, reloadAfterSubmit: true,
            },//编辑面板的配置
            {    //开启添加完成模态框自动关闭
                closeAfterAdd: true, reloadAfterSubmit: true,
            },//添加面板的配置
            {},//删除的配置
            {}//搜索的配置
        );
    });
</script>

<div class="col-sm-10">
    <div class="page-header" style="margin-top: 0px;">
        <h4><strong>用户列表</strong></h4>
    </div>
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/guru/list.jsp');">用户信息</a>
        </li>
        <li>
    </ul>
    <%--用户列表--%>
    <table id="user"></table>
    <%--分页工具栏--%>
    <div id="pager"></div>
</div>
