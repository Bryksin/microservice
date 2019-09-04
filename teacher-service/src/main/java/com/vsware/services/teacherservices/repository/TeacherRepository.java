package com.vsware.services.teacherservices.repository;

import com.vsware.services.teacherservices.model.TeacherModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends ReactiveCrudRepository<TeacherModel, String> {
}
