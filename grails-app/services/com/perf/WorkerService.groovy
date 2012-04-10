package com.perf

class WorkerService {

    static transactional = false
    def redisService
    def jesqueService
    def grailsApplication

    def startWorkers(String jobName, String workers) {
        log.debug "Starting $jobName with $workers workers"
        redisService.set(jobName, PerformanceConstants.RUNNING)
        //Integer.parseInt(workers).times {
        //like the multiple threads per worker approach better than the 1->1 approach for now
        jesqueService.enqueue('gPerfQueue', PerformanceRunnerJob.simpleName, jobName, workers)
        //}
    }

    def stopWorkers(String jobName) {
        log.debug "Stopping ${jobName}"
        redisService.set(jobName, PerformanceConstants.STOPPED)
        redisService.set("${jobName}${PerformanceConstants.WORKERS}", "0")
    }
}
