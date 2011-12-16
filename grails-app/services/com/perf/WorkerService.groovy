package com.perf

class WorkerService {

    static transactional = false
    def redisService
    def resultsService
    def jesqueService

    def resumeWorkers() {

    }

    def startWorkers(String jobName, String workers) {
        log.debug "Starting $jobName with $workers workers"
        redisService.set(jobName, PerformanceConstants.RUNNING)
        jesqueService.enqueue('gPerfQueue',PerformanceRunnerJob.simpleName, jobName, workers)
    }

    def stopWorkers(String jobName) {
        log.debug "Stopping ${jobName}"
        redisService.set(jobName, PerformanceConstants.STOPPED)
        redisService.set("${jobName + PerformanceConstants.WORKERS}", "0")
    }
}
