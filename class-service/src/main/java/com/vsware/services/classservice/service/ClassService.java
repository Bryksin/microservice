package com.vsware.services.classservice.service;

import com.github.javafaker.Faker;
import com.vsware.services.classservice.model.ClassModel;
import com.vsware.services.classservice.model.ClassResponseModel;
import com.vsware.services.classservice.network.CommunicationClient;
import com.vsware.services.classservice.network.StudentResponseModel;
import com.vsware.services.classservice.repository.ClassRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class ClassService {

    private final CommunicationClient netClient;
    private final ClassRepository classRepository;

    private Faker faker;
    private Random random;



    public ClassService(CommunicationClient netClient, ClassRepository classRepository) {
        this.netClient = netClient;
        this.classRepository = classRepository;

        faker = new Faker();
        random = new Random();
    }

    public Flux<ClassModel> getAll() {
        return classRepository.findAll();
    }

    public Mono<ClassModel> findById(String id) {
        return classRepository.findById(id);
    }

    public Mono<ClassModel> addRandomClass() {
        return Mono.fromCallable(() -> random.nextInt((100 - 10) + 1) + 10)
                .map(classRoom -> new ClassModel(null, classRoom.toString(),
                        faker.educator().course(), null, null))
                .flatMap(classRepository::save);
    }

    public Flux<ClassResponseModel> getAllDetailed() {
        return classRepository.findAll().flatMap(
                classModel -> Mono.zip(
                    netClient.getTeacherById(classModel.getTeacherId()),
                    netClient.getStudentsByIds(classModel.getStudentIds()).collectList(),
                    (teacher, students) -> {
                        ClassResponseModel responseModel = new ClassResponseModel();
                        responseModel.setId(classModel.getId());
                        responseModel.setRoomNr(classModel.getRoomNr());
                        responseModel.setClassName(classModel.getClassName());
                        responseModel.setTeacher(teacher);
                        responseModel.setStudents(students);
                        return responseModel;
                    })
                );
    }

    public Mono<ClassResponseModel> findByIdDetailed(String id) {
        return classRepository.findById(id).map(this::mapClassModelToClassResponseModel);
    }

    public Mono<ClassModel> addRandomClassFull() {
        return Mono.zip(
                netClient.addRandomTeacher(),
                netClient.addRandomStudents(random.nextInt((30 - 5) + 1) + 5).collectList(),
                (teacher, students) -> {
                    ClassModel classModel = new ClassModel();
                    classModel.setRoomNr(Integer.toString(random.nextInt((100 - 10) + 1) + 10));
                    classModel.setClassName(faker.educator().course());
                    classModel.setTeacherId(teacher.getId());
                    for (StudentResponseModel student : students) {
                        classModel.addStudentId(student.getId());
                    }
                    return classModel;
                }).flatMap(classRepository::save);
    }

    private ClassResponseModel mapClassModelToClassResponseModel(ClassModel classModel) {
        ClassResponseModel resp = new ClassResponseModel();
        resp.setId(classModel.getId());
        resp.setClassName(classModel.getClassName());
        resp.setRoomNr(classModel.getRoomNr());

        if (classModel.getTeacherId() != null)
            netClient.getTeacherById(classModel.getTeacherId()).doOnSuccess(resp::setTeacher);

        if (classModel.getStudentIds() != null)
            classModel.getStudentIds().forEach(studentId ->
                    netClient.getStudentById(studentId).doOnSuccess(resp::addStudent));
        return resp;
    }

    public Mono<Void> deleteAll() {
        return classRepository.deleteAll();
    }
}
