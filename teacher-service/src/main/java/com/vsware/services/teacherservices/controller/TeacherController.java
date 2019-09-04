package com.vsware.services.teacherservices.controller;

import com.github.javafaker.Faker;
import com.netflix.discovery.EurekaClient;
import com.vsware.services.teacherservices.model.TeacherModel;
import com.vsware.services.teacherservices.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final EurekaClient eurekaClient;

    private Faker faker;
    private String instanceId;

    public TeacherController(TeacherRepository teacherRepository, EurekaClient eurekaClient) {
        this.teacherRepository = teacherRepository;
        this.eurekaClient = eurekaClient;
        this.faker = new Faker();
    }

    @GetMapping("/")
    public Flux<TeacherModel> getAllTeachers() {
        log.info(getEurekaInfo() + ": executing: getAllTeachers()");
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<TeacherModel> getTeacherById(@PathVariable("id") String id) {
        log.info(getEurekaInfo() + ": executing: getTeacherById(" + id + ")");
        return teacherRepository.findById(id);
    }

    @GetMapping("/addRandomTeacher")
    public Mono<TeacherModel> addRandomTeacher() {
        log.info(getEurekaInfo() + ": executing: addRandomTeacher()");
        String[] fullRandomName = faker.name().fullName().split(" ");
        return teacherRepository.save(
                new TeacherModel(null, fullRandomName[0], fullRandomName[1])
        );
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
