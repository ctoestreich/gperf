package com.perf.runners.data

import com.perf.result.ComparisonResult
import com.perf.Result
import com.perf.runners.AbstractPerformanceService

class DataComparisonPerformanceService extends AbstractPerformanceService {

    Result performTest() {
        def result1="", result2=""
        def fallAsleepOnADate = { sleep 1000; return new Date() }.memoize()
        def executionTime = benchmark {
            result1 = fallAsleepOnADate()
            result2 = fallAsleepOnADate()
        }
        new ComparisonResult(result1: result1, result2: result2, testName: 'Data Comparison - Memoize Sleep Date', executionTime: executionTime)
    }
}

