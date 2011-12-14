package com.uhg.perf.cid

import org.springframework.beans.factory.InitializingBean

class ConsumerDataFactoryService implements InitializingBean {

    def redisService

    void afterPropertiesSet() {
        println "HELLO"
    }
//    void afterPropertiesSet() {
    //        consumerData = new XmlSlurper().parseText(new File("docs/stock_sample.xml").text)
    //        println "got data ${consumerData}"
    //        consumerSize = consumerData.ROWSET.ROW.size()
    //        println "size $consumerSize"
    //    }

    /*
    <CNSM_IDNTY_ID>129982</CNSM_IDNTY_ID>
    <SRC_SYS_CNSM_ID>client13FN11Test</SRC_SYS_CNSM_ID>
    <SRC_SYS_FST_NM>CLIENT13FN11</SRC_SYS_FST_NM>
    <SRC_SYS_LST_NM>(null)</SRC_SYS_LST_NM>
    <SRC_SYS_ELIG_SRCH_ID>805041010</SRC_SYS_ELIG_SRCH_ID>
    <SRC_SYS_DOB_DT>2000-12-31</SRC_SYS_DOB_DT>
     */

    def getRandomMember() {
//        def row = consumerData.ROWSET.ROW[Math.floor(Math.random() * consumerSize ?: 1).asType(Integer.class)]
//        [consumerID: row?.CNSM_IDNTY_ID,
//                sourceSystemConsumerID: row?.SRC_SYS_CNSM_ID,
//                firstName: row?.SRC_SYS_FST_NM,
//                lastName: row?.SRC_SYS_LST_NM,
//                eligibilitySearchID: row?.SRC_SYS_ELIG_SRCH_ID,
//                birthDate: row?.SRC_SYS_DOB_DT
//        ]
    }

//    private loadData() {
//
//    }


}
