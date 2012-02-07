package com.perf

import grails.plugin.redis.RedisService
import groovy.json.JsonBuilder
import grails.converters.JSON

class ResultsService {

    static transactional = false
    RedisService redisService

    def getQueueCount(String queueName) {
        redisService.llen(queueName)
    }

    def getResultsStatistics(String jobName) {
        def items = redisService.lrange("${jobName + PerformanceConstants.DURATION}", 0, -1)
        def errorCount = redisService.llen("${jobName + PerformanceConstants.ERROR}")

        if(items.size() == 0) {
            return [cnt: "0", avg: "0", err: errorCount]
        }

        def sum = items?.collect { it.toInteger() }?.sum() ?: 0
        def count = items?.size() ?: 0
        def average = 0
        try {
            average = ((count && sum) ? (sum / count) : 0)
        } catch (Exception e) {
            println e.message
        }

        [cnt: count, avg: average, err: errorCount]
    }

    void saveResults(String jobName, Result result) {
        println result as JSON
        redisService.rpush(jobName + (result?.isError ? PerformanceConstants.ERROR : PerformanceConstants.RESULTS), (result as JSON).toString())
        redisService.rpush(jobName + PerformanceConstants.DURATION, result.executionTime.toString())
    }
}
