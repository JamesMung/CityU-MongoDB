var myChart = null;

$(document).ready(function () {
    $.getJSON("/statistics/offerStatistics", function(data){
        var _barData = [];
        var _labelsData = [];

        var _statdata = data.content;

        $.each(_statdata, function (i, item) {
           _labelsData[i] = item.year;
           _barData[i] = item.offeredNum;
        });

        var barData = {
             labels: _labelsData,
             datasets: [{
                label: '# of course',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                pointBorderColor: "#fff",
                data: _barData
             }]
        }

        var barOptions = {
            responsive: true,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        };

        var ctx = document.getElementById("barChart").getContext("2d");
        myChart = new Chart(ctx, { type: 'bar', data: barData, options: barOptions });
    });
});


