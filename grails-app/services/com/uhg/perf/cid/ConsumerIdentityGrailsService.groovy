package com.uhg.perf.cid

import com.perf.AbstractPerformanceAnalysisJob



class ConsumerIdentityGrailsService extends AbstractPerformanceAnalysisJob {

//    static transactional = false
//    RedisService redisService
//    ResultsService resultsService
//
//    def invokeService(String workers) {
//        println "spawing grails test with ${workers} workers"
//        def threads = Integer.parseInt(workers)
//        threads.times {
//            runAsync {
//                while(redisService.get(PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB) == PerformanceConstants.RUNNING) {
//                    performTest()
//                }
//            }
//        }
//    }
//
//    private performTest() {
//        GetConsumerIdentityRequest request = createGetConsumerIdentityRequest()
//        GetConsumerIdentityResponse response = null
//        def details = [:]
//        def duration = benchmark {
//            try {
//                response = consumerIdentityGrails.getConsumerIdentity(request)
//                details = [msg:"Consumer ID: ${response?.consumerIdentity?.identifier}", error: false]
//            } catch (Exception ex) {
//                log.error ex
//                details = [msg:"Exception: ${ex}, ${ex.stackTrace.toString().substring(0,200)}", error: true]
//            }
//        }
//
//        resultsService.saveResults(
//                details?.error ? PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB_ERROR : PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB_RESULTS,
//                new Result(testName: PerformanceConstants.CONSUMER_IDENTITY_GRAILS_JOB, details: details?.msg ?: "", executionTime: duration))
//    }
//
//    private GetConsumerIdentityRequest createGetConsumerIdentityRequest() {
//        def member = getRandomMember()
//        println "using member $member"
//        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
//        xgcal.year = Integer.parseInt(member?.birthDate?.toString()?.substring(0,4) ?: "1900")
//        xgcal.month = Integer.parseInt(member?.birthDate?.toString()?.substring(5,7) ?: "01")
//        xgcal.day = Integer.parseInt(member?.birthDate?.toString()?.substring(8,10) ?: "01")
//        //SRC_SYS_CNSM_ID	SRC_SYS_FST_NM	SRC_SYS_LST_NM	SRC_SYS_DOB_DT	SRC_SYS_ELIG_SRCH_ID
//        //126510	DEBORAH	SHANNON	1951-03-07 00:00:00	00022403408
//        EligibilityCriteria criteria = new EligibilityCriteria(firstName: member?.firstName, lastName: member?.lastName, dateOfBirth: xgcal, eligibilitySearchIdentifier: member?.eligibilitySearchID)
//        new GetConsumerIdentityRequest(eligibilityCriteria: criteria, sourceSystemCode: "ICUE", sourceSystemConsumerIdentifier: member?.sourceSystemConsumerID)
//    }
//
//    private getRandomMember(){
//        String key = ""
//        Integer i = 0
//        while(!key.contains("consumer:") && i++ <= 100){
//            key = redisService.randomKey()
//        }
//        if(key.contains("consumer")){
//            return redisService.hgetAll(key)
//        } else {
//            return [:]
//        }
//    }
}
