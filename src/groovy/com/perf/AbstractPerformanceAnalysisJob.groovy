package com.perf

import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.plugin.redis.RedisService

/**
 */
abstract class AbstractPerformanceAnalysisJob {

    /**
     * Wrap call to method with this closure to benchmark its performance
     * use like the following:
     * <code>
     * def duration = benchmark {*     doWork()
     *     doMoreWork()
     *}* println "took $duration ms"
     * </code>
     * @param closure Stuff to execute
     * @return Time in ms closure took to execute
     */
    protected Long benchmark(Closure closure) {
        def start = System.currentTimeMillis()
        closure.call()
        def now = System.currentTimeMillis()
        now - start
    }
}
