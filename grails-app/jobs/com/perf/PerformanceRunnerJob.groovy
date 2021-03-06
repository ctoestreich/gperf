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
        Integer.parseInt(workers).times {
            runAsync {
                println "running ${jobName} on thread :: ${Thread.currentThread().id}"
                while(redisService.get(jobName) == PerformanceConstants.RUNNING) {
                    saveResults(jobName, service.performTest())
                }
            }
        }
    }

    private void saveResults(String jobName, Result result) {
        log.debug result
        resultsService.saveResults(jobName, result)
    }
}
