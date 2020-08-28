var _data = [];
var startDate = new Date(1990, 0, 1);

$(document).ready(function () {
    $('#datedobdiv .input-group.date').datepicker("setDate", startDate).datepicker({ dateFormat: 'yy-mm-dd', autoclose: true });
});


$(function () {
    $.getJSON("/student/list", function(data){
        _data = data.content;
        var $modal = $('#modal-form'),
            $editor = $('#editor'),
            $editorTitle = $('#editor-title'),
            ft = FooTable.init('#resultstb', {
                "columns": [
                    { "name": "stuId", "title": "ID" },
                    { "name": "stuName", "title": "Name" },
                    { "name": "DOB", "title": "Date of birthday" }
                ],
                "rows": _data,
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
                        $editor.find('#txtstudentID').val(values.stuId);
                        $editor.find('#txtstuName').val(values.stuName);
                        //$editor.find('#txtdob').val(values.DOB);
                        $('#datedobdiv .input-group.date').datepicker("setDate", new Date(values.DOB));
                        $editor.find('#origStudentID').val(values.stuId);
                        $editor.find('#txtstudentID').prop("disabled",true);
                        $modal.data('row', row);
                        $editorTitle.text('Modify Data #' + values.stuId);
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
                                DeleteStudent(values.stuId);
                            } else {
                                return
                            }
                        });
                    }
                }
            });

        ft.pageSize(100);

        $("#btndeptadd").on("click", function (e) {
            e.preventDefault();
            $('#editor-title').text("Add Row");
            $('#txtstuName').val("");
            $('#txtstudentID').val("");
            //$('#txtdob').val("");
            $('#datedobdiv .input-group.date').datepicker("setDate", startDate);
            $('#origStudentID').val("");
            $('#txtstudentID').prop("disabled",false);
            $modal.modal('show');
        });

        $editor.on('submit', function (e) {
            if (this.checkValidity && !this.checkValidity()) return;
            e.preventDefault();
            var row = $modal.data('row');
            values = {
                OrgID: $editor.find('#origStudentID').val(),
                stuId: $editor.find('#txtstudentID').val(),
                stuName: $editor.find('#txtstuName').val(),
                dob: $editor.find('#txtdob').val()
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
                    UpdateStudent(values);
                } else {
                    return
                }
            });
        });
    });
});

function DeleteStudent(stuId){
    var _url = "/student/del?stuId=" + stuId;
    var _model = "DELETE"

    AjaxPost(_url, _model, undefined, function (data) {
        var _de_data = $.parseJSON(data);
        if (_de_data.success == true) {
            swal({
                title: "Updated",
                text:  _de_data.msg,
                type: "success",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "OK",
                closeOnConfirm: true
            }, function () {
                location.reload();
            });
        } else {
           swal("Error", _de_data.msg, "error");
        }
    });
}

function UpdateStudent(values) {
    var _url = "/student/register";
    var _model = "POST"
    var fd = new FormData();

    if( IsNull(values.OrgID) == false){
        _url = "/student/update";
        _model = "PUT";
    }

    fd.append( 'stuId', values.stuId);
    fd.append( 'stuName', values.stuName);
    fd.append( 'DOB', values.dob);

    AjaxPost(_url, _model, fd, function (data) {
        var _de_data = $.parseJSON(data);
        if (_de_data.success == true) {
            swal({
                title: "Updated",
                text:  _de_data.msg,
                type: "success",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "OK",
                closeOnConfirm: true
            }, function () {
                location.reload();
            });
        } else {
           swal("Error", _de_data.msg, "error");
        }
    });
}
