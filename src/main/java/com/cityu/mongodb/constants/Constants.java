package com.cityu.mongodb.constants;

public interface Constants {
    String LOGIN_USER = "LOGIN_USER";
    String DEFAULT_PWD = "password";

    interface Message {
        String QUERY_SUCCESS = "Query succeed.";
        String ALL_MANDATORY_FIELDS = "All fields cannot be empty.";
        String MANDATORY_FIELD = "Mandatory field: %s";
        String INSERT_SUCCEED = "Add %s succeed.";
        String INSERT_FAILED = "Add %s failed, It may has created.";
        String UPDATE_SUCCEED = "Update succeed.";
        String UPDATE_FAILED = "Update failed.";
        String DELETE_SUCCEED = "Delete succeed.";
        String DELETE_FAILED = "Delete failed.";
        String EMPTY_USERNAME = "Username should not be empty.";
        String EMPTY_PWD = "Password should not be empty.";
        String LOGIN_SUCCEED = "Login succeed.";
        String LOGIN_FAILED = "Login username/password is incorrect, please double confirm.";
        String NO_USER_LOGIN = "Please login first.";
        String NO_ENROLL_PERMISSION = "Permission denied. It must be a student.";
        String ENROLL_SUCCEED = "Enroll course succeed.";
        String ENROLL_FAILED = "Enroll course failed.";
        String UNENROLL_SUCCEED = "Remove erroll course succeed.";
        String UNENROLL_FAILED = "Remove enroll course failed.";
        String LOGOUT_SUCCEED = "Logout succeed.";
        String ALREADY_LOGOUT = "It has logout.";
    }

    interface UserRole {
        String ADMIN = "Admin";
        String STUDENT = "Student";
    }
}
