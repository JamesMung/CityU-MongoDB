var currentTime = new Date();

$(document).ready(function () {
    $.getJSON("/dept/list", function(data){
        _deptdata = data.content;
        $.each(_deptdata, function (i, item) {
            var option = $("<option/>").data("deptdataset", item).val(item.deptId).text(item.deptId + " - " + item.deptName);
            $('#txtsearchdept').append(option);
        });
    });

    $('#txtyeardiv .input-group.date').datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        autoclose: true
    }).datepicker("setDate", currentTime);

    $('#txtsearchyeardiv .input-group.date').datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        autoclose: true
    }).datepicker("setDate", currentTime);
});

$(function () {
    var _url = "/student/enrolledList?year=2020";

    $.getJSON(_url, function(data){
        _data = data.content;
        var $modal = $('#modal-form'),
            $editor = $('#editor'),
            $editorTitle = $('#editor-title'),
            ft = FooTable.init('#resultstb', {
            "columns": [
                { "name": "deptId", "title": "Dept. ID", "visible":false },
                { "name": "deptName", "title": "Dept. Name" },
                { "name": "courseId", "title": "Course ID", "visible":false },
                { "name": "title", "title": "Course Title" },
                { "name": "level", "title": "Level", "breakpoints": "xs" },
                { "name": "year", "title": "Year", "breakpoints": "xs" },
                { "name": "classSize", "title": "Class Size", "breakpoints": "xs" },
                { "name": "enrolledNum", "title": "No. of Enrolled", "breakpoints": "xs", "visible":false },
                { "name": "availablePlaces", "title": "Available Places", "breakpoints": "xs" },
                { "name": "valid",
                  "title": "Action",
                  "type":"text",
                  "formatter": function (value) {
                        var actions = $('<div/>').append();
                        var user_button = $('<a/>').addClass("btn btn-default")
                                            .append( $('<i/>').addClass("fa fa-user-check")
                                              .on("click", this, unenroll))
                                              .appendTo(actions);
                        return actions;
                  }
                },
            ],
            "rows" : _data,
            "paging": {
                enabled: true,
                "position": "right"
            },
            "editing": {
                enabled: true,
                allowDelete: false,
                allowAdd: true,
                allowEdit: false,
                alwaysShow: true,
                allowView: false,
                editText: '<i class= "fa fa-edit" ></i>',
                deleteText: '<i class= "fa fa-trash-alt" ></i>',
            }
        });

        ft.pageSize(100);

        function unenroll(e){
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

        $("#btnsearch").on("click", function (e) {
            e.preventDefault();
            var _searchurl = "/student/enrolledList";
            _querydept = $('#txtsearchdept').val();
            _querycourse = $('#txtsearchcourse').val();
            _querylevel = $('#txtsearchlevel').val();
            _queryyear = $('#txtsearchyear').val();
            _filter = "";

            if (IsNull(_querydept) == false){ _filter += "&deptId=" + _querydept;  }
            if (IsNull(_querycourse) == false){ _filter += "&courseId=" + _querycourse;  }
            if (IsNull(_querylevel) == false){ _filter += "&level=" + _querylevel;  }
            if (IsNull(_queryyear) == false){ _filter += "&year=" + _queryyear;  }

            if (!IsNull(_filter)) {
                _searchurl += "?" + _filter.substring(1, _filter.length)
            }

            $.getJSON(_searchurl, function(data){
                var _coursedata = data.content;
                ft.rows.load(_coursedata);
            });
        });

    });
});