package com.perf

import com.perf.runners.PerformanceService
import grails.plugin.redis.RedisService
import org.codehaus.groovy.grails.commons.GrailsApplication

class PerformanceRunnerJob {

    GrailsApplication grailsApplication
    RedisService redisService
    ResultsService resultsService
    def executorService

    def perform(jobName, workers) {
        println "jesque queueing up job ${jobName} with ${workers} threads"
        Class clazz = grailsApplication.config?.perf?.runners[jobName]?.workerClass
        if(!clazz) {
            log.error "Can not start a performance worker without a workerClass defined in the config attribute"
        }
        PerformanceService service = (PerformanceService) grailsApplication.mainContext.getBean(clazz)

        def threads = Integer.parseInt(workers)
        threads.times {
            callAsync {
                while(redisService.get(jobName) == PerformanceConstants.RUNNING) {
                    Result result = service.performTest()
                    saveResults(jobName, result)
                }
            }
        }
    }

    def doWork(service, jobName, workers) {
        while(redisService.get(jobName) == PerformanceConstants.RUNNING) {
            Result result = service.performTest()
            saveResults(jobName, result)
        }
    }

    private void saveResults(String jobName, Result result) {
        resultsService.saveResults(jobName, result)
    }
}
