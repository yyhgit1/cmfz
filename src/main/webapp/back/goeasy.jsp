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

    <title>简单的聊天室</title>
    <script>
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-f35886506227474a82bec38703ee6d43", //替换为您的应用appkey
        });
        //接收消息
        goEasy.subscribe({
            channel: "cmfz", //替换为您自己的channel
            onMessage: function (message) {
                // 手动将 字符串类型转换为 Json类型
                //var data = JSON.parse(message.content);
                //alert("Channel:" + message.channel + " content:" + message.content);
                //创建Div对象 （文本输入框对象）
                var $content = $('#content');
                //创建输出对象的容器把内容追加进去
                var p = $('</p>').html(message.content);
                //把内容追加到聊天框的div中
                $content.append(p);
            }
        });

        function send(sendtext) {
            //发送消息
            goEasy.publish({
                //channel:订阅的对象必须保持一致
                channel: "cmfz", //替换为您自己的channel
                //message：需要发送的内容
                message: sendtext //替换为您想要发送的消息内容
            });
        };
        //发送方法
        $(function () {
            //给提交按钮添加事件，触发发送消息的方法
            $('#btn').click(function () {
                //获取输入框的值给发送消息传值
                send($('#t').val());
                $('#t').val("");
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-2">
            <h1>简单聊天室</h1>
            <div style="width: 700px; height: 400px; overflow: scroll; border: 1px solid" id="content">
            </div>
            <br/>
            <input type="text" id="t">
            <button type="button" id="btn">提交</button>
        </div>
    </div>
</div>
</body>
</html>