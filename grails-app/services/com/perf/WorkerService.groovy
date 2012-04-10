package com.perf

class WorkerService {

    static transactional = false
    def redisService
    def jesqueService
    def grailsApplication

    def resumeWorkers() {

    }

    def startWorkers(String jobName, String workers) {
        log.debug "Starting $jobName with $workers workers"
        redisService.set(jobName, PerformanceConstants.RUNNING)
        workers = !grailsApplication.config?.perf?.multiServer ? workers : 1
        Integer.parseInt(workers).times {
            jesqueService.enqueue('gPerfQueue', PerformanceRunnerJob.simpleName, jobName, workers)
        }
    }

    def stopWorkers(String jobName) {
        log.debug "Stopping ${jobName}"
        redisService.set(jobName, PerformanceConstants.STOPPED)
        redisService.set("${jobName}${PerformanceConstants.WORKERS}", "0")
    }
}
