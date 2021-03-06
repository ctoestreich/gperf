import com.perf.DataService
import com.perf.PerformanceRunnerJob
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {

    DataService dataService
    GrailsApplication grailsApplication
    def executorService

    def init = { servletContext ->
        dataService.populateSampleData()

        PerformanceRunnerJob.metaClass.runAsync = { Runnable runme ->
            executorService.withPersistence(runme)
        }

        PerformanceRunnerJob.metaClass.callAsync = { Closure clos ->
            executorService.withPersistence(clos)
        }

        PerformanceRunnerJob.metaClass.callAsync = { Runnable runme, returnval ->
            executorService.withPersistence(runme, returnval)
        }
    }
    def destroy = {
    }


}
