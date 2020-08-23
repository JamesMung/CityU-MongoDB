package com.cityu.mongodb;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.dao.DeptDao;
import com.cityu.mongodb.dao.StudentDao;
import com.cityu.mongodb.model.*;
import com.google.common.collect.ImmutableMap;
import me.xdrop.jrand.JRand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class SampleDataGenerator {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private MongoTemplate dao;

    private static final int STUDENT_COUNT = 1000;
    private static final String[] DEPT_LIST = {"BE", "CS", "EE", "ME", "SEEM"};
    private static final String[] DEPT_NAME_LIST = {"Biomedical Engineering", "Computer Science", "Electrical Engineering", "Mechanical Engineering", "Systems Engineering and Engineering Management"};
    private static final String[] LOCATION_LIST = {"Yeung Kin Man Academic Building"};
    private static final Map<String, List<String>> L5_COURSE_MAPPING = ImmutableMap.<String, List<String>>builder()
            .put("BE", Arrays.asList("Artificial Intelligence in Biomedical Engineering", "Engineering Mathematics", "Electronic Circuits", "Thermo and Fluid Dynamics", "Biomechanics", "Biomaterials", "Electromagnetics", "Engineering Graphics", "Engineering Computing", "Medical Biotechnology in Imaging and Measurement", "Biomedical Signals and Systems", "Micro and Nanotechnology", "Computational Biology and Bioinformatics", "Biomedical Instrumentation", "Bio-sensors and Bio-devices", "Biomedical Systems and Control", "Consumer Mechatronics", "Robotics and Machine Vision", "Molecules and Cells", "Cell Transport and Signalling", "Tissue Engineering", "Regenerative Medicine", "Gene Therapy", "Human Quantitative Physiology", "Health Maintenance and Wellness Technology", "Bio-safety and Security", "Technology for Drug Discovery", "Radiotherapy Physics"))
            .put("CS", Arrays.asList("Computer Organization", "Operating Systems", "Computer Network", "Data Structures", "Algorithm Design Techniques", "Software Design", "Database Design and Management"))
            .put("EE", Arrays.asList("Foundations of Digital Techniques", "Principles of Electronic Engineering", "Introduction to Electronic Design", "Logic Circuit Design", "Microcomputer Systems", "Electronic Devices and Circuits", "Introduction to Electromagnetics", "Computational Engineering Analysis", "Principles of Communications", "Engineers in Society", "Design Project", "Communication Engineering", "Applied Electromagnetics", "Systems & Control", "Applied Optoelectronic Devices", "Differential Equations for Electrical Engineering", "Analogue Circuit Fundamentals", "Introduction to Electric Power Systems", "Introduction to Electric Machines and Drives", "Signals and Systems", "Project", "Engineering Training I", "Engineering Training II", "Multi-variable Calculus and Linear Algebra"))
            .put("ME", Arrays.asList("Engineering Materials", "Basic Electronic Engineering", "Engineering Drawing", "Engineering Analysis", "Electronics", "Control Principles", "Mechanical Design", "Microprocessor Applications", "Industrial Automation"))
            .put("SEEM", Arrays.asList("Introduction to Systems Engineering and Management", "Data Analytics and Statistical Methods", "Engineering Economic Analysis", "Ergonomics in Workplace Design", "Logistics and Materials Management", "Operations and Logistics Planning", "Work Design", "Engineering Database and Systems", "Quality Improvement Methodologies", "Operations Research", "Quality Engineering", "Process Analysis and Design", "Operations and Logistics Engineering Workshop", "Professional Engineering Practice", "Product and Service Design and Innovation"))
            .build();
    private static final Map<String, List<String>> L6_COURSE_MAPPING = ImmutableMap.<String, List<String>>builder()
            .put("BE", Arrays.asList("Micro Systems Technology", "Manufacturing of Biomedical Devices", "Biomedical Instrumentation", "Biomechanics", "Biomedical Engineering Design", "Biomedical Safety and Risk Assessment", "Human Machine Interface", "Biomedical Engineering Design", "Regenerative Medicine", "Dissertation", "Project Development Study", "Industrial Case Study", "Mechanical Behaviour of Materials: From Metallic to Biomedical/ Biological Materials", "Advanced Control Systems", "Biorobotics", "Biomedical Safety and Risk Assessment", "Biomedical Photonics", "Electron Microscopy", "Physiological Modeling", "Flexible Bioelectronics for Medical Applications", "Engineering Principles for Drug Delivery"))
            .put("CS", Arrays.asList("Machine Learning: Principles and Practice","Natural Language Processing","Guided Study in Artificial Intelligence","Algorithms & Techniques for Web Searching","Cloud Computing: Theory and Practice","Machine Learning: Algorithms and Applications","Guided Study in Data Science","Topics on Information Security","Privacy-enhancing Technologies","Guided Study in Information Security","Virtual Reality Technologies and Applications","Computer Games Design","Vision and Language","Topics in Machine Learning","Project","Guided Study"))
            .put("EE", Arrays.asList("Topics in Image Processing", "Topics in Computer Graphics", "Digital Audio Processing and Applications", "Green Electronics-Theory, Eco-design, Experiments and Applications", "Supply Chain Management", "Managing Strategic Quality"))
            .put("ME", Arrays.asList("Advanced Automation Technology", "Sustainable Green Manufacturing", "Advanced Thermo-fluid", "Micro Systems Technology", "Mechanical Behaviour of Materials: From Metallic to Biomedical/ Biological Materials", "Applied Engineering Mechanics"))
            .put("SEEM", Arrays.asList("Operations Management", "Engineering Management Principles and Concepts", "Project Management", "Technological Innovation and Entrepreneurship", "Asset and Maintenance Management", "Supply Chain Management"))
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

    private void insertAdmin() {
        User u = new User();
        u.setId(0);
        u.setUsername("Admin");
        u.setPassword(Constants.DEFAULT_PWD);
        u.setRole(Constants.UserRole.ADMIN);

        dao.insert(u);
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
        for (int i = 0; i < 800; i++) {
            enroll(studentList.get(i), offerList);
        }

        // insert DB
        offerList.forEach(o -> dao.insert(o));
    }

    private void enroll(Student student, List<Offer> offerList) {
        try {
            int randomIdx = (int) (Math.random() * offerList.size());

            Offer offer = offerList.get(randomIdx);
            if (offer.getAvailablePlaces() <= 0) {
                enroll(student, offerList);
            } else {
                Enrolled e = new Enrolled();
                e.setStudent(student);
                e.setCourse(offer.getCourse());
                e.setYear(offer.getYear());

                Calendar calendar = Calendar.getInstance();
                e.setEnrolDate(dateFormat.parse(offer.getYear() + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH)));

                dao.insert(e);
                offer.setAvailablePlaces(offer.getAvailablePlaces() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Offer> generateCourse(List<Department> deptList) {
        List<Offer> result = new ArrayList<>(690);
        for (Department dept: deptList) {
            int num = 101;

            List<String> l5List = L5_COURSE_MAPPING.get(dept.getDeptId());
            for (int i = 0; i < l5List.size(); i++) {
                Course c = new Course();
                c.setCourseId(dept.getDeptId() + num++);
                c.setTitle(l5List.get(i));
                c.setLevel("5");
                dao.insert(c);

                int year = 2016;
                while(year <= 2020) {
                    Offer o = new Offer();
                    o.setCourse(c);
                    o.setDept(dept);
                    o.setClassSize(50);
                    o.setAvailablePlaces(50);
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

                int year = 2016;
                while(year <= 2020) {
                    Offer o = new Offer();
                    o.setCourse(c);
                    o.setDept(dept);
                    o.setClassSize(50);
                    o.setAvailablePlaces(50);
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
        int stuId = 15100001;

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
