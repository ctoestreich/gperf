package com.perf

class Result implements Serializable {

    String testName
    String details
    Integer executionTime = 0
    Date createDate = new Date()
    Boolean isError = false

//    static constraints = {
//        testName blank: false
//        executionTime blank: false
//    }

    public String toString() {
        "testName=" + testName +
        "&details=" + details +
        "&executionTime=" + executionTime +
        "&createDate=" + createDate +
        "&isError=" + isError
    }
}
