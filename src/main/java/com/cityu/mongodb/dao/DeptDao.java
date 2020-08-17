package com.cityu.mongodb.dao;

import com.cityu.mongodb.dto.DeptDto;
import com.cityu.mongodb.model.Course;
import com.cityu.mongodb.model.Department;
import com.cityu.mongodb.model.Enrolled;
import com.cityu.mongodb.model.Offer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeptDao extends AbstractDao {

    public List<Offer> findOfferedCourses(String deptId, Integer year) {
        Criteria c = new Criteria();
        if(!StringUtils.isEmpty(deptId))
            c.and("dept.deptId").is(deptId);
        if(year != null)
            c.and("year").is(year);

        return dao.find(Query.query(c), Offer.class);
    }

    @Transactional
    public boolean addDept(Department dept) {
        boolean isExist = dao.exists(Query.query(Criteria.where("deptId").is(dept.getDeptId())), Department.class);

        if (isExist) {
            return false;
        } else {
            dao.insert(dept);
            return true;
        }
    }

    @Transactional
    public void updateDept(DeptDto deptDto) {
        Department dept = deptDto.applyToDept();
        dao.update(Department.class)
                .matching(Criteria.where("deptId").is(deptDto.getOrigDeptId()))
                .replaceWith(dept)
                .findAndReplaceValue();

        dao.updateMulti(Query.query(Criteria.where("dept.deptId").is(deptDto.getOrigDeptId())), Update.update("dept", dept), Offer.class);
    }

    @Transactional
    public void deleteDept(String deptId) {
        dao.remove(Department.class).matching(Criteria.where("deptId").is(deptId)).findAndRemove();

        List<Offer> removedOffer = dao.remove(Offer.class).matching(Criteria.where("dept.deptId").is(deptId)).findAndRemove();
        List<String> courseIds = removedOffer.stream().map(o -> o.getCourse().getCourseId()).collect(Collectors.toList());
        dao.remove(Query.query(Criteria.where("course.courseId").in(courseIds)), Enrolled.class);
    }

    public List<Department> findAllList() {
        return dao.findAll(Department.class);
    }

    public Department findById(String deptId) {
        return dao.findById(deptId, Department.class);
    }
}
