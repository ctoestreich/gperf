package com.perf

import grails.plugin.redis.RedisService

class PerformanceController {

    RedisService redisService
    WorkerService workerService

    def index = {
        render(view: "index")
    }

    def startWorkers = {
        if(params?.id && params?.workers) {
            workerService.startWorkers(params?.id, params?.workers)
            render text: PerformanceConstants.RUNNING, contentType: "text/plain"
        } else {
            redisService.set(params?.id, PerformanceConstants.ERROR)
            render text: PerformanceConstants.ERROR, contentType: "text/plain"
        }
    }

    def stopWorkers = {
         if(params?.id) {
            workerService.stopWorkers(params.id)
            render text: PerformanceConstants.STOPPED, contentType: "text/plain"
        } else {
            render text: PerformanceConstants.ERROR, contentType: "text/plain"
        }
    }

    def status = {
        //don't overwhelm client or browser
        render text: redisService.get(params?.id) ?: PerformanceConstants.IDLE, contentType: "text/plain"
    }
}