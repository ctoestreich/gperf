package com.uhg.perf

import grails.plugin.redis.RedisService

import com.perf.ResultsService
import grails.converters.JSON

class ResultsController {

    ResultsService resultsService
    RedisService redisService

    def resultDetails = {
        def results = []
        def itemCount = 0
        def max = params?.max?.toInteger() ?: 10
        def offset = params?.offset?.toInteger() ?: 0
        def type = redisService.type(params?.queue)
        if(type == 'list') {
            results = redisService.lrange(params?.queue, offset, max+offset-1)
            itemCount = redisService.llen(params?.queue)
        }
        render(view: "resultDetails", model: [itemCount: itemCount, results: results, queue: params?.queue])
    }

    def resultKeys = {
        def keyCount = redisService.keys("*").size()
        def max = params?.max?.toInteger() ?: 10
        def offset = params?.offset?.toInteger() ?: 0
        def usedKeys = redisService.keys("*").sort { it }
        def keys = []

        usedKeys.eachWithIndex {key, i ->
            if(i >= offset && i < (offset + max)) {
                keys << [key: key, type: redisService.type(key), value: getKeyValue(key)]
            }
        }
        render(view: "resultKeys", model: [key: params?.key, keyCount: keyCount, keys: keys])
    }

    def getKeyValue(key) {
        def details = null
        if(key) {
            def type = redisService.type(key)
            //println "type of ${key} is ${type}"
            if(type == "hash") {
                details = redisService.hgetAll(key)
            } else if(type == "string") {
                details = redisService.get(key)
            } else if(type == "list") {
                details = redisService.llen(key)
            }
        }
        details
    }

    def statistics = {
        def map = [cnt: 0, avg: 0, err: 0]
        if(params?.id) {
            map = resultsService.getResultsStatistics(params.id)
        }
        render map as JSON
    }
}
