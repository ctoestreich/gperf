GRAILS PERFORMANCE FRAMEWORK
======

A simple framework with a jQuery widget on the front end to do performance testing of code using redis, jesque and executor.

Using the web admin console, configured jobs will be submitted to a queue in jesque with a type (workerClass) and number of threads to use.  A jesque worker will then pick up the queued jobs and spawn off a number of worker threads using executor.  These threads will continue to run until the user then stops the job via the admin console.  Results will be updated real-time to the screen using a custom ajax enabled jQuery widget.

The admin console provides the following functionality:

* View paged resultset of successful & error results per job
* List all keys in Redis
* Clear all results
* Clear all data (Flush Redis Database)

_There may be a slight delay in the updating of statistics both when starting and stopping the tests as the jQuery widget only updates stats every 5s._

### Create A Job ###

To create a new performance job simple create a new service in the services/com/perf/runners directory.  I have 3 sample runners created in this project.  It is important that you extend the `AbstractPerformanceService` and implement the `performTest` method.

The abstract service comtains a benchmark method to use to encapsulate a call you wish to time and it will return the execution time.

    class LargeNumberPerformanceService extends AbstractPerformanceService {
        Result performTest() {
            Long result = 1
            def executionTime = benchmark {
                100000.times {
                    result += it
                }
            }
            new Result(testName: 'Long Number Performance Service', executionTime: executionTime)
        }
    }

This method performTest must return a Result object.  Currently this object is pretty simple and the framework certainly has room for enahancement to support more complex types of results.  Here is the current Result object

    class Result implements Serializable {

        String testName
        String details
        Integer executionTime = 0
        Date createDate = new Date()
        Boolean isError = false

        public String toString() {
            "testName=" + testName +
            "&details=" + details +
            "&executionTime=" + executionTime +
            "&createDate=" + createDate +
            "&isError=" + isError
        }
    }

The last and more important step is adding the job to the Config.groovy block.  To wire up the job above to show up on the available jobs screen you need to add the following:

    perf {
        runners {
            largeNumberPerformanceRunner {
                description = 'Large Number Performance Test'
                maxWorkers = 20
                workerClass = com.perf.runners.math.LargeNumberPerformanceService
            }
        }
    }

The values are represented via the following:

    description - A name that will be used to display on the jobs screen
    maxWorkers - Max number of workers available to choose on job screen.  Will be between 1 and maxWorkers in drop down.
    workerClass - Points to the class of the worker to wire up to the job.

### Running the Application ###

_You will need to start a redis server and make sure the Config.groovy points to it before running the application._

Once one or more jobs are configured and redis is running you can `run-app` and navigate to [localhost][http://localhost:8080/gperf].
