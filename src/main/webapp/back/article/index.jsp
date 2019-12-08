<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/article/findAll",
                datatype: "json",
                colNames: ['编号', '内容', '标题', '封面', '状态', '上传时间', '发布时间', '所属上师ID', '上师名字', '操作'],
                colModel: [
                    {name: 'id', align: "center", hidden: true},
                    {name: 'content', align: "center", hidden: true},
                    {name: 'title', align: "center"},
                    {
                        name: 'cover', align: "center", editable: true,
                        formatter: function (value, options, row) {
                            return "<img style='width:200px;height: 100px;' src='" + row.cover + "'/>";
                        }
                    },
                    {
                        name: "status", align: "center", editable: true, formatter: function (data) {
                            if (data == "不展示") {
                                return "不展示";
                            } else return "展示";
                        }, editable: true, edittype: "select", editoptions: {value: "展示:展示;不展示:不展示"}
                    },
                    {name: 'publish_date', align: "center"},
                    {name: 'create_date', align: "center"},
                    {name: 'guru_id', align: "center"},
                    {name: 'gname', align: "center"},
                    {
                        name: 'option', formatter: function (cellvalue, options, rowObject) {
                            var result = '';
                            result += "<a href='javascript:void(0)' onclick=\"showModel('" + rowObject.id + "')\" class='btn btn-lg' title='查看详情'> <span class='glyphicon glyphicon-th-list'></span></a>";
                            return result;
                        }
                    },
                ],
                rowNum: 2,
                rowList: [2, 10, 20],
                pager: '#articlePager',
                mtype: "post",
                viewrecords: true,
                sortorder: "desc",
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                height: "500px",
                editurl: "${pageContext.request.contextPath}/article/edit"
            });
        $("#articleTable").jqGrid('navGrid', "#articlePager", {
            add: false,
            edit: false,
            del: true,
            deltext: "删除"
        });

    });

    // 打开模态框
    function addArticle() {
        // 清除表单内数据
        $("#kindfrm")[0].reset();
        // kindeditor 提供的数据回显方法  通过"" 将内容设置为空串
        KindEditor.html("#editor_id", "");
        $("#create_date").val("");
        $("#publish_date").val("");
        // 未提供查询上师信息 发送ajax请求查询
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>" +
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"insertArticle()\">添加</button>")
        $("#kind").modal("show");
    }

    // 编辑文章
    function showModel(id) {
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#articleTable").jqGrid("getRowData", id);
        $("#name").val(data.title);
        $("#statusList").val(data.status);
        //$("#editor_id").val(data.content);
        $("#id").val(data.id);
        $("#guruList").val(data.guru_id);
        $("#create_date").val(data.create_date);
        $("#publish_date").val(data.publish_date);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id", data.content);
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>" +
            "<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" onclick=\"updateArticle()\">修改</button>")
        $("#kind").modal("show");
        editor.sync();
    }

    // 添加文章
    function insertArticle() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/add",
            datatype: "json",
            type: "post",
            fileElementId: "inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data: {
                id: $("#id").val(),
                title: $("#name").val(),
                guru_id: $("#guruList").val(),
                content: $("#editor_id").val(),
                status: $("#statusList").val(),
                create_date: $("#create_date").val(),
                publish_date: $("#publish_date").val()
            },
            success: function (data) {
                $("#kind").modal("hide");
                // jqgrid重新载入
                $("#articleTable").trigger("reloadGrid");

            }
        })
    }

    function updateArticle() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/add",
            datatype: "json",
            type: "post",
            fileElementId: "inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data: {
                id: $("#id").val(),
                title: $("#name").val(),
                guru_id: $("#guruList").val(),
                content: $("#editor_id").val(),
                status: $("#statusList").val(),
                create_date: $("#create_date").val(),
                publish_date: $("#publish_date").val()
            },
            success: function (data) {
                $("#kind").modal("hide");
                // jqgrid重新载入
                $("#articleTable").trigger("reloadGrid");

            }
        })
    }
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h2><strong>文章管理</strong></h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a>文章列表</a></li>
        <li><a onclick="addArticle()">添加文章</a></li>
    </ul>
    <div class="panel">
        <table id="articleTable"></table>
        <div id="articlePager" style="width: auto;height: 50px"></div>
    </div>
</div>