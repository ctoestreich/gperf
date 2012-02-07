package com.perf.result

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true, excludes = "class, id")
class ComparisonResult extends SimpleResult {
    String result1
    String result2
}
