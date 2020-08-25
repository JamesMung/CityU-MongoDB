var currentTime = new Date();

$(document).ready(function () {
    $('#searchyeardiv .input-group.date').datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        autoclose: true
    }).datepicker("setDate", currentTime);
});

$(function () {
    

    var $modal = $('#modal-form'),
        $editor = $('#editor'),
        $editorTitle = $('#editor-title'),
        ft = FooTable.init('#resultstb', {
            "columns": [
                { "name": "deptID", "title": "Dept. ID" },
                { "name": "courseID", "title": "Course ID" },
                { "name": "year", "title": "Year" },
                { "name": "classsize", "title": "Class Size" },
                { "name": "availableplaces", "title": "Available Places" }
            ],
            "rows": [
                {
                    "options": {
                        "expanded": true
                    },
                    "value": { "deptID": "CS", "courseID": "CS101", "year": 2016, "classsize": 40, "availableplaces": 40}
                }
            ],
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
                    $editor.find('#txtdeptID').val(values.deptID);
                    $editor.find('#txtcourseID').val(values.courseID);
                    $editor.find('#txtyear').val(values.year);
                    $editor.find('#txtclasssize').val(values.classsize);
                    $editor.find('#txtavailableplaces').val(values.availableplaces);
                    $modal.data('row', row);
                    $editorTitle.text('Modify Data #' + values.deptID);
                    $modal.modal('show');
                },
                deleteRow: function (row) {
                    // This example displays a confirm popup and then simply removes the row but you could just
                    // as easily make an ajax call and then only remove the row once you retrieve a response.
                    if (confirm('Are you sure you want to delete the row?')) {
                        row.delete();
                    }
                }
            }
        });

});
