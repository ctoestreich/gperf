GRAILS PERFORMANCE FRAMEWORK
======

A simple framework with a jQuery widget on the front end to do performance testing of code using redis, jesque and executor.

## Detailed Description

Using a web admin console, configured jobs can be submitted via ajax to a queue in jesque with a type (workerClass) and number of threads to run.  A jesque worker will then pick up the queued jobs and spawn off a number of worker threads using executor.  These threads will continue to run until the user then stops the job via the admin console.  Stopping the job is essentially flipping an active flag for the job to false in the datastore ([redis][redis]).  Ther results will be updated near-time on the screen using a custom ajax enabled jQuery widget that polls and aggregates the data from redis.  These operations are extremely fast due to the speed of [redis][redis].

The admin console provides the following functionality:

 * View paged resultset of successful & error results per job
 * List all keys in Redis
 * Clear all results
 * Clear all data (Flush Redis Database)

See the sequence diagram below for a UML view of how the system operates.

_There may be a slight delay in the updating of statistics both when starting and stopping the tests as the jQuery widget only updates stats every 5s._

## Create A Job

To create a new performance job simply create a new service in the services/com/perf/runners directory.  I have 3 sample jobs created in the base project.  It is important that you extend the `AbstractPerformanceService` and implement the `performTest` method.

The abstract service contains a benchmark method that accepts a closure which you should use to encapsulate the code you wish to time.  The benchmark method will return the time in ms the closure took to execute.

``` groovy
    class LargeNumberPerformanceService extends AbstractPerformanceService {
        Result performTest() {
            Long result = 1
            def executionTime = benchmark {
                100000.times {
                    result += it
                }
            }
            new SimpleResult(testName: 'Long Number Performance Service', executionTime: executionTime)
        }
    }
```

This method performTest must return a Result interface as this will be used to log into the results data cache in redis.  I have two types of results, SimpleResult and ComparisonResult, that both inherit from Result and are demonstrated in the project.  Users are free to add their own types of result objects if they need more fields.

``` groovy
     interface Result {
         String testName
         String details
         Integer executionTime
         Date createDate
         Boolean isError
     }

```

``` groovy
    @ToString(includeNames = true, includeFields = true, excludes = "class, id")
    class SimpleResult implements Serializable, Result {

        String testName
        String details
        Integer executionTime = 0
        Date createDate = new Date()
        Boolean isError = false
    }
```

``` groovy
    @ToString(includeNames = true, includeFields = true, excludes = "class, id")
    class ComparisonResult extends SimpleResult {
        String result1
        String result2
    }
```

Result Interface Fields:

    testName - This can be an abitrary value that you would like, simply used when displaying detailed results view.
    details - If you would like to put any specific output from the test like facts, figures, etc. you can do so in the details field.
    executionTime - This should be the value of the result from the benchmark method.  You could roll your own timing schema and put that value here.
    createDate - The date, defaulted to now, simply used when displaying detailed results view.
    isError - This will cause the result to be logged into the error queue.  You should set this if an error condition occurs perhaps in a try catch or when unexpected results are reached.

Here is an example of a test the may set the error flag if the results are empty or an exception occurs:

``` groovy
    Result performTest() {
        String quote = ''
        Boolean isError = false

        def duration = benchmark {
            try {
                quote = stockQuoteClient.getQuote(randomStock)
            } catch (Exception e) {
                isError = true
            }
        }

        new SimpleResult(details: quote, isError: (isError || !quote), executionTime: duration, testName: 'Stock Quote Performance Service')
    }
```

The last and most important step for activating a performance job is adding the job config to the Config.groovy perf runners block.

``` groovy
    perf {
        runners {
            jobName {
                description
                maxWorkers
                workerClass
            }
        }
    }
```

The values are represented via the following:

    jobName - A unique name for the job node (no spaces)
    description - A name that will be used to display on the jobs screen
    maxWorkers - Max number of workers available to choose on job screen.  Will be between 1 and maxWorkers in drop down.
    workerClass - Points to the class of the worker to wire up to the job.

_The jobName MUST be unique or you will get overlapping and/or inaccurate results._

To wire up the LargeNumberPerformanceService job above to show up in the admin console as an available job you need to add the following:

``` groovy
    perf {
        runners {
            largeNumberPerformanceRunner {
                description = 'Large Number Performance Test'
                maxWorkers = 20
                workerClass = com.perf.runners.math.LargeNumberPerformanceService
            }
        }
    }
```

Note: I have not tested a large number of jobs, but having several available to run would be okay.  If you tried to run several jobs with large thread pools, you will probably experience inaccurate results as your machine struggles to keep up.  Leaving the job service classes in place but simply commenting out the jobName block in the config will cause the admin console to not list the job for running and should take up no overhead while essentially disabling the job.

## Running the Application ##

_You will need to start a redis server and make sure the Config.groovy points to it before running the application._

Once one or more jobs are configured and redis is running you can `run-app` and navigate to [localhost][localhost].

[redis]: http://www.grails.org/plugin/redis (Redis Plugin)
[jesque]: http://www.grails.org/plugin/jesque (Jesque Plugin)
[executor]: http://www.grails.org/plugin/executor (Executor Plugin)
[github]: https://github.com/ctoestreich/gperf (GPERF Framework)
[localhost]: http://localhost:8080/gperf (Local Web App)