package com.cityu.mongodb.service;

import com.cityu.mongodb.dao.DeptDao;
import com.cityu.mongodb.dto.DeptDto;
import com.cityu.mongodb.model.Department;
import com.cityu.mongodb.vo.DeptCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptService {

    private DeptDao deptDao;

    public List<DeptCourse> getCourses(String deptId) {
      return getCourses(deptId, null);
    }

    public List<DeptCourse> getCourses(String deptId, Integer year) {
        return deptDao.findOfferedCourses(deptId, year).stream()
                .map(o -> {
                    DeptCourse c = new DeptCourse();
                    c.setDeptId(deptId);
                    c.setDeptName(o.getDept().getDeptName());
                    c.setClassSize(o.getClassSize());
                    c.setEnrolledNum(o.getClassSize()-o.getAvailablePlaces());
                    c.setLevel(o.getCourse().getLevel());
                    c.setTitle(o.getCourse().getTitle());
                    c.setYear(o.getYear());

                    return c;
                }).collect(Collectors.toList());
    }

    public boolean addDept(Department dept) {
        return deptDao.addDept(dept);
    }

    public DeptDao getDeptDao() {
        return deptDao;
    }

    @Autowired
    public void setDeptDao(DeptDao deptDao) {
        this.deptDao = deptDao;
    }

    public void updateDept(Department dept) {
        deptDao.updateDept(dept);
    }

    public void deleteDept(String deptId) {
        deptDao.deleteDept(deptId);
    }

    public List<Department> getAllList() {
        return deptDao.findAllList();
    }

    public Department getById(String deptId) {
        return deptDao.findById(deptId);
    }
}
