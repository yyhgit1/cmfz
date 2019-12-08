<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor/lang/zh-CN.js"></script>
    <script charset="UTF-8" src="../echarts/echarts.min.js"></script>
    <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <title>持明法洲后台管理系统</title>
    <script>
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                width: '600px',
                // 1. 指定图片上传路径
                uploadJson: "${pageContext.request.contextPath}/article/upload",//富文本编辑框的图片
                allowFileManager: true,
                fileManagerJson: "${pageContext.request.contextPath}/article/showImgs",//展示文件夹里面图片
                afterBlur: function () {
                    this.sync();
                }
            });
        });
        //获取上师信息，在表单回显
        $(function () {
            $.post("${pageContext.request.contextPath}/guru/findAll", function (data) {
                var option = null;
                data.forEach(function (guru) {
                    option += "<option value='" + guru.id + "'>" + guru.nick_name + "</option>"
                    if (guru.id == "1") {
                    }
                });
                $("#guruList").append(option);
            }, "JSON");
        });
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎：${loginManager.username}</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/exit">退出登录</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse ">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user/index.jsp')">
                                        <p class="text-muted" style="margin: 0px">用户信息管理</p></a></li>
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user/echarts.jsp')">
                                        <p class="text-muted" style="margin: 0px">用户注册趋势</p></a></li>
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user/map.jsp')">
                                        <p class="text-muted" style="margin: 0px">用户注册分布</p></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/guru/index.jsp')">
                                        <p class="text-muted" style="margin: 0px">上师信息</p></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/article/index.jsp');">
                                        <p class="text-muted" style="margin: 0px">文章信息</p></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/album/index.jsp');">
                                        <p class="text-muted" style="margin: 0px">专辑信息</p></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingFive">
                        <h4 class="panel-title">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/banner/list.jsp');">
                                        <p class="text-muted" style="margin: 0px">轮播图信息</p></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 需要替换的内容 -->
        <div id="content">
            <div class="col-sm-10">
                <div class="container">
                    <div class="jumbotron" style="margin: 0px; padding:10px;">
                        <h2>欢迎使用持明法洲后台管理系统 ！</h2>
                    </div>
                    <br/><br/>
                    <div id="myCarousel" class="carousel slide">
                        <!-- 轮播（Carousel）指标 -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>
                        <!-- 轮播（Carousel）项目 -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="${pageContext.request.contextPath}/back/img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg"
                                     alt="First slide">
                                <div class="carousel-caption">标题 1</div>
                            </div>
                            <div class="item">
                                <img src="${pageContext.request.contextPath}/back/img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg"
                                     alt="Second slide">
                                <div class="carousel-caption">标题 2</div>
                            </div>
                            <div class="item">
                                <img src="${pageContext.request.contextPath}/back/img/098ca7cad1c8a786b4e6a0366609c93d71cf5049.jpg"
                                     alt="Third slide">
                                <div class="carousel-caption">标题 3</div>
                            </div>
                        </div>
                        <!-- 轮播（Carousel）导航 -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br/><br/>
<div class="panel-footer">
    <h4 style="text-align: center">@百知教育 baizhi@zparkhr.com.cn</h4>
</div>
<div class="modal fade" id="kind" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" enctype="multipart/form-data" id="kindfrm">
                    <input name="id" type="hidden" id="id">
                    <div class="form-group">
                        <label for="name">标题</label>
                        <input type="text" class="form-control" name="title" id="name" placeholder="请输入名称">
                    </div>
                    <div class="form-group">
                        <label for="inputfile">封面上传</label>
                        <input type="file" id="inputfile" name="inputfile">
                    </div>
                    <div class="form-group">
                        <label for="name">所属上师</label>
                        <select class="form-control" id="guruList" name="guru_id">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name">状态</label>
                        <select class="form-control" id="statusList" name="status">
                            <option value="展示">展示</option>
                            <option value="不展示">不展示</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name">上传时间</label>
                        <input type="date" class="form-control" id="create_date" name="create_date">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name">发布时间</label>
                        <input type="date" class="form-control" id="publish_date" name="publish_date">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editor_id">内容</label>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px; resize: none;">
						</textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>