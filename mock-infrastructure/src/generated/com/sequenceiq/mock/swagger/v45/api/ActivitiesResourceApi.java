/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v45.api;

import com.sequenceiq.mock.swagger.model.ApiActivity;
import com.sequenceiq.mock.swagger.model.ApiActivityList;
import com.sequenceiq.mock.swagger.model.ApiMetricList;
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

@Api(value = "ActivitiesResource", description = "the ActivitiesResource API")
@RequestMapping(value = "/{mockUuid}/api/v45")
public interface ActivitiesResourceApi {

    Logger log = LoggerFactory.getLogger(ActivitiesResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Fetch metric readings for a particular activity.", nickname = "getMetrics", notes = "Fetch metric readings for a particular activity. <p> By default, this call will look up all metrics available for the activity. If only specific metrics are desired, use the <i>metrics</i> parameter. <p> By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults to the current server time). The <i>from</i> and <i>to</i> parameters can be used to control the window being queried. A maximum window of 3 hours is enforced. <p> When requesting a \"full\" view, aside from the extended properties of the returned metric data, the collection will also contain information about all metrics available for the activity, even if no readings are available in the requested window.", response = ApiMetricList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ActivitiesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of readings from the monitors.", response = ApiMetricList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/activities/{activityId}/metrics",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiMetricList> getMetrics(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the activity.",required=true) @PathVariable("activityId") String activityId,@ApiParam(value = "The name of the cluster.",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The name of the service.",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "Start of the period to query.") @Valid @RequestParam(value = "from", required = false) String from,@ApiParam(value = "Filter for which metrics to query.") @Valid @RequestParam(value = "metrics", required = false) List<String> metrics,@ApiParam(value = "End of the period to query.", defaultValue = "now") @Valid @RequestParam(value = "to", required = false, defaultValue="now") String to,@ApiParam(value = "The view of the data to materialize, either \"summary\" or \"full\".", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default ActivitiesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Read all activities in the system.", nickname = "readActivities", notes = "Read all activities in the system", response = ApiActivityList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ActivitiesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of activities", response = ApiActivityList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/activities",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiActivityList> readActivities(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the cluster",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The name of the service",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "The maximum number of activities to return.", defaultValue = "100") @Valid @RequestParam(value = "maxResults", required = false, defaultValue="100") Integer maxResults,@ApiParam(value = "The query to perform to find activities in the system. By default, this call returns top level (i.e. root) activities that have currently started. <p> The query specifies the intersection of a list of constraints, joined together with semicolons (without spaces). For example: </p> <dl> <dt>status==started;parent==</dt> <dd>looks for running root activities. This is also the default query.</dd> <dt>status==failed;finishTime=gt=2012-04-01T20:30:00.000Z</dt> <dd>looks for failed activities after the given date time.</dd> <dt>name==Pi Estimator;startTime=gt=2012-04-01T20:30:00.000Z</dt> <dd>looks for activities started after the given date time, with the name of \"Pi Estimator\".</dd> <dt>startTime=lt=2012-01-02T00:00:00.000Z;finishTime=ge=2012-01-01T00:00:00.000Z</dt> <dd>looks for activities that are active on 2012 New Year's Day. Note that they may start before or finish after that day.</dd> <dt>status==failed;parent==000014-20120425161321-oozie-joe</dt> <dd>looks for failed child activities of the given parent activity id.</dd> <dt>status==started;metrics.cpu_user=gt=10</dt> <dd>looks for started activities that are using more than 10 cores per second.</dd> <dt>type==hive;metrics.user==bc;finishTime=gt=2012-04-01T20:30:00.000Z</dt> <dd>looks for all hive queries submitted by user bc after the given date time.</dd> </dl>  You may query any fields present in the ApiActivity object. You can also query by activity metric values using the <em>metrics.*</em> syntax. Values for date time fields should be ISO8601 timestamps. <p> The valid comparators are <em>==</em>, <em>!=</em>, <em>=lt=</em>, <em>=le=</em>, <em>=ge=</em>, and <em>=gt=</em>. They stand for \"==\", \"!=\", \"&lt;\", \"&lt;=\", \"&gt;=\", \"&gt;\" respectively.", defaultValue = "status==started;parent==") @Valid @RequestParam(value = "query", required = false, defaultValue="status==started;parent==") String query,@ApiParam(value = "Specified the offset of activities to return.", defaultValue = "0") @Valid @RequestParam(value = "resultOffset", required = false, defaultValue="0") Integer resultOffset,@ApiParam(value = "The view of the activities to materialize", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"type\" : \"MR\",    \"parent\" : \"...\",    \"startTime\" : \"...\",    \"finishTime\" : \"...\",    \"id\" : \"...\",    \"status\" : \"FAILED\",    \"user\" : \"...\",    \"group\" : \"...\",    \"inputDir\" : \"...\",    \"outputDir\" : \"...\",    \"mapper\" : \"...\",    \"combiner\" : \"...\",    \"reducer\" : \"...\",    \"queueName\" : \"...\",    \"schedulerPriority\" : \"...\"  }, {    \"name\" : \"...\",    \"type\" : \"OOZIE\",    \"parent\" : \"...\",    \"startTime\" : \"...\",    \"finishTime\" : \"...\",    \"id\" : \"...\",    \"status\" : \"ASSUMED_SUCCEEDED\",    \"user\" : \"...\",    \"group\" : \"...\",    \"inputDir\" : \"...\",    \"outputDir\" : \"...\",    \"mapper\" : \"...\",    \"combiner\" : \"...\",    \"reducer\" : \"...\",    \"queueName\" : \"...\",    \"schedulerPriority\" : \"...\"  } ]}", ApiActivityList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ActivitiesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns a specific activity in the system.", nickname = "readActivity", notes = "Returns a specific activity in the system", response = ApiActivity.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ActivitiesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The Activity object with the specified id", response = ApiActivity.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/activities/{activityId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiActivity> readActivity(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The id of the activity to retrieve",required=true) @PathVariable("activityId") String activityId,@ApiParam(value = "The name of the cluster",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The name of the service",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "The view of the activity to materialize", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"type\" : \"MR\",  \"parent\" : \"...\",  \"startTime\" : \"...\",  \"finishTime\" : \"...\",  \"id\" : \"...\",  \"status\" : \"SUBMITTED\",  \"user\" : \"...\",  \"group\" : \"...\",  \"inputDir\" : \"...\",  \"outputDir\" : \"...\",  \"mapper\" : \"...\",  \"combiner\" : \"...\",  \"reducer\" : \"...\",  \"queueName\" : \"...\",  \"schedulerPriority\" : \"...\"}", ApiActivity.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ActivitiesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns the child activities.", nickname = "readChildActivities", notes = "Returns the child activities", response = ApiActivityList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ActivitiesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The list of child activities for the specified activity", response = ApiActivityList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/activities/{activityId}/children",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiActivityList> readChildActivities(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The id of the activity",required=true) @PathVariable("activityId") String activityId,@ApiParam(value = "The name of the cluster",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The name of the service",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "The maximum number of activities to return.", defaultValue = "100") @Valid @RequestParam(value = "maxResults", required = false, defaultValue="100") Integer maxResults,@ApiParam(value = "Specified the offset of activities to return.", defaultValue = "0") @Valid @RequestParam(value = "resultOffset", required = false, defaultValue="0") Integer resultOffset,@ApiParam(value = "The view of the children to materialize", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"type\" : \"PIG\",    \"parent\" : \"...\",    \"startTime\" : \"...\",    \"finishTime\" : \"...\",    \"id\" : \"...\",    \"status\" : \"UNKNOWN\",    \"user\" : \"...\",    \"group\" : \"...\",    \"inputDir\" : \"...\",    \"outputDir\" : \"...\",    \"mapper\" : \"...\",    \"combiner\" : \"...\",    \"reducer\" : \"...\",    \"queueName\" : \"...\",    \"schedulerPriority\" : \"...\"  }, {    \"name\" : \"...\",    \"type\" : \"STREAMING\",    \"parent\" : \"...\",    \"startTime\" : \"...\",    \"finishTime\" : \"...\",    \"id\" : \"...\",    \"status\" : \"UNKNOWN\",    \"user\" : \"...\",    \"group\" : \"...\",    \"inputDir\" : \"...\",    \"outputDir\" : \"...\",    \"mapper\" : \"...\",    \"combiner\" : \"...\",    \"reducer\" : \"...\",    \"queueName\" : \"...\",    \"schedulerPriority\" : \"...\"  } ]}", ApiActivityList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ActivitiesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns a list of similar activities.", nickname = "readSimilarActivities", notes = "Returns a list of similar activities", response = ApiActivityList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "ActivitiesResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The list of similar activities to the specified activity", response = ApiActivityList.class) })
    @RequestMapping(value = "/clusters/{clusterName}/services/{serviceName}/activities/{activityId}/similar",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiActivityList> readSimilarActivities(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The id of the activity",required=true) @PathVariable("activityId") String activityId,@ApiParam(value = "The name of the cluster",required=true) @PathVariable("clusterName") String clusterName,@ApiParam(value = "The name of the service",required=true) @PathVariable("serviceName") String serviceName,@ApiParam(value = "The view of the activities to materialize", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"type\" : \"UNKNOWN\",    \"parent\" : \"...\",    \"startTime\" : \"...\",    \"finishTime\" : \"...\",    \"id\" : \"...\",    \"status\" : \"STARTED\",    \"user\" : \"...\",    \"group\" : \"...\",    \"inputDir\" : \"...\",    \"outputDir\" : \"...\",    \"mapper\" : \"...\",    \"combiner\" : \"...\",    \"reducer\" : \"...\",    \"queueName\" : \"...\",    \"schedulerPriority\" : \"...\"  }, {    \"name\" : \"...\",    \"type\" : \"HIVE\",    \"parent\" : \"...\",    \"startTime\" : \"...\",    \"finishTime\" : \"...\",    \"id\" : \"...\",    \"status\" : \"SUSPENDED\",    \"user\" : \"...\",    \"group\" : \"...\",    \"inputDir\" : \"...\",    \"outputDir\" : \"...\",    \"mapper\" : \"...\",    \"combiner\" : \"...\",    \"reducer\" : \"...\",    \"queueName\" : \"...\",    \"schedulerPriority\" : \"...\"  } ]}", ApiActivityList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ActivitiesResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
