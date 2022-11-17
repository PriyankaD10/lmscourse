package com.learning.course.controller;

import com.learning.course.entity.Course;
import com.learning.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public Course saveCourse(@RequestBody Course course){
        //log.info("Inside saveCourse method of CourseController");
        return courseService.saveCourse(course);
    }

    @DeleteMapping("/delete/{courseName}")
    public int deleteCourse(@PathVariable("courseName") String courseName){
        //log.info("Inside saveCourse method of CourseController");
        int status = courseService.deleteCourse(courseName);
        return status;
    }

    @GetMapping("/getall")
    public List<Course> findAllCourses(){
        //log.info("Inside findAllCourses method of CourseController");
        return courseService.findAllCourses();
    }
    @GetMapping("/info/{searchBy}/{paramValue}")
    public List<Course> findCoursesByTechnology(@PathVariable("searchBy") String searchBy,@PathVariable("paramValue") String paramValue){
        //log.info("Inside findCourseById method of CourseController");
        return courseService.findCoursesByNameTechnology(searchBy,paramValue);
    }

    @GetMapping("get/{searchBy}/{param}/{from}/{to}")
    public List<Course> findCoursesByTechnology(@PathVariable("searchBy") String searchBy,@PathVariable("param") String param, @PathVariable("from") String from, @PathVariable("from") String to ){
        //log.info("Inside findCourseById method of CourseController");
        return courseService.findCoursesByAllSearchParams(searchBy,param,from,to);
    }

    @GetMapping("/exists/{coursename}")
    public Boolean isCourseExists(@PathVariable("coursename") String courseName){
        //log.info("Inside findCourseById method of CourseController");
        Boolean exists =false;
       List <Course> courseList = courseService.findCoursesByNameTechnology("course",courseName);
       if(courseList!=null && courseList.size()>0){
           exists =true;
       }
       return exists;
    }
}
