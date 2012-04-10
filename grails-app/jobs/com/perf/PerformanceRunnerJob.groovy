package com.perf

import com.perf.runners.PerformanceService
import grails.plugin.redis.RedisService
import org.codehaus.groovy.grails.commons.GrailsApplication

class PerformanceRunnerJob {

    GrailsApplication grailsApplication
    RedisService redisService
    ResultsService resultsService

    void perform(jobName, workers) {
        println "jesque queueing up job ${jobName} with ${workers} threads"
        Class clazz = grailsApplication.config?.perf?.runners[jobName]?.workerClass
        if(!clazz) {
            log.error "Can not start a performance worker without a workerClass defined in the config attribute"
        }
        PerformanceService service = (PerformanceService) grailsApplication.mainContext.getBean(clazz)

        if(!grailsApplication.config?.perf?.multiServer) {
            doWorkAsync(service, jobName, workers)
        } else {
            doWork(service, jobName, workers)
        }
    }

    /**
     * will spawn as many threads as their are workers passed in
     */
    private doWorkAsync(service, jobName, workers) {
        def threads = Integer.parseInt(workers)
        threads.times {
            runAsync {
                doWork(service, jobName, workers)
            }
        }
    }

    /**
     * invokes the service and collects and saves the result
     */
    private doWork(service, jobName, workers) {
        println "running ${jobName} on thread :: ${Thread.currentThread().id}"
        while(redisService.get(jobName) == PerformanceConstants.RUNNING) {
            Result result = service.performTest()
            println "blah" + result
            saveResults(jobName, result)
        }
    }

    private void saveResults(String jobName, Result result) {
        resultsService.saveResults(jobName, result)
    }
}
