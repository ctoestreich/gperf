package com.perf.runners

import com.uhg.perf.AbstractPerformanceAnalysisJob
import grails.plugin.redis.RedisService
import org.codehaus.groovy.grails.commons.GrailsApplication

abstract class PerformanceBaseService extends AbstractPerformanceAnalysisJob {

    static transactional = false
    GrailsApplication grailsApplication
    RedisService redisService
    
    abstract
}
