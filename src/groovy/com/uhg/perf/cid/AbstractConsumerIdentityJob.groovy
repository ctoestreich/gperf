package com.uhg.perf.cid

import com.unitedhealthgroup.clinical.schema.contract.identity.v1.GetConsumerIdentityRequest
import com.unitedhealthgroup.clinical.schema.canonical.identity.eligibilitycriteria.v1_00.EligibilityCriteria

import com.uhg.perf.AbstractPerformanceAnalysisJob
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

/**
 */
abstract class AbstractConsumerIdentityJob extends AbstractPerformanceAnalysisJob {

    ConsumerDataFactoryService dataFactoryService

     protected GetConsumerIdentityRequest createGetConsumerIdentityRequest() {
        def member = dataFactoryService.getRandomMember()
        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xgcal.year = 1951
        xgcal.month = 3
        xgcal.day = 7
        //SRC_SYS_CNSM_ID	SRC_SYS_FST_NM	SRC_SYS_LST_NM	SRC_SYS_DOB_DT	SRC_SYS_ELIG_SRCH_ID
        //126510	DEBORAH	SHANNON	1951-03-07 00:00:00	00022403408
        EligibilityCriteria criteria = new EligibilityCriteria(firstName: 'DEBORAH', lastName: 'SHANNON', dateOfBirth: xgcal, eligibilitySearchIdentifier: '00022403408')
        new GetConsumerIdentityRequest(eligibilityCriteria: criteria, sourceSystemCode: "ICUE", sourceSystemConsumerIdentifier: "126510")
    }

    abstract performanceTest()
}
