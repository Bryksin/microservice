package com.vsware.services.classservice.model;

import com.vsware.services.classservice.network.StudentResponseModel;
import com.vsware.services.classservice.network.TeacherResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponseModel {

    private String id;
    private String roomNr;
    private String className;
    private TeacherResponseModel teacher;
    private List<StudentResponseModel> students;


    public void addStudent(StudentResponseModel student) {
        if (students == null)
            students = new ArrayList<>();
        students.add(student);
    }

}
