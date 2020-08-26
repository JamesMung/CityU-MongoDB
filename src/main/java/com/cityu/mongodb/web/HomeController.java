package com.cityu.mongodb.web;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.constants.Message;
import com.cityu.mongodb.model.Department;
import com.cityu.mongodb.utils.MessageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/dashboard")
    public String Viewdashboard(){
        return "dashboard";
    }

    @GetMapping("/student")
    public String Viewstudent(){
        return "student";
    }

    @GetMapping("/course")
    public String Viewcourse(){
        return "course";
    }

    @GetMapping("/department")
    public String Viewdepartment(){
        return "department";
    }

    /*student*/
    @GetMapping("/studentdashboard")
    public String Viewdashboard2(){
        return "dashboardStu";
    }

    @GetMapping("/studentcourse")
    public String Viewcourse2(){
        return "courseStu";
    }

}
