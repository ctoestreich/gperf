package com.perf

class PerformanceService {

    static transactional = false
    def redisService
    def resultsService

    def resumeWorkers() {

    }

    def startWorkers(String jobName, String workers) {
        if(log.isDebugEnabled()) log.debug "Starting $jobName with $workers workers"
//        redisService.set(jobName, PerformanceConstants.RUNNING)
//        redisService.set("${jobName}Workers", workers)
//        if(jobName == PerformanceConstants.CONSUMER_IDENTITY_JAVA_JOB){
//            consumerIdentityJavaService.invokeService(workers)
//        } else if(jobName == PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB){
//            consumerIdentityGrailsService.invokeService(workers)
//        } else if(jobName == PerformanceConstants.CONSUMER_IDENTITY_COMPARISON_JOB){
//            consumerIdentityComparisonService.invokeService(workers)
//        }
    }

    def stopWorkers(String jobName) {
        if(log.isDebugEnabled()) log.debug "Stopping ${jobName}"
//        redisService.set(jobName, PerformanceConstants.STOPPED)
//        redisService.set("${jobName}Workers", "0")
    }

//    private perfomanceTest(Closure closure) {
//
//        def response = null
//        String details = ""
//        Boolean hasError = false
//        def duration = benchmark {
//            try {
//                response = closure.call()
//                details = "cid:${response?.consumerIdentity?.identifier}"
//            } catch (Exception ex) {
//                details = "ex:${ex.message}"
//                hasError = true
//                log.error ex
//            }
//        }
//
//        resultsService.saveResults(
//                PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB_RESULTS + ((hasError) ? "Error" : ""),
//                new Result(testName: PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB, details: details, executionTime: duration))
//    }


}
