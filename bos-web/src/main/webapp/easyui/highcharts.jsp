<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>highcharts</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script src="${pageContext.request.contextPath }/highcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath }/highcharts/modules/exporting.js"></script>
</head>
<body>
	<div id="hc"></div>
	<script type="text/javascript">
		$(function(){
			$("#hc").highcharts(
					chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false,
			            type: 'pie'
			        },
			        title: {
			            text: 'Browser market shares January, 2015 to May, 2015'
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: false
			                },
			                showInLegend: true
			            }
			        },
			        series: [{
			            name: 'Brands',
			            colorByPoint: true,
			            data: [{
			                name: 'Microsoft Internet Explorer',
			                y: 56.33
			            }, {
			                name: 'Chrome',
			                y: 24.03,
			                sliced: true,
			                selected: true
			            }, {
			                name: 'Firefox',
			                y: 10.38
			            }, {
			                name: 'Safari',
			                y: 4.77
			            }, {
			                name: 'Opera',
			                y: 0.91
			            }, {
			                name: 'Proprietary or Undetectable',
			                y: 0.2
			            }]
			        }]		
			);
		});
	</script>
</body>
</html>