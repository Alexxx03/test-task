package org.job.interview.roombookingservice.controller.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;
    private String permissionCode;
    private String description;
}
