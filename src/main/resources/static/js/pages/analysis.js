document.addEventListener("DOMContentLoaded", function() {
	enableDonutChart("#like_dislike_chart");
	enablePublicRadialBar("#publicRadialChart");
});
function enableDonutChart(chartSelector) {
	 const chartData = JSON.parse(document.getElementById("chart-data").textContent);

    var options = {
        series: chartData.weekly,
        chart: {
            type: 'donut',
        },
        labels: ["Likes", "Dislikes"],
        colors: ["#4FD675", "#2B2B29"],
        legend: {
            position: "bottom"
        }
    };

    var chart = new ApexCharts(document.querySelector(chartSelector), options);
    chart.render();

    document.getElementById("chartFilter").addEventListener("change", function (e) {
        chart.updateSeries(chartData[e.target.value]);
    });
}

function enablePublicRadialBar(publicRadialBarSelector){
	var options = {
			series: [44, 55, 67],
			chart: {
				height: 350,
				type: 'radialBar',
			},
			plotOptions: {
				radialBar: {
					dataLabels: {
						name: {
							fontSize: '22px',
						},
						value: {
							fontSize: '16px',
						},
						total: {
							show: true,
							label: 'Popularity',
							formatter: function (w) {
								return "50%";
							}
						}
					}
				}
			},
			labels: ['Apples', 'Oranges', 'Bananas', 'Berries'],
		};

		var chart = new ApexCharts(document.querySelector(publicRadialBarSelector), options);
		chart.render();
}
