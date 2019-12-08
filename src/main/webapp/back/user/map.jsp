<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    $(function () {
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
                console.log(m.map);
                myChart.setOption({
                    series: [
                        {
                            name: '用户',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: m.map
                        }
                    ]
                });
            }
        });
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('userMap'));
        var option = {
            title: {
                text: '用户分布图',
                subtext: '纯属虚构',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['用户']  //男女分开时必须保证男的所有省份都有一个
            },
            visualMap: {
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: []
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        // Ajax异步数据回显
        $.get("${pageContext.request.contextPath}/user/map", function (data) {
            myChart.setOption({
                series: [
                    {
                        name: '用户',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: data.map
                    }
                ]
            })
        }, "json");
    });

</script>

<div class="col-sm-10">
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="userMap" style="width: 600px;height:400px;"></div>
</div>
