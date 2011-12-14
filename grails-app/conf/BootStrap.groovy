import grails.plugin.redis.RedisService
import com.perf.DataService

class BootStrap {

    DataService dataService

    def init = { servletContext ->
        dataService.populateSampleConsumerData()

    }
    def destroy = {
    }


}
