package com.vsware.services.classservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassModel {

    @Id
    private String id;
    private String roomNr;
    private String className;
    private String teacherId;
    private List<String> studentIds;


    public void addStudentId(String studentId) {
        if (studentIds == null)
            studentIds = new ArrayList<>();
        studentIds.add(studentId);
    }

}
