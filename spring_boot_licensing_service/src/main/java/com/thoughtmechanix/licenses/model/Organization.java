package com.thoughtmechanix.licenses.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}
