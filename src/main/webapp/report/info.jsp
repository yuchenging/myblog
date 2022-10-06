<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/8/27
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-signal"></span>&nbsp;数据报表 </div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-12">
                    <%--柱状图的所需容器--%>
                    <div id="monthChart" style="height: 400px">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--引入echarts的js文件--%>
<script type="text/javascript" src="statics/echarts/echarts.min.js"></script>
<script type="text/javascript">
    $.ajax({
        type:"get",
        url:"report",
        data:{
            actionName:"month"
        },
        success:function (result) {
            if(result.code==1){
                var monthArray=result.result.monthArray;
                var dataArray=result.result.dataArray;

                // 加载柱状图
                loadMonthChart(monthArray,dataArray);
            }
        }
    })
    <%--  加载柱状图  --%>
   function loadMonthChart(monthArray,dataArray) {
       // 基于准备好的dom，初始化echarts实例
       var myChart = echarts.init(document.getElementById('monthChart'));

       // 指定图表的配置项和数据
       // prettier-ignore
       let dataAxis = monthArray;
       // prettier-ignore
       let data = dataArray;
       let yMax = 30;
       let dataShadow = [];
       for (let i = 0; i < data.length; i++) {
           dataShadow.push(yMax);
       }
       var option = {
           title: {
               text: '按月统计',
               subtext: '通过月份查询博客数量',
               left:'center'
           },
           toolbars:{},
           xAxis: {
               data: dataAxis
           },
           yAxis: {
               axisLine: {
                   show: false
               },
               axisTick: {
                   show: false
               },
               axisLabel: {
                   color: '#999'
               }
           },
           dataZoom: [
               {
                   type: 'inside'
               }
           ],
           series: [
               {
                   type: 'bar',
                   showBackground: true,
                   itemStyle: {
                       color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                           { offset: 0, color: '#83bff6' },
                           { offset: 0.5, color: '#188df0' },
                           { offset: 1, color: '#188df0' }
                       ])
                   },
                   emphasis: {
                       itemStyle: {
                           color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                               { offset: 0, color: '#2378f7' },
                               { offset: 0.7, color: '#2378f7' },
                               { offset: 1, color: '#83bff6' }
                           ])
                       }
                   },
                   data: data
               }
           ]
       };
       // 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option);
   }
</script>