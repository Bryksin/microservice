package com.vsware.services.studentservice.repository;

import com.vsware.services.studentservice.model.StudentModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<StudentModel, String> {

}
