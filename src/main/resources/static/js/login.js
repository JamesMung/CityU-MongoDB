$(function () {
    $("#password").keypress(function(e){
        code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13){
          login();
        }
    });

    $("#btnlogin").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        login();
    })
});

function login(){
    var _username = $('#loginid').val();
    var _password = $('#password').val();

    var _url = "/user/login?username=" + _username + "&password=" + _password;
    var _model = "POST"

    AjaxPost(_url, _model, undefined, function (data) {
        var _rtndata = $.parseJSON(data);

        if (_rtndata.success == true) {
            var couserid = GetStorage("userid");
            if(IsNull(couserid) == false){
                RemoveStorage("userid");
                RemoveStorage("username");
                RemoveStorage("userrole");
            }

            var tempdata = _rtndata.content;
            var datausername = tempdata.username;
            var student_data = tempdata.student;

            if(IsNull(student_data) == false){
                SaveStorage("username", student_data.stuName);
            }else{
                SaveStorage("username", datausername);
            }

            SaveStorage("userid", _username);

            if(_username == "admin"){
                SaveStorage("userrole", "Admin");
                window.location = "/home/dashboard";
            }else{
                SaveStorage("userrole", "Student");
                window.location = "/home/studentdashboard";
            }

        } else {
           swal("Error", _rtndata.msg, "error");
        }
    });
}