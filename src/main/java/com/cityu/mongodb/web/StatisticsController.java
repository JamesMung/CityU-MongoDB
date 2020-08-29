package com.cityu.mongodb.web;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.constants.Message;
import com.cityu.mongodb.service.CourseService;
import com.cityu.mongodb.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private CourseService courseService;

    @GetMapping("/rankCourseEnrolled")
    @ResponseBody
    public Message getRankCourseEnrolled(Integer year) {
        return MessageUtils.returnSuccessMsgWithContent(courseService.getCourseEnrollStatistics(year), Constants.Message.QUERY_SUCCESS);
    }

    @GetMapping("/offerStatistics")
    @ResponseBody
    public Message getOfferedByYear() {
        return MessageUtils.returnSuccessMsgWithContent(courseService.getOfferedStatistic(), Constants.Message.QUERY_SUCCESS);
    }

    @GetMapping("/enrollStatistics")
    @ResponseBody
    public Message getEnrolledByYear() {
        return MessageUtils.returnSuccessMsgWithContent(courseService.getEnrolledStatistic(), Constants.Message.QUERY_SUCCESS);
    }

    public CourseService getCourseService() {
        return courseService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
