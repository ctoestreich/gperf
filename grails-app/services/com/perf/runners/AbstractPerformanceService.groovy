package com.perf.runners

import com.perf.AbstractPerformanceAnalysisJob
import grails.plugin.redis.RedisService
import com.perf.ResultsService

abstract class AbstractPerformanceService extends AbstractPerformanceAnalysisJob {

    static transactional = false
    RedisService redisService
    ResultsService resultsService

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
