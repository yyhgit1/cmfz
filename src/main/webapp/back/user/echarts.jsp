<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-sm-10">
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>
</div>
>
<script type="text/javascript">
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BS-cab6ba5c3dd841118b20df931e1eaba6", //替换为您的应用appkey
    });
    //接收消息
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            // 手动将 字符串类型转换为 Json类型
            var m = JSON.parse(message.content);
            myChart.setOption({
                series: [
                    {
                        name: '男',
                        type: 'bar',
                        data: m.man,
                    }, {
                        name: '女',
                        type: 'bar',
                        data: m.women,
                    }
                ]
            })
        }
    });
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户注册趋势'
        },
        tooltip: {},
        legend: {
            data: ['男', '女']
        },
        xAxis: {
            data: ["1天", "7天", "30天", "1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显
    $.get("${pageContext.request.contextPath}/user/findUsers", function (data) {
        myChart.setOption({
            series: [
                {
                    name: '男',
                    type: 'bar',
                    data: data.man,
                }, {
                    name: '女',
                    type: 'bar',
                    data: data.women,
                }
            ]
        })
    }, "json")
</script>