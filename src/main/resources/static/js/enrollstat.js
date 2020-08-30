var myChart = null;

$(document).ready(function () {
    $.getJSON("/statistics/enrollStatistics", function(data){
        var _barData = [];
        var _barData2 = [];
        var _labelsData = [];
        var _barData3 = [];

        var _statdata = data.content;

        $.each(_statdata, function (i, item) {
           _labelsData[i] = item.year;
           _barData[i] = item.enrolledNum;
           _barData2[i] = item.unEnrolledNum;
           _barData3[i] = item.totalEnrolled;
        });

        var barData = {
             labels: _labelsData,
             datasets: [{
                label: '# of total enrolled student',
                backgroundColor: '#b5b8cf',
                pointBorderColor: "#fff",
                data: _barData3
             },{
                label: '# of distinct enrolled student',
                backgroundColor: 'rgba(220, 220, 220, 0.5)',
                pointBorderColor: "#fff",
                data: _barData
             },{
               label: '# of distinct unenrolled student',
               backgroundColor: 'rgba(26,179,148,0.5)',
               borderColor: "rgba(26,179,148,0.7)",
               pointBackgroundColor: "rgba(26,179,148,1)",
               data: _barData2
             }
             ]
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


