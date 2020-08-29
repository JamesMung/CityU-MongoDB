package com.cityu.mongodb.web;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.constants.Message;
import com.cityu.mongodb.model.User;
import com.cityu.mongodb.service.UserService;
import com.cityu.mongodb.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Message login(HttpSession session, String username, String password) {
        if (StringUtils.isEmpty(username)) {
            return MessageUtils.returnErrorMsg(Constants.Message.EMPTY_USERNAME);
        } else if (StringUtils.isEmpty(password)) {
            return MessageUtils.returnErrorMsg(Constants.Message.EMPTY_PWD);
        } else {
            User u = userService.login(username, password);

            if (u != null) {
                session.setAttribute(Constants.LOGIN_USER, u);
                return MessageUtils.returnSuccessMsgWithContent(u, Constants.Message.LOGIN_SUCCEED);
            } else {
                return MessageUtils.returnErrorMsg(Constants.Message.LOGIN_FAILED);
            }
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public Message logout(HttpSession session) {
        User u = (User)session.getAttribute(Constants.LOGIN_USER);
        if (u == null) {
            return MessageUtils.returnErrorMsg(Constants.Message.ALREADY_LOGOUT);
        } else {
            session.removeAttribute(Constants.LOGIN_USER);
            return MessageUtils.returnSuccessMsg(Constants.Message.LOGOUT_SUCCEED);
        }
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
