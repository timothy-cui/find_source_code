package com.thoughtmechanix.organization.events.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationChangeModel{
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
}