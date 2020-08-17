package com.cityu.mongodb.model;


import com.cityu.mongodb.dto.DeptDto;
import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;

public class Department {
    @Id
    private String deptId;

    private String deptName;
    private String location;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isValid() {
        return StringUtils.isEmpty(deptId) && StringUtils.isEmpty(deptName) && StringUtils.isEmpty(location);
    }

}
