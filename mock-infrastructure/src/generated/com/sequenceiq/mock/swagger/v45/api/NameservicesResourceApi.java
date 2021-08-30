/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v45.api;

import com.sequenceiq.mock.swagger.model.ApiMetricList;
import com.sequenceiq.mock.swagger.model.ApiNameservice;
import com.sequenceiq.mock.swagger.model.ApiNameserviceList;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")

@Api(value = "NameservicesResource", description = "the NameservicesResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface NameservicesResourceApi {

    Logger log = LoggerFactory.getLogger(NameservicesResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Fetch metric readings for a particular nameservice.", nickname = "getMetrics", notes = "Fetch metric readings for a particular nameservice. <p> By default, this call will look up all metrics available. If only specific metrics are desired, use the <i>metrics</i> parameter. <p> By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults to the current server time). The <i>from</i> and <i>to</i> parameters can be used to control the window being queried. A maximum window of 3 hours is enforced. <p> When requesting a \"full\" view, aside from the extended properties of the returned metric data, the collection will also contain information about all metrics available, even if no readings are available in the requested window.", response = ApiMetricList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "NameservicesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of readings from the monitors.", response = ApiMetricList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/nameservices/{nameservice}/metrics",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiMetricList> getMetrics(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The nameservice.",required=true) @PathVariable("nameservice") String nameservice,@ApiParam(value = "The service name.",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "Start of the period to query.") @Valid @RequestParam(value = "from", required = false) String from,@ApiParam(value = "Filter for which metrics to query.") @Valid @RequestParam(value = "metrics", required = false) List<String> metrics,@ApiParam(value = "End of the period to query.", defaultValue = "now") @Valid @RequestParam(value = "to", required = false, defaultValue="now") String to,@ApiParam(value = "The view of the data to materialize, either \"summary\" or \"full\".", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"context\" : \"...\",    \"unit\" : \"...\",    \"data\" : [ {      \"timestamp\" : \"...\",      \"value\" : 12345.0    }, {      \"timestamp\" : \"...\",      \"value\" : 12345.0    } ],    \"displayName\" : \"...\",    \"description\" : \"...\"  }, {    \"name\" : \"...\",    \"context\" : \"...\",    \"unit\" : \"...\",    \"data\" : [ {      \"timestamp\" : \"...\",      \"value\" : 12345.0    }, {      \"timestamp\" : \"...\",      \"value\" : 12345.0    } ],    \"displayName\" : \"...\",    \"description\" : \"...\"  } ]}", ApiMetricList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default NameservicesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "List the nameservices of an HDFS service.", nickname = "listNameservices", notes = "List the nameservices of an HDFS service.", response = ApiNameserviceList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "NameservicesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of nameservices.", response = ApiNameserviceList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/nameservices",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiNameserviceList> listNameservices(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The service name.",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "The view of the data to materialize, either \"summary\" or \"full\".", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"active\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"GOOD\",      \"roleStatus\" : \"HISTORY_NOT_AVAILABLE\"    },    \"activeFailoverController\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"HISTORY_NOT_AVAILABLE\",      \"roleStatus\" : \"STARTED\"    },    \"standBy\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"NOT_AVAILABLE\",      \"roleStatus\" : \"BUSY\"    },    \"standByFailoverController\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"BAD\",      \"roleStatus\" : \"HISTORY_NOT_AVAILABLE\"    },    \"secondary\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"HISTORY_NOT_AVAILABLE\",      \"roleStatus\" : \"NA\"    },    \"mountPoints\" : [ \"...\", \"...\" ],    \"healthSummary\" : \"GOOD\",    \"healthChecks\" : [ {      \"name\" : \"...\",      \"summary\" : \"NOT_AVAILABLE\",      \"explanation\" : \"...\",      \"suppressed\" : true    }, {      \"name\" : \"...\",      \"summary\" : \"DISABLED\",      \"explanation\" : \"...\",      \"suppressed\" : true    } ]  }, {    \"name\" : \"...\",    \"active\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"GOOD\",      \"roleStatus\" : \"NA\"    },    \"activeFailoverController\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"DISABLED\",      \"roleStatus\" : \"STARTING\"    },    \"standBy\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"DISABLED\",      \"roleStatus\" : \"STOPPING\"    },    \"standByFailoverController\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"BAD\",      \"roleStatus\" : \"UNKNOWN\"    },    \"secondary\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\",      \"healthSummary\" : \"DISABLED\",      \"roleStatus\" : \"STARTED\"    },    \"mountPoints\" : [ \"...\", \"...\" ],    \"healthSummary\" : \"NOT_AVAILABLE\",    \"healthChecks\" : [ {      \"name\" : \"...\",      \"summary\" : \"BAD\",      \"explanation\" : \"...\",      \"suppressed\" : true    }, {      \"name\" : \"...\",      \"summary\" : \"CONCERNING\",      \"explanation\" : \"...\",      \"suppressed\" : true    } ]  } ]}", ApiNameserviceList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default NameservicesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Retrieve information about a nameservice.", nickname = "readNameservice", notes = "Retrieve information about a nameservice.", response = ApiNameservice.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "NameservicesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Details of the nameservice.", response = ApiNameservice.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/nameservices/{nameservice}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiNameservice> readNameservice(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The nameservice to retrieve.",required=true) @PathVariable("nameservice") String nameservice,@ApiParam(value = "The service name.",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "The view to materialize. Defaults to 'full'.", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"active\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\",    \"healthSummary\" : \"GOOD\",    \"roleStatus\" : \"HISTORY_NOT_AVAILABLE\"  },  \"activeFailoverController\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\",    \"healthSummary\" : \"GOOD\",    \"roleStatus\" : \"HISTORY_NOT_AVAILABLE\"  },  \"standBy\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\",    \"healthSummary\" : \"DISABLED\",    \"roleStatus\" : \"STARTING\"  },  \"standByFailoverController\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\",    \"healthSummary\" : \"NOT_AVAILABLE\",    \"roleStatus\" : \"STARTING\"  },  \"secondary\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\",    \"healthSummary\" : \"GOOD\",    \"roleStatus\" : \"NA\"  },  \"mountPoints\" : [ \"...\", \"...\" ],  \"healthSummary\" : \"GOOD\",  \"healthChecks\" : [ {    \"name\" : \"...\",    \"summary\" : \"GOOD\",    \"explanation\" : \"...\",    \"suppressed\" : true  }, {    \"name\" : \"...\",    \"summary\" : \"NOT_AVAILABLE\",    \"explanation\" : \"...\",    \"suppressed\" : true  } ]}", ApiNameservice.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default NameservicesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
