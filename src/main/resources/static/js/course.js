﻿var _data = [];
var _deptdata = [];
var currentTime = new Date();

$(document).ready(function () {
    $.getJSON("/dept/list", function(data){
        _deptdata = data.content;
        $.each(_deptdata, function (i, item) {
            var option = $("<option/>").data("deptdataset", item).val(item.deptId).text(item.deptId + " - " + item.deptName);
            $('#txtdept').append(option);
        });
    });

    $('#txtyeardiv .input-group.date').datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        autoclose: true
    }).datepicker("setDate", currentTime);
});


$(function () {
    var ft2 = FooTable.init('#resultstb2', {
        "columns": [
            { "name": "stuId", "title": "ID" },
            { "name": "stuName", "title": "Name" },
            { "name": "DOB", "title": "Date of birthday" },
        ],
        "paging": {
            enabled: true,
            "position": "right"
        }
    });

    $.getJSON("/course/list", function(data){
        _data = data.content;
        var $modal = $('#modal-form'),
            $studentlist = $('#modal-studentform'),
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
                  "title": "Student list",
                  "type":"text",
                  "formatter": function (value) {
                        var actions = $('<div/>').append();
                        var user_button = $('<a/>').addClass("btn btn-default")
                                            .append( $('<i/>').addClass("fa fa-list")
                                              .on("click", this, showstudentlist))
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
                allowDelete: true,
                allowAdd: true,
                alwaysShow: true,
                allowView: false,
                editText: '<i class= "fa fa-edit" ></i>',
                deleteText: '<i class= "fa fa-trash-alt" ></i>',
                //這裡是修改時繫結資料
                editRow: function (row) {
                    var values = row.val();
                    $editor.find('#txtdept').val(values.deptId);
                    $editor.find('#txtdept').val(values.deptId);
                    $editor.find('#txtcourseId').val(values.courseId);
                    $editor.find('#txtcoursetitle').val(values.title);
                    $editor.find('#txtlevel').val(values.level);
                    $('#txtyeardiv .input-group.date').datepicker("setDate", new Date(values.year, 0, 1));
                    $editor.find('#txtclassSize').val(values.classSize);
                    $editor.find('#txtavailableplaces').val(values.availablePlaces);

                    $editor.find('#txtcourseId').prop("disabled",true);
                    $modal.data('row', row);
                    $editorTitle.text('Modify Data #' + values.courseId);
                    $modal.modal('show');
                },
                deleteRow: function (row) {
                    // This example displays a confirm popup and then simply removes the row but you could just
                    // as easily make an ajax call and then only remove the row once you retrieve a response.
                    swal({
                        title: "Alert",
                        text: "Are you sure you want to delete the row?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes",
                        cancelButtonText: "No",
                        closeOnConfirm: false,
                        closeOnCancel: true
                    }, function (isConfirm) {
                        if (isConfirm) {
                            var values = row.val();
                            DeleteDepartment(values.deptId);
                        } else {
                            return
                        }
                    });
                }
            }
        });

        function showstudentlist(e){
            e.preventDefault();
            var $row = $(this).closest('tr');
            var row = FooTable.getRow($row);
            var values = row.val();
            var _url = "/course/" + values.courseId + "/" + values.year
            $.getJSON(_url, function(data){
                var _coursedata = data.content;
                var _studentdata = [];
                $.each(_coursedata, function (i, item) {
                   _studentdata.append(item.student)
                });
                ft2.rows.load(_studentdata);
                $studentlist.modal('show');
            });
        }

        $("#btncourseadd").on("click", function (e) {
            e.preventDefault();
            $('#editor-title').text("Add Row");
            $('#txtdeptId').val("");
            $('#txtdept').val("");
            $('#txtcourseId').val("");
            $('#txtcoursetitle').val("");
            $('#txtlevel').val("");
            $('#txtyeardiv .input-group.date').datepicker("setDate", currentTime);
            $('#txtclassSize').val(0);
            $('#txtavailableplaces').val(0);
            $('#txtcourseId').prop("disabled",false);
            $modal.modal('show');
        });


        $editor.on('submit', function (e) {
            if (this.checkValidity && !this.checkValidity()) return;
            e.preventDefault();
            var row = $modal.data('row');
            values = {
                OrgID: $editor.find('#txtcourseId').val(),
                deptId: $editor.find('#txtdeptID').val(),
                deptName: $editor.find('#txtdept').val(),
                courseId: $editor.find('#txtcourseId').val(),
                coursetitle: $editor.find('#txtcoursetitle').val(),
                courseId: $editor.find('#txtcourseId').val(),
            };

            swal({
                title: "Alert",
                text: "Confirm your input data?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes",
                cancelButtonText: "No",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    return
                } else {
                    return
                }
            });
        });
    });
});
