var _data = [];
var _deptdata = [];
var currentTime = new Date();

$(document).ready(function () {
    $.getJSON("/dept/list", function(data){
        _deptdata = data.content;
        $.each(_deptdata, function (i, item) {
            var option = $("<option/>").data("deptdataset", item).val(item.deptId).text(item.deptId + " - " + item.deptName);
        });
    });
});


$(function () {

    $.getJSON("/course/list", function(data){
        _data = data.content;
        var ft = FooTable.init('#resultstb', {
            "columns": [
                { "name": "deptId", "title": "Dept. ID", "visible":false },
                { "name": "deptName", "title": "Dept. Name" },
                { "name": "courseId", "title": "Course ID" },
                { "name": "title", "title": "Course Title" },
                { "name": "level", "title": "Level", "breakpoints": "xs" },
                { "name": "year", "title": "Year", "breakpoints": "xs" },
                { "name": "classSize", "title": "Class Size", "breakpoints": "xs" },
                { "name": "enrolledNum", "title": "No. of Enrolled", "breakpoints": "xs", "visible":false },
                { "name": "availablePlaces", "title": "Available Places", "breakpoints": "xs" },
                { "name": "valid",
                  "title": "",
                  "type":"text",
                  "formatter": function (value) {
                        var actions = $('<div/>').append();
                        var user_button = $('<a/>').addClass("btn btn-default")
                                            .append( $('<i/>').addClass("fa fa-user-check")
                                              .on("click", this, enroll))
                                              .appendTo(actions);

                        var user_undobutton = $('<a/>').addClass("btn btn-default")
                                            .append( $('<i/>').addClass("fa fa-undo")
                                              .on("click", this, unroll))
                                              .appendTo(actions);
                        return actions;
                  }
                },
            ],
            "rows" : _data,
            "paging": {
                enabled: true,
                "position": "right"
            }
        });

        ft.pageSize(100);

        function enroll(e){
             e.preventDefault();
             e.stopPropagation();

             var $row = $(this).closest('tr');
             var row = FooTable.getRow($row);
             var values = row.val();
             var _url = "/student/enroll?courseId=" + values.courseId + "&year=" + values.year;
             var _model = "POST";

             AjaxPost(_url, _model, undefined, function (data) {
                 var _rtndata = $.parseJSON(data);

                 if (_rtndata.success == true) {
                    swal({
                        title: "Alert",
                        text: "Enroll Successful",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes",
                        cancelButtonText: "No",
                        closeOnConfirm: true,
                        closeOnCancel: true
                    }, function (isConfirm) {
                        if (isConfirm) {
                            location.reload();
                        } else {
                            location.reload();
                        }
                    });
                 } else {
                    swal("Error", _rtndata.msg, "error");
                 }
             });
        }

        function unroll(e){
             e.preventDefault();
             e.stopPropagation();

             var $row = $(this).closest('tr');
             var row = FooTable.getRow($row);
             var values = row.val();
             var _url = "/student/unEnroll?courseId=" + values.courseId + "&year=" + values.year;
             var _model = "DELETE";

             AjaxPost(_url, _model, undefined, function (data) {
                var _rtndata = $.parseJSON(data);

                if (_rtndata.success == true) {
                    swal({
                        title: "Alert",
                        text: "unEnroll Successful",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes",
                        cancelButtonText: "No",
                        closeOnConfirm: true,
                        closeOnCancel: true
                    }, function (isConfirm) {
                        if (isConfirm) {
                            location.reload();
                        } else {
                            location.reload();
                        }
                    });
                } else {
                    swal("Error", _rtndata.msg, "error");
                }
             });
        }
    });
});
