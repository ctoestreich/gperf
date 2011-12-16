package com.perf

import grails.plugin.redis.RedisService

class DataService {

    static transactional = false
    RedisService redisService

    def populateSampleConsumerData() {
//        def consumerData = new XmlSlurper().parseText(new File("docs/stock_sample.xml").text)
//        consumerData.ROWSET.ROW.each { row ->
//            redisService.hset("consumerid:${row.CNSM_IDNTY_ID}".toString(), "consumerID", row?.CNSM_IDNTY_ID.text())
//            redisService.hset("consumerid:${row.CNSM_IDNTY_ID}".toString(), "sourceSystemConsumerID", row?.SRC_SYS_CNSM_ID.text())
//            redisService.hset("consumerid:${row.CNSM_IDNTY_ID}".toString(), "firstName", row?.SRC_SYS_FST_NM.text())
//            redisService.hset("consumerid:${row.CNSM_IDNTY_ID}".toString(), "lastName", row?.SRC_SYS_LST_NM.text())
//            redisService.hset("consumerid:${row.CNSM_IDNTY_ID}".toString(), "eligibilitySearchID", row?.SRC_SYS_ELIG_SRCH_ID.text())
//            redisService.hset("consumerid:${row.CNSM_IDNTY_ID}".toString(), "birthDate", row?.SRC_SYS_DOB_DT.text())
//        }
    }

    def flushDB() {
        redisService.flushDB()
    }

    def flushResults() {
        redisService.keys("*${PerformanceConstants.RESULTS}").each {
            redisService.del(it)
        }

        redisService.keys("*${PerformanceConstants.ERROR}").each {
            redisService.del(it)
        }

        redisService.keys("*${PerformanceConstants.DURATION}").each {
            redisService.del(it)
        }
    }
}
