package com.vsware.services.classservice.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseModel {

    private String id;
    private String name;
    private String surName;

}
