package com.cityu.mongodb.web;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.constants.Message;
import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.query.SearchCriteria;
import com.cityu.mongodb.service.CourseService;
import com.cityu.mongodb.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @GetMapping("/list")
    @ResponseBody
    public Message getList() {
        return MessageUtils.returnSuccessMsgWithContent(courseService.getAllList(), Constants.Message.QUERY_SUCCESS);
    }

    @GetMapping("/search")
    @ResponseBody
    public Message search(SearchCriteria criteria) {
        return MessageUtils.returnSuccessMsgWithContent(courseService.searchCourse(criteria), Constants.Message.QUERY_SUCCESS);
    }

    @GetMapping("/{id}/{year}")
    @ResponseBody
    public Message getEnrolledStudents(@PathVariable("id") String courId, @PathVariable("year") Integer year) {
        return MessageUtils.returnSuccessMsgWithContent(courseService.getEnrolledStudents(courId, year), Constants.Message.QUERY_SUCCESS);
    }

    public CourseService getCourseService() {
        return courseService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Message addCourse(CourseDto courDto) {
        boolean isSucceed = courseService.addCourse(courDto);
        if(isSucceed) {
            return MessageUtils.returnSuccessMsg(Constants.Message.INSERT_SUCCEED, "Course");
        } else {
            return MessageUtils.returnErrorMsg(Constants.Message.INSERT_FAILED, "Course");
        }
    }
}
