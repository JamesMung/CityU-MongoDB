var _data = [];

$(function () {
    $.getJSON("/dept/list", function(data){
        _data = data.content;
        var $modal = $('#modal-form'),
            $editor = $('#editor'),
            $editorTitle = $('#editor-title'),
            ft = FooTable.init('#resultstb', {
            "columns": [
                { "name": "deptId", "title": "ID" },
                { "name": "deptName", "title": "Name" },
                { "name": "location", "title": "Location" },
                { "name": "valid", "title": "valid", "visible":false }
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
                    $editor.find('#txtdeptID').val(values.deptId);
                    $editor.find('#txtdeptName').val(values.deptName);
                    $editor.find('#txtlocation').val(values.location);
                    $editor.find('#origDeptId').val(values.deptId);
                    $editor.find('#txtdeptID').prop("disabled",true);
                    $modal.data('row', row);
                    $editorTitle.text('Modify Data #' + values.deptId);
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
        }).pageSize(100);

        $("#btndeptadd").on("click", function (e) {
            e.preventDefault();
            $('#editor-title').text("Add Row");
            $('#txtdeptName').val("");
            $('#txtdeptID').val("");
            $('#txtlocation').val("");
            $('#origDeptId').val("");
            $('#txtdeptID').prop("disabled",false);
            $modal.modal('show');
        });


        $editor.on('submit', function (e) {
            if (this.checkValidity && !this.checkValidity()) return;
            e.preventDefault();
            var row = $modal.data('row');
            values = {
                OrgID: $editor.find('#origDeptId').val(),
                deptId: $editor.find('#txtdeptID').val(),
                deptName: $editor.find('#txtdeptName').val(),
                location: $editor.find('#txtlocation').val()
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
                    UpdateDepartment(values);
                } else {
                    return
                }
            });
        });
    });
});

function DeleteDepartment(deptId){
    var _url = "/dept/delete/" + deptId;
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

function UpdateDepartment(values) {
    var _url = "/dept/add";
    var _model = "POST"
    var fd = new FormData();

    if( IsNull(values.OrgID) == false){
        _url = "/dept/update";
        _model = "PUT";
        fd.append( 'origDeptId', values.OrgID);
        fd.append( 'newDeptId', values.deptId);
    }else{
        fd.append( 'deptId', values.deptId);
    }
    fd.append( 'deptName', values.deptName);
    fd.append( 'location', values.location);

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




