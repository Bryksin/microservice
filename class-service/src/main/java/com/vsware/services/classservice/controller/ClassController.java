package com.vsware.services.classservice.controller;

import com.netflix.discovery.EurekaClient;
import com.vsware.services.classservice.model.ClassModel;
import com.vsware.services.classservice.model.ClassResponseModel;
import com.vsware.services.classservice.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ClassController {

    private EurekaClient eurekaClient;
    private final ClassService classService;


    private String instanceId;

    public ClassController(EurekaClient eurekaClient, ClassService classService) {
        this.eurekaClient = eurekaClient;
        this.classService = classService;
    }

    @GetMapping("/")
    public Flux<ClassModel> getAllClasses() {
        log.info(getEurekaInfo() + ": executing: getAllClasses()");
        return classService.getAll();
    }

    //Detailed
    @GetMapping("/detailed")
    public Flux<ClassResponseModel> getAllClassesDetailed() {
        log.info(getEurekaInfo() + ": executing: getAllClasses()");
        return classService.getAllDetailed();
    }

    @GetMapping("/{id}")
    public Mono<ClassModel> getClassById(@PathVariable String id) {
        log.info(getEurekaInfo() + ": executing: getClassById(" + id + ")");
        return classService.findById(id);
    }

    @GetMapping("/addRandomClass")
    public Mono<ClassModel> addRandomClass() {
        log.info(getEurekaInfo() + ": executing: addRandomClass()");
        return classService.addRandomClass();
    }

    @GetMapping("/addRandomClassFull")
    public Mono<ClassModel> addRandomClassFull() {
        log.info(getEurekaInfo() + ": executing: addRandomClass()");
        return classService.addRandomClassFull();
    }

    @GetMapping("/deleteAll")
    public Mono<Void> deleteAll() {
        log.info(getEurekaInfo() + ": executing: deleteAll()");
        return classService.deleteAll();
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
