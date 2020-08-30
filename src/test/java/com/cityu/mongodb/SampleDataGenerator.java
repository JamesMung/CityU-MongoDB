
package com.cityu.mongodb;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.dao.DeptDao;
import com.cityu.mongodb.dao.StudentDao;
import com.cityu.mongodb.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import me.xdrop.jrand.JRand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class SampleDataGenerator {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private MongoTemplate dao;

    private static final int STUDENT_COUNT = 299;
    private static final String[] DEPT_LIST = {"BE", "CS", "EE", "ME", "IS"};
    private static final String[] DEPT_NAME_LIST = {"Biomedical Engineering", "Computer Science", "Electrical Engineering", "Mechanical Engineering", "Information Systems"};
    private static final String[] LOCATION_LIST = {"Yeung Kin Man Academic Building"};
    private static final Map<String, List<String>> L5_COURSE_MAPPING = ImmutableMap.<String, List<String>>builder()
            .put("BE", Arrays.asList("Artificial Intelligence in Biomedical Engineering", "Engineering Mathematics"))
            .put("CS", Arrays.asList("Computer Organization", "Operating Systems", "Computer Network"))
            .put("EE", Arrays.asList("Foundations of Digital Techniques", "Principles of Electronic Engineering"))
            .put("ME", Arrays.asList("Engineering Materials", "Basic Electronic Engineering"))
            .put("IS", Arrays.asList("Data Management", "Systems Analysis and Design"))
            .build();
    private static final Map<String, List<String>> L6_COURSE_MAPPING = ImmutableMap.<String, List<String>>builder()
            .put("BE", Arrays.asList("Micro Systems Technology", "Manufacturing of Biomedical Devices"))
            .put("CS", Arrays.asList("Machine Learning: Principles and Practice","Natural Language Processing","Cloud Computing: Theory and Practice"))
            .put("EE", Arrays.asList("Topics in Computer Graphics", "Digital Audio Processing and Applications"))
            .put("ME", Arrays.asList("Advanced Automation Technology", "Micro Systems Technology"))
            .put("IS", Arrays.asList("Social, Legal and Ethical Issues of the Internet", "Business Process and Service Management"))
            .build();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Test
    void insert() {
        List<Department> deptList = generateDept();
        List<Offer> offerList = generateCourse(deptList);
        insertAdmin();
        List<Student> studentList = generateStudent();
        enrollCourse(offerList, studentList);
    }

    @Test
    void offer() {
        try {
            Offer cs101 = dao.findOne(Query.query(Criteria.where("course.courseId").is("CS101")), Offer.class);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cs101);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void enrolled() {
        try {
            Enrolled enrolled = dao.findOne(Query.query(Criteria.where("student.stuId").is("15100001")), Enrolled.class);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(enrolled);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void user() {
        try {
            User u = dao.findOne(Query.query(Criteria.where("student.stuId").is("15100001")), User.class);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(u);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertAdmin() {
        User u = new User();
        u.setId(0);
        u.setUsername("admin");
        u.setPassword(Constants.DEFAULT_PWD);
        u.setRole(Constants.UserRole.ADMIN);

        dao.insert(u);
    }

    private Student insertChanTaiMan() {
        try {
            Student student = new Student();
            student.setStuName("Chan Tai Man");
            student.setDOB(dateFormat.parse("2009-08-10"));
            student.setStuId("15100001");

            studentDao.addStudent(student);
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void clearAll() {
        dao.dropCollection(User.class);
        dao.dropCollection(Student.class);
        dao.dropCollection(Department.class);
        dao.dropCollection(Course.class);
        dao.dropCollection(Offer.class);
        dao.dropCollection(Enrolled.class);
    }

    private void enrollCourse(List<Offer> offerList, List<Student> studentList) {
        int size = (int)(studentList.size() * 1.0);

        Map<Integer, List<Offer>> offerMap = offerList.stream().collect(Collectors.groupingBy(o -> o.getYear()));

        for (int i = 0; i < size; i++) {
            enroll(studentList.get(i), offerMap);
        }

        // insert DB
        offerList.forEach(o -> dao.insert(o));
    }

    private void enroll(Student student, Map<Integer, List<Offer>> offerMap) {
            final Random random = new Random();

            offerMap.forEach((year, list) -> {
                try {
                    int num = 4;

                    Set<Integer> idxSet = new HashSet<>(num);
                    for (int i = 0; i < num; i++) {
                        idxSet.add(random.nextInt(list.size()));
                    }

                    for (Integer idx: idxSet) {
                        Offer offer = list.get(idx);

                        if (offer.getAvailablePlaces() > 0) {
                            Enrolled e = new Enrolled();
                            e.setStudent(student);
                            e.setCourse(offer.getCourse());
                            e.setYear(offer.getYear());

                            Calendar calendar = Calendar.getInstance();
                            e.setEnrolDate(dateFormat.parse(offer.getYear() + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH)));

                            dao.insert(e);
                            offer.setAvailablePlaces(offer.getAvailablePlaces() - 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    }

    private List<Offer> generateCourse(List<Department> deptList) {
        List<Offer> result = new ArrayList<>();
        for (Department dept: deptList) {
            int num = 101;

            final Random random = new Random();
            List<String> l5List = L5_COURSE_MAPPING.get(dept.getDeptId());
            for (int i = 0; i < l5List.size(); i++) {
                Course c = new Course();
                c.setCourseId(dept.getDeptId() + num++);
                c.setTitle(l5List.get(i));
                c.setLevel("5");
                dao.insert(c);


                int year = 2016 + random.nextInt(2);
                int range = 3 + random.nextInt(2);
                while(range-- > 0 && year <= 2020) {
                    Offer o = new Offer();
                    o.setCourse(c);
                    o.setDept(dept);
                    o.setClassSize(40);
                    o.setAvailablePlaces(40);
                    o.setYear(year++);
                    // DB insert after enroll
                    result.add(o);
                }
            }

            List<String> l6List = L6_COURSE_MAPPING.get(dept.getDeptId());
            for (int i = 0; i < l6List.size(); i++) {
                Course c = new Course();
                c.setCourseId(dept.getDeptId() + num++);
                c.setTitle(l6List.get(i));
                c.setLevel("6");
                dao.insert(c);

                int year = 2016 + random.nextInt(2);
                int range = 3 + random.nextInt(2);
                while(range-- > 0 && year <= 2020) {
                    Offer o = new Offer();
                    o.setCourse(c);
                    o.setDept(dept);
                    o.setClassSize(40);
                    o.setAvailablePlaces(40);
                    o.setYear(year++);
                    // DB insert after enroll
                    result.add(o);
                }
            }
        }

        return result;
    }

    private List<Student> generateStudent() {
        List<Student> result = new ArrayList<>(STUDENT_COUNT);
        result.add(insertChanTaiMan());

        int stuId = 15100002;
        for (int i = 0; i < STUDENT_COUNT; i++) {
            Student student = new Student();
            student.setStuName(JRand.name().gen());
            student.setDOB(JRand.birthday().gen());
            student.setStuId(String.valueOf(stuId++));

            studentDao.addStudent(student);
            result.add(student);
        }

        return result;
    }

    private List<Department> generateDept() {
        List<Department> result = new ArrayList<>(DEPT_LIST.length);

        for (int i = 0; i < DEPT_LIST.length; i++) {
            Department dept = new Department();
            dept.setDeptId(DEPT_LIST[i]);
            dept.setDeptName(DEPT_NAME_LIST[i]);
            dept.setLocation(LOCATION_LIST[0]);

            deptDao.addDept(dept);
            result.add(dept);
        }

        return result;
    }
}

