package com.perf

import grails.plugin.redis.RedisService
import com.perf.DataService

class DataController {

    RedisService redisService
    DataService dataService

     def populateSampleData = {
        dataService.populateSampleData()
        render text: "Sample Data Populated.", contentType: "text/plain"
    }

    def flushdb = {
        dataService.flushDB()
        render text: "Flushed Redis DB.", contentType: "text/plain"
    }

    def flushresults = {
        dataService.flushResults()
        render text: "Flushed results.", contentType: "text/plain"
    }
}
