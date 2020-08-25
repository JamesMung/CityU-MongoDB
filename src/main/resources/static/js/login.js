$(function () {
    $("#btnlogin").click(function (e) {
        e.preventDefault();
        e.stopPropagation();

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
                    RemoveStorage("userrole");
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

    })

});