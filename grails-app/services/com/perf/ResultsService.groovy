package com.perf

import grails.plugin.redis.RedisService

class ResultsService {

    static transactional = true
    RedisService redisService

    def getQueueCount(String queueName) {
        redisService.llen(queueName)
    }

    def getResultsStatistics(String queueName) {
        def items = redisService.lrange("${queueName}ResultsDuration", 0, -1)
        def errorCount = redisService.llen("${queueName}Error")

        if(items.size() == 0){
            return [cnt: "0", avg: "0", err: errorCount]
        }

        def sum = items?.collect { it.toInteger() }?.sum() ?: 0
        def count = items?.size() ?: 0
        def average = 0
        try {
            average = ((count && sum) ? (sum / count) : 0)
        } catch(Exception e){
            println e.message
        }

        [cnt: count, avg: average, err: errorCount]
    }

    void saveResults(String jobName, def result) {
        //redisService.sadd(jobName)
        redisService.rpush(jobName, result.toString())
        redisService.rpush(jobName + "Duration", result.executionTime.toString())
        //result.save()
    }

    private Map createMap(String data) {
        def map = [:]
        data.findAll(/([^&=]+)=([^&]+)/) { full, name, value ->  map[name] = value }
        map
    }
}
