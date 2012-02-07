package com.perf.result

import groovy.transform.ToString
import com.perf.Result

@ToString(includeNames = true, includeFields = true, excludes = "class, id")
class SimpleResult implements Serializable, Result {

    String testName
    String details
    Integer executionTime = 0
    Date createDate = new Date()
    Boolean isError = false
}
