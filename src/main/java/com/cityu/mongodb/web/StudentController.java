package com.cityu.mongodb.web;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.constants.Message;
import com.cityu.mongodb.model.Student;
import com.cityu.mongodb.model.User;
import com.cityu.mongodb.query.SearchCriteria;
import com.cityu.mongodb.service.CourseService;
import com.cityu.mongodb.service.StudentService;
import com.cityu.mongodb.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;
    private CourseService courseService;

    @GetMapping("/list")
    @ResponseBody
    public Message stuList() {
        return MessageUtils.returnSuccessMsgWithContent(studentService.findStudentList(), Constants.Message.QUERY_SUCCESS);
    }

    @PostMapping("/register")
    @ResponseBody
    public Message register(Student student) {
        studentService.addStudent(student);
        return MessageUtils.returnSuccessMsg(Constants.Message.INSERT_SUCCEED, "Student");
    }

    @PostMapping("/enroll")
    @ResponseBody
    public Message enrollCourse(String courseId, Integer year, HttpSession session) {
        User u = (User)session.getAttribute(Constants.LOGIN_USER);
        if(u == null) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_USER_LOGIN);
        } else if(Constants.UserRole.ADMIN.equals(u.getRole())) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_ENROLL_PERMISSION);
        } else if (year == null || year == 0) {
            return MessageUtils.returnErrorMsg(Constants.Message.MANDATORY_FIELD, "Year");
        }

        boolean succeed = studentService.enrollCourse(u.getStudent(), courseId, year);

        return succeed ? MessageUtils.returnSuccessMsg(Constants.Message.ENROLL_SUCCEED) :
            MessageUtils.returnErrorMsg(Constants.Message.ENROLL_FAILED);
    }

    @GetMapping("/enrolledList")
    @ResponseBody
    public Message getEnrolledList(HttpSession session, SearchCriteria criteria) {
        User u = (User)session.getAttribute(Constants.LOGIN_USER);
        if(u == null) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_USER_LOGIN);
        } else if(Constants.UserRole.ADMIN.equals(u.getRole())) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_ENROLL_PERMISSION);
        }

        return MessageUtils.returnSuccessMsgWithContent(courseService.getEnrolledCourses(u.getStudent(), criteria), Constants.Message.QUERY_SUCCESS);
    }

    @DeleteMapping("/unEnroll")
    @ResponseBody
    public Message unEnrollCourse(String courseId, Integer year, HttpSession session) {
        User u = (User)session.getAttribute(Constants.LOGIN_USER);
        if(u == null) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_USER_LOGIN);
        } else if(Constants.UserRole.ADMIN.equals(u.getRole())) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_ENROLL_PERMISSION);
        } else if (year == null || year == 0) {
            return MessageUtils.returnErrorMsg(Constants.Message.MANDATORY_FIELD, "Year");
        }

        boolean succeed = studentService.unEnrollCourse(u.getStudent(), courseId, year);

        return succeed ? MessageUtils.returnSuccessMsg(Constants.Message.UNENROLL_SUCCEED) :
                MessageUtils.returnErrorMsg(Constants.Message.UNENROLL_FAILED);
    }

    @GetMapping("/unEnrollList")
    @ResponseBody
    public Message getUnEnrollCourseList(HttpSession session, SearchCriteria criteria) {
        User u = (User)session.getAttribute(Constants.LOGIN_USER);
        if(u == null) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_USER_LOGIN);
        } else if(Constants.UserRole.ADMIN.equals(u.getRole())) {
            return MessageUtils.returnErrorMsg(Constants.Message.NO_ENROLL_PERMISSION);
        }

        return MessageUtils.returnSuccessMsgWithContent(courseService.getUnEnrolledCourses(u.getStudent(), criteria), Constants.Message.QUERY_SUCCESS);
    }

    @PutMapping("/update")
    @ResponseBody
    public Message update(Student student) {
        studentService.update(student);
        return MessageUtils.returnSuccessMsg(Constants.Message.UPDATE_SUCCEED);
    }

    @DeleteMapping("/del")
    @ResponseBody
    public Message del(String stuId) {
        studentService.delete(stuId);
        return MessageUtils.returnSuccessMsg(Constants.Message.DELETE_SUCCEED);
    }

    public StudentService getStudentService() {
        return studentService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
