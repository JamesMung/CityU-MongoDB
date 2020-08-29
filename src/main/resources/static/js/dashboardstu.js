$(document).ready(function () {
    $.getJSON("/statistics/rankCourseEnrolled", function(data){
        var _barData = [];
        var _labelsData = [];

        var _statdata = data.content;

        $.each(_statdata, function (i, item) {
           _labelsData[i] = item.title;
           _barData[i] = item.enrolledNum;
        });

        var barData = {
             labels: _labelsData,
             datasets: [{
                label: '# of student',
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
        new Chart(ctx, { type: 'horizontalBar', data: barData, options: barOptions });
    });
});