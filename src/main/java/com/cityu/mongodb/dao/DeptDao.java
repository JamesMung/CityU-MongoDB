package com.cityu.mongodb.dao;

import com.cityu.mongodb.model.Department;
import com.cityu.mongodb.model.Enrolled;
import com.cityu.mongodb.model.Offer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    public void updateDept(Department dept) {
        dao.updateFirst(Query.query(Criteria.where("deptId").is(dept.getDeptId())), new Update().set("deptName",dept.getDeptName()).set("location", dept.getLocation()), Department.class);

        dao.updateMulti(Query.query(Criteria.where("dept.deptId").is(dept.getDeptId())), Update.update("dept", dept), Offer.class);
    }

    @Transactional
    public void deleteDept(String deptId) {
        List<Offer> removedOffers = dao.findAllAndRemove(Query.query(Criteria.where("dept.deptId").is(deptId)), Offer.class);
        dao.findAllAndRemove(Query.query(Criteria.where("deptId").is(deptId)), Department.class);

        List<String> courseIds = removedOffers.stream().map(o -> o.getCourse().getCourseId()).collect(Collectors.toList());
        dao.remove(Query.query(Criteria.where("course.courseId").in(courseIds)), Enrolled.class);
    }

    public List<Department> findAllList() {
        return dao.findAll(Department.class);
    }

    public Department findById(String deptId) {
        return dao.findById(deptId, Department.class);
    }
}
