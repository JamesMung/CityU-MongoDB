package com.cityu.mongodb.dto;

import com.cityu.mongodb.model.Department;
import org.springframework.util.StringUtils;

public class DeptDto {
    private String origDeptId;
    private String newDeptId;
    private String deptName;
    private String location;

    public String getOrigDeptId() {
        return origDeptId;
    }

    public void setOrigDeptId(String origDeptId) {
        this.origDeptId = origDeptId;
    }

    public String getNewDeptId() {
        return newDeptId;
    }

    public void setNewDeptId(String newDeptId) {
        this.newDeptId = newDeptId;
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

    public boolean isValid () {
        return StringUtils.isEmpty(newDeptId) && StringUtils.isEmpty(deptName) && StringUtils.isEmpty(location);
    }

    public Department applyToDept() {
        Department d = new Department();
        d.setDeptId(this.newDeptId);
        d.setDeptName(this.deptName);
        d.setLocation(this.location);

        return d;
    }
}
