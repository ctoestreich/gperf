package com.perf.runners.math

import com.perf.Result
import com.perf.runners.AbstractPerformanceService
import com.perf.runners.PerformanceService

class LargeNumberPerformanceService extends AbstractPerformanceService implements PerformanceService {

    Result performanceTest() {
        Long result = 1
        100000.times {
            result += it
        }
        new Result()
    }
}
