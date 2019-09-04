package com.vsware.services.studentservice.controller;

import com.github.javafaker.Faker;
import com.netflix.discovery.EurekaClient;
import com.vsware.services.studentservice.model.StudentModel;
import com.vsware.services.studentservice.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@Slf4j
public class StudentController {

    private final StudentRepository studentRepository;
    private final EurekaClient eurekaClient;

    private Faker faker;
    private String instanceId;

    public StudentController(StudentRepository studentRepository, EurekaClient eurekaClient) {
        this.studentRepository = studentRepository;
        this.eurekaClient = eurekaClient;
        faker = new Faker();
    }

    @GetMapping("/")
    public Flux<StudentModel> getAllStudents() {
        log.info(getEurekaInfo() + ": executing: getAllStudents()");
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<StudentModel> getStudentById(@PathVariable String id) {
        log.info(getEurekaInfo() + ": executing: getStudentById(" + id + ")");
        return studentRepository.findById(id);
    }

    @GetMapping("/students")
    public Flux<StudentModel> getStudentsByIds(@RequestParam Set<String> ids) {
        log.info(getEurekaInfo() + ": executing: getStudentsByIds(" + ids + ")");
        return  studentRepository.findAllById(ids);
    }

    @GetMapping("/addRandomStudent")
    public Mono<StudentModel> addRandomStudent() {
        log.info(getEurekaInfo() + ": executing: addRandomStudent()");
        return Mono.fromCallable(() -> faker.name().fullName().split(" "))
                .map(fullRandomName -> new StudentModel(null, fullRandomName[0], fullRandomName[1]))
                .flatMap(studentRepository::save);
    }

    @GetMapping("/addRandomStudents/{nr}")
    public Flux<StudentModel> addRandomStudents(@PathVariable int nr) {
        log.info(getEurekaInfo() + ": executing: addRandomStudents(" + nr + ")");
        return addRandomStudent().repeat(nr);
    }

    @GetMapping("/discoveryInfo")
    public String getEurekaInfo() {
        if (instanceId == null)
        {
            instanceId = eurekaClient.getApplicationInfoManager().getInfo().getInstanceId();
        }
        return instanceId;
    }


}
