<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>login</title>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function changeImage() {
            var img = document.getElementById('imgVcode');
            img.src = '${pageContext.request.contextPath}/admin/img?' + Math.random();
        }

        $(function () {
            $("#log").on('click', function () {
                $.post("${pageContext.request.contextPath}/admin/login", {
                    username: $("#username").val(),
                    password: $("#password").val(),
                    number: $("#number").val()
                }, function (result) {
                    if (result = "ok") {
                        window.location = "${pageContext.request.contextPath}/back/main/main.jsp";
                    } else {
                        $("#msg").prop('style', 'color:red').html(result);
                    }
                });
            });
        });
    </script>
</head>
<body style=" background: url(../img/980186345982b2b7bcce9fcb3cadcbef76099b35.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" action="javascript:;">
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名" autocomplete="off" id="username">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" autocomplete="off" id="password">
                </div>
                <span id="msg"></span>
                <br/> <br/>
                <div class="code" style="width:100px;">
                    <input type="text" id="number"/>
                </div>
                <br/>
                <div class="Captcha-operate">
                    <div class="Captcha-imageConatiner">
                        <a class="code_pic" id="vcodeImgWrap" name="change_code_img" href="javascript:void(0);">
                            <img id="imgVcode" src="${pageContext.request.contextPath}/admin/img"
                                 class="Ucc_captcha Captcha-image" onClick="changeImage()">
                        </a>
                        <a id="vcodeImgBtn" name="change_code_link" class="code_picww" href="javascript:changeImage()">换张图</a>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="form-group">
                    <button type="button" class="btn btn-primary form-control" id="log">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
