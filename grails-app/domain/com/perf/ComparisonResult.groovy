package com.perf

class ComparisonResult extends Result {
    String result1
    String result2

//    static constraints = {
    //        testName blank: false
    //        executionTime blank: false
    //    }

    public String toString() {
        "testName=" + testName +
        "&details=" + details +
        "&result1=" + result1 +
        "&result2=" + result2 +
        "&executionTime=" + executionTime +
        "&createDate=" + createDate
    }
}
