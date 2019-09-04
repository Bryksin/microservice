package com.vsware.services.classservice.repository;

import com.vsware.services.classservice.model.ClassModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends ReactiveCrudRepository<ClassModel, String> {
}
