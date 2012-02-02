package com.perf

import grails.plugin.redis.RedisService

class DataService {

    static transactional = false
    RedisService redisService

    def populateSampleData(){
        //put some data into redis to use for lookups here
    }

    def flushDB() {
        redisService.flushDB()
    }

    def flushResults() {
        redisService.keys("*${PerformanceConstants.RESULTS}").each {
            redisService.del(it)
        }

        redisService.keys("*${PerformanceConstants.ERROR}").each {
            redisService.del(it)
        }

        redisService.keys("*${PerformanceConstants.DURATION}").each {
            redisService.del(it)
        }
    }
}
