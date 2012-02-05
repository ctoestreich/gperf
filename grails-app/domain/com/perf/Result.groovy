package com.perf

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true, excludes = "class, id")
class Result implements Serializable {

    String testName
    String details
    Integer executionTime = 0
    Date createDate = new Date()
    Boolean isError = false
}
