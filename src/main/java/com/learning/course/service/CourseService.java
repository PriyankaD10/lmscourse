package com.learning.course.service;

import com.learning.course.entity.Course;
import com.learning.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    private static final String OPTION1 = "course";

    public Course saveCourse(Course course) {
        Course newCourse = new Course();
        //log.info("Inside saveCourse method of CourseService");
        try {
            newCourse = courseRepository.save(course);
        }
        catch(Exception e){
            e.printStackTrace();
            //log.info("Failed to  save course in CourseService "+e.getMessage());
        }
        return newCourse;
    }

    public int deleteCourse(String courseName) {
        int flag = -1;
        //log.info("Inside saveCourse method of CourseService");
        try {
            try {
                Course course = new Course();
                course.setCourseName(courseName);
                Example<Course> example = Example.of(course);
                Optional<Course> deleteCourse =  courseRepository.findOne(example);
                if(deleteCourse!=null && deleteCourse.isPresent()){
                    courseRepository.deleteById(deleteCourse.get().getId());
                    flag = 1;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            //log.info("Failed to  save course in CourseService "+e.getMessage());
        }
        return flag;
    }

    public Course findCourseById(Long courseId) {
        //log.info("Inside findCourseById method of CourseService");
        Course course  = null;
        try {
            course = courseRepository.findCourseById(courseId);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return course;
    }

    public List<Course> findAllCourses() {
        //log.info("Inside findAllCourses method of CourseService");
        List<Course> courseList = new LinkedList<Course>();
       try {
           courseList =  courseRepository.findAll();
       }
       catch(Exception e){
           e.printStackTrace();
       }
        return courseList;
    }

    public List<Course> findCoursesByNameTechnology(String searchBy, String paramValue) {
        //log.info("Inside findCoursesByTechnology method of CourseService");
        List<Course> courseList = new ArrayList<Course>();
        boolean isSearchByDuration = false;
        try {
            Course course = new Course();
            if(searchBy.matches(".*\\d.*") && paramValue.matches(".*\\d.*")){
                isSearchByDuration =true;
            }
            if(isSearchByDuration){
                ArrayList<Course> finalCourseList = new ArrayList<>();
                courseRepository.findAll().forEach(c -> {
                    if(c.getDuration()>= Long.parseLong(searchBy) && c.getDuration()<= Long.parseLong(paramValue)) {
                        finalCourseList.add(c);
                    }
                });
                courseList = finalCourseList;
            }else {
                if (OPTION1.equals(searchBy)) {
                    course.setCourseName(paramValue);
                } else {
                    course.setTechnology(paramValue);
                }
                Example<Course> example = Example.of(course);
                courseList =  courseRepository.findAll(example);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return courseList;
    }

    public List<Course> findCoursesByAllSearchParams(String searchBy,String param,String from, String to) {
        //log.info("Inside findCoursesByTechnologyAndDuration method of CourseService");
        ArrayList<Course>filteredList = new ArrayList<Course>();
        try {
            Course course = new Course();
            if (OPTION1.equals(searchBy)) {
                course.setCourseName(param);
            } else {
                course.setTechnology(param);
            }
            Example<Course> example = Example.of(course);
            courseRepository.findAll(example).forEach(c -> {
                        if(c.getDuration()>= Long.parseLong(from) && c.getDuration()<= Long.parseLong(to)) {
                            filteredList.add(c);
                        }
                    });
        }
        catch(Exception e){
            e.printStackTrace();
        }
       return filteredList;
    }
}
