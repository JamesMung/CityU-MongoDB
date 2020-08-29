var currentTime = new Date();
var myChart = null;

$(document).ready(function () {
    $('#txtsearchyeardiv .input-group.date').datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        autoclose: true
    }).datepicker("setDate", currentTime);

    $.getJSON("/statistics/rankCourseEnrolled?year=2020", function(data){
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
                xAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        };

        var ctx = document.getElementById("barChart").getContext("2d");
        myChart = new Chart(ctx, { type: 'horizontalBar', data: barData, options: barOptions });
    });
});

$(function () {
    $("#btnsearch").on("click", function (e) {
        e.preventDefault();
        var _searchurl = "/statistics/rankCourseEnrolled";
        var _queryyear = $('#txtsearchyear').val();
        var _filter = "";

        if (IsNull(_queryyear) == false){ _filter += "&year=" + _queryyear;  }

        if (!IsNull(_filter)) {
            _searchurl += "?" + _filter.substring(1, _filter.length)
        }

        $.getJSON(_searchurl, function(data){
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
                    xAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            };

            var ctx = document.getElementById("barChart").getContext("2d");
            if(myChart != null) {
                myChart.destroy();
            }
            myChart = new Chart(ctx, { type: 'horizontalBar', data: barData, options: barOptions });
        });
    });

});
