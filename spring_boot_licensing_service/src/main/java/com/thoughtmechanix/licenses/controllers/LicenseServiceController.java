package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);
    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        return License.builder()
                .licenseId(licenseId)
                .productName("Teco")
                .licenseType("Seat")
                .organizationId(organizationId).build();
    }

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId,
                                         @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        logger.info("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }
}
