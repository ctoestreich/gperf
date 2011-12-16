package com.perf.runners.math

import com.perf.Result
import com.perf.runners.AbstractPerformanceService

class LargeNumberPerformanceService extends AbstractPerformanceService {

    Result performTest() {
        Long result = 1
        def executionTime = benchmark {
            100000.times {
                result += it
            }
        }
        new Result(testName: 'Long Number Performance Service', executionTime: executionTime)
    }
}
