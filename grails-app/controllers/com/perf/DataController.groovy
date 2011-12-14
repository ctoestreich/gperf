package com.perf

import grails.plugin.redis.RedisService
import com.perf.DataService

class DataController {

    RedisService redisService
    DataService dataService

     def populateSampleConsumerData = {
        dataService.populateSampleConsumerData()
        render text: "sample data populated", contentType: "text/plain"
    }

    def flushdb = {
        dataService.flushDB()
        render text: "flushed db", contentType: "text/plain"
    }

    def flushresults = {
        dataService.flushResults()
        render text: "flushed results", contentType: "text/plain"
    }
}
