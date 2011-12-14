package com.uhg.perf.cid

import com.uhg.cid.v2.Identity
import com.uhg.perf.AbstractPerformanceAnalysisJob
import com.perf.ComparisonResult
import com.uhg.perf.PerformanceConstants
import com.unitedhealthgroup.clinical.schema.contract.identity.v1.IdentityService
import grails.converters.deep.XML
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

class ConsumerIdentityComparisonService extends AbstractPerformanceAnalysisJob {

    static transactional = true

    def redisService
    Identity consumerIdentityGrails
    IdentityService consumerIdentityJava
    def resultsService

    def invokeService(String workers) {
        println "spawing grails test with ${workers} workers"
        runAsync {
            10.times {
                while(redisService.get(PerformanceConstants.CONSUMER_IDENTITY_COMPARISON_JOB) == PerformanceConstants.RUNNING) {
                    performTest()
                }
            }
        }
    }

    private performTest() {
        com.unitedhealthgroup.clinical.schema.contract.identity.v1.GetConsumerIdentityResponse javaResponse
        com.uhg.cid.v2.GetConsumerIdentityResponse grailsResponse
        def details = [:]
        def member = getRandomMember()

        def duration = benchmark {
            try {
                javaResponse = consumerIdentityJava.getConsumerIdentity(createJavaRequest(member))
                grailsResponse = consumerIdentityGrails.getConsumerIdentity(createGrailsRequest(member))
                details = [msg: "1=grails,2=java", result1: (grailsResponse as XML).toString(), result2: (javaResponse as XML).toString()]
            } catch (Exception e) {
                log.error e
                details = [msg: "Exception: ${ex}, ${ex.stackTrace.toString().substring(0, 200)}", error: true]
            }
        }

        resultsService.saveResults(
                details?.error ? PerformanceConstants.CONSUMER_IDENTITY_COMPARISON_JOB_ERROR : PerformanceConstants.CONSUMER_IDENTITY_COMPARISON_JOB_RESULTS,
                new ComparisonResult(result1: details?.result1,
                                     result2: details?.result2,
                                     testName: PerformanceConstants.CONSUMER_IDENTITY_COMPARISON_JOB,
                                     details: details?.msg,
                                     executionTime: duration))
    }


    private com.uhg.cid.v2.GetConsumerIdentityRequest createGrailsRequest(member) {
        println "using member $member"
        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xgcal.year = Integer.parseInt(member?.birthDate?.toString()?.substring(0, 4) ?: "1900")
        xgcal.month = Integer.parseInt(member?.birthDate?.toString()?.substring(5, 7) ?: "01")
        xgcal.day = Integer.parseInt(member?.birthDate?.toString()?.substring(8, 10) ?: "01")
        //SRC_SYS_CNSM_ID	SRC_SYS_FST_NM	SRC_SYS_LST_NM	SRC_SYS_DOB_DT	SRC_SYS_ELIG_SRCH_ID
        //126510	DEBORAH	SHANNON	1951-03-07 00:00:00	00022403408
        com.uhg.cid.v2.EligibilityCriteria criteria = new com.uhg.cid.v2.EligibilityCriteria(firstName: member?.firstName, lastName: member?.lastName, dateOfBirth: xgcal, eligibilitySearchIdentifier: member?.eligibilitySearchID)
        new com.uhg.cid.v2.GetConsumerIdentityRequest(eligibilityCriteria: criteria, sourceSystemCode: "ICUE", sourceSystemConsumerIdentifier: member?.sourceSystemConsumerID)
    }

    private com.unitedhealthgroup.clinical.schema.contract.identity.v1.GetConsumerIdentityRequest createJavaRequest(member) {
        println "using member $member"
        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xgcal.year = Integer.parseInt(member?.birthDate?.toString()?.substring(0, 4) ?: "1900")
        xgcal.month = Integer.parseInt(member?.birthDate?.toString()?.substring(5, 7) ?: "01")
        xgcal.day = Integer.parseInt(member?.birthDate?.toString()?.substring(8, 10) ?: "01")
        //SRC_SYS_CNSM_ID	SRC_SYS_FST_NM	SRC_SYS_LST_NM	SRC_SYS_DOB_DT	SRC_SYS_ELIG_SRCH_ID
        //126510	DEBORAH	SHANNON	1951-03-07 00:00:00	00022403408
        com.unitedhealthgroup.clinical.schema.canonical.identity.eligibilitycriteria.v1_00.EligibilityCriteria criteria = new com.unitedhealthgroup.clinical.schema.canonical.identity.eligibilitycriteria.v1_00.EligibilityCriteria(firstName: member?.firstName, lastName: member?.lastName, dateOfBirth: xgcal, eligibilitySearchIdentifier: member?.eligibilitySearchID)
        new com.unitedhealthgroup.clinical.schema.contract.identity.v1.GetConsumerIdentityRequest(eligibilityCriteria: criteria, sourceSystemCode: "ICUE", sourceSystemConsumerIdentifier: member?.sourceSystemConsumerID)
    }

    private getRandomMember() {
        String key = ""
        Integer i = 0
        while(!key.contains("consumer:") && i++ <= 100) {
            key = redisService.randomKey()
        }
        if(key.contains("consumer")) {
            return redisService.hgetAll(key)
        } else {
            return [:]
        }
    }
}
