package com.cityu.mongodb.web;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.constants.Message;
import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.dto.DeptDto;
import com.cityu.mongodb.model.Department;
import com.cityu.mongodb.service.DeptService;
import com.cityu.mongodb.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dept")
public class DeptController {

    private DeptService deptService;
    private CourseController courseController;

    @GetMapping("/{id}/courses")
    @ResponseBody
    public Message getDeptCourses(@PathVariable("id") String deptId) {
        return MessageUtils.returnSuccessMsgWithContent(deptService.getCourses(deptId), Constants.Message.QUERY_SUCCESS);
    }

    @GetMapping("/courses/search")
    @ResponseBody
    public Message searchDeptCourses(String deptId, Integer year) {
        return MessageUtils.returnSuccessMsgWithContent(deptService.getCourses(deptId, year), Constants.Message.QUERY_SUCCESS);
    }

    @PostMapping("/addCourse")
    @ResponseBody
    public Message addCourse(CourseDto courDto) {
        return courseController.addCourse(courDto);
    }

    @GetMapping("/list")
    @ResponseBody
    public Message getDeptList() {
        return MessageUtils.returnSuccessMsgWithContent(deptService.getAllList(), Constants.Message.QUERY_SUCCESS);
    }

    @PostMapping("/add")
    @ResponseBody
    public Message addDept(Department dept) {
        if (!dept.isValid()) {
            return MessageUtils.returnErrorMsg(Constants.Message.ALL_MANDATORY_FIELDS);
        }

        boolean isSucceed = deptService.addDept(dept);

        return isSucceed ? MessageUtils.returnSuccessMsg(Constants.Message.INSERT_SUCCEED, "Department")
                : MessageUtils.returnErrorMsg(Constants.Message.INSERT_FAILED, "Department");

    }

    @PutMapping("/update")
    @ResponseBody
    public Message update(DeptDto deptDto) {
        if (!deptDto.isValid()) {
            return MessageUtils.returnErrorMsg(Constants.Message.ALL_MANDATORY_FIELDS);
        }

        deptService.updateDept(deptDto);
        return MessageUtils.returnSuccessMsg(Constants.Message.UPDATE_SUCCEED);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Message getById(@PathVariable("id") String deptId) {
        return MessageUtils.returnSuccessMsg(Constants.Message.QUERY_SUCCESS, deptService.getById(deptId));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Message delete(@PathVariable("id") String deptId) {
        deptService.deleteDept(deptId);
        return MessageUtils.returnSuccessMsg(Constants.Message.DELETE_SUCCEED);
    }

    public DeptService getDeptService() {
        return deptService;
    }

    @Autowired
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public CourseController getCourseController() {
        return courseController;
    }

    @Autowired
    public void setCourseController(CourseController courseController) {
        this.courseController = courseController;
    }
}
