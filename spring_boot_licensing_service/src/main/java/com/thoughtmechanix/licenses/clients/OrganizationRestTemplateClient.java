package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    // @Autowired
    // RestTemplate restTemplate;

    // OAuth2RestTemplate为RestTemplate的替代品。可以处理Authorization的传播
    @Autowired
    OAuth2RestTemplate restTemplate;

    public Organization getOrganization(String organizationId){
        /*
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://organizationservice/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);
         */

        // 通过zuul进行路由调用
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://zuulserver:5555/api/organization/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
