<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script>
    $(function () {
        // 创建父级JqGrid表格
        $("#table").jqGrid(
            {
                autowidth: true,
                styleUI: "Bootstrap",
                url: "${pageContext.request.contextPath}/album/findAll",
                datatype: "json",
                height: 500,
                colNames: ['id', '标题', '分数', '作者', '播音员', '专辑简介', '章节数', '状态', '上传时间', '插图'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'title', align: "center", editable: true},
                    {name: 'score', align: "center", editable: true},
                    {name: 'author', align: "center", editable: true},
                    {name: 'broadcast', align: "center", editable: true},
                    {name: 'description', align: "center", editable: true},
                    {name: 'count', align: "center"},
                    {
                        name: "status", align: "center", editable: true, formatter: function (data) {
                            if (data == "不展示") {
                                return "不展示";
                            } else return "展示";
                        }, editable: true, edittype: "select", editoptions: {value: "展示:展示;不展示:不展示"}
                    },
                    {name: "date", align: "center"},
                    {
                        name: "cover", align: "center", editable: true,
                        edittype: "file", editoptions: {enctype: "multipart/form-data"},
                        formatter: function (value, options, row) {
                            return "<img style='width:200px;height: 100px;' src='" + row.cover + "'/>"
                        }
                    }
                ],
                rowNum: 2,
                rowList: [2, 10, 20, 30],
                pager: '#pager',
                mtype: "post",
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                multiselect: false,
                // 开启多级表格支持
                subGrid: true,
                caption: "",//表格标题框
                toolbar: [true, 'top'],
                // 重写创建子表格方法
                subGridRowExpanded: function (subgrid_id, row_id) {
                    addTable(subgrid_id, row_id);
                },
                // 删除表格方法
                subGridRowColapsed: function (subgrid_id, row_id) {
                },
                editurl: "${pageContext.request.contextPath}/album/edit"
            });
        $("#table").jqGrid('navGrid', '#pager', {
                edit: true, add: true, del: true,
                edittext: "编辑", addtext: "添加", deltext: "删除"
            },
            {
                closeAfterEdit: true,
            }, {
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    var albumId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        datatype: "json",
                        type: "post",
                        data: {id: albumId},
                        // 指定的上传input框的id
                        fileElementId: "cover",
                        success: function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#table").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                closeAfterDel: true
            });
    });

    // subgrid_id 下方空间的id  row_id 当前行id数据
    function addTable(subgrid_id, row_id) {
        // 声明子表格|工具栏id
        var subgridTable = subgrid_id + "table";
        var subgridPager = subgrid_id + "pager";
        // 根据下方空间id 创建表格及工具栏
        $("#" + subgrid_id).html("<table id='" + subgridTable + "'></table><div style='height: 50px' id='" + subgridPager + "'></div>")
        // 子表格JqGrid声明
        $("#" + subgridTable).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/findAll?album_id=" + row_id,
            datatype: "json",
            data: {ablum_id: row_id},
            colNames: ['id', '标题', '大小', '时长', '上传时间', '音频', '操作'],
            colModel: [
                {name: "id", hidden: true},
                {name: 'title', align: "center", editable: true},
                {name: 'size', align: "center", editable: true},
                {name: 'time', align: "center", editable: true},
                {name: "create_time", align: "center", editable: true, edittype: "date"},
                {
                    name: "url", align: "center", editable: true,
                    edittype: "file", editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, options, row) {
                        var a = row.url.split("/");
                        var s = a[a.length - 1];
                        return s;
                    }
                },
                {
                    name: "options", align: "center", formatter: function (value, option, row) {
                        var result = "";
                        result += "<a href='javascript:void(0)' onclick=\"playAudio('" + row.url + "')\" class='btn btn-lg' title='播放'><span class='glyphicon glyphicon-play-circle'></span></a>";
                        result += "<a href='javascript:void(0)' onclick=\"downloadAudio('" + row.url + "')\" class='btn btn-lg' title='下载'><span class='glyphicon glyphicon-download'></span></a>";
                        return result;
                    }
                }
            ],
            rowNum: 2,
            rowList: [2, 10, 20, 30],
            pager: "#" + subgridPager,
            sortname: 'num',
            sortorder: "asc",
            height: '100%',
            styleUI: "Bootstrap",
            editurl: "${pageContext.request.contextPath}/chapter/edit?album_id=" + row_id,
            autowidth: true
        });
        $("#" + subgridTable).jqGrid('navGrid',
            "#" + subgridPager, {
                edit: true,
                add: true,
                del: true,
                edittext: "编辑", addtext: "添加", deltext: "删除"
            },
            {
                closeAfterEdit: true,
                beforeShowForm: function (frm) {
                    frm.find("#size").attr("readOnly", true);
                    frm.find("#time").attr("readOnly", true);
                    frm.find("#create_time").attr("readOnly", true);
                },
            }, {
                closeAfterAdd: true,
                beforeShowForm: function (frm) {
                    frm.find("#size").attr("readOnly", true);
                    frm.find("#time").attr("readOnly", true);
                },
                afterSubmit: function (response, postData) {
                    var chapterId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/chapter/upload",
                        datatype: "json",
                        type: "post",
                        data: {id: chapterId},
                        // 指定的上传input框的id
                        fileElementId: "url",
                        success: function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#table").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                closeAfterDel: true,
                afterComplete: function (response, postData) {
                    $("#table").trigger("reloadGrid");
                    return postData;
                },
            });
    }

    function playAudio(data) {
        $("#myModal").modal('show');
        $("#myaudio").prop("src", data);
    }

    function downloadAudio(data) {
        location.href = "${pageContext.request.contextPath}/chapter/down?url=" + data;
    }
</script>
<div class="col-sm-10">
    <div class="page-header" style="margin-top: 0px;">
        <h4><strong>专辑章节管理</strong></h4>
    </div>
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="#">专辑章节信息</a></li>
        <li>
    </ul>
    <%--专辑列表--%>
    <table id="table"></table>
    <%--分页工具栏--%>
    <div id="pager"></div>
</div>
<div class="modal fade" id="myModal" tabindex="-1">
    <audio src="" id="myaudio" controls></audio>
</div>