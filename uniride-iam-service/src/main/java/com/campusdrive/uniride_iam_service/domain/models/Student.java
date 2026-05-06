package com.campusdrive.uniride_iam_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;
    private String studentCode;
    private String universityName;
    private String tiuPhoto;
    private User user;
}
