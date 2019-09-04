package com.vsware.services.classservice.network;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveFeignClient(name = "gateway-service")
public interface CommunicationClient {


    //Student API
    @GetMapping("/student/")
    Flux<StudentResponseModel> getAllStudents();

    @GetMapping("/student/students")
    Flux<StudentResponseModel> getStudentsByIds(@RequestParam("ids") List<String> ids);

    @GetMapping("/student/{id}")
    Mono<StudentResponseModel> getStudentById(@PathVariable("id") String id);

    @GetMapping("/student/addRandomStudent")
    Mono<StudentResponseModel> addRandomStudent();

    @GetMapping("/student/addRandomStudents/{nr}")
    Flux<StudentResponseModel> addRandomStudents(@PathVariable("nr") int nr);

    @GetMapping("/student/discoveryInfo")
    Mono<String> getStudentDiscoveryInfo();


    //Teacher API
    @GetMapping("/teacher/")
    Flux<TeacherResponseModel> getAllTeachers();

    @GetMapping("/teacher/{id}")
    Mono<TeacherResponseModel> getTeacherById(@PathVariable("id") String id);

    @GetMapping("/teacher/addRandomTeacher")
    Mono<TeacherResponseModel> addRandomTeacher();

    @GetMapping("/teacher/discoveryInfo")
    Mono<String> getTeacherDiscoveryInfo();


}
