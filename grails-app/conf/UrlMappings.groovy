import com.uhg.perf.PerformanceController

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'performance', action:'index')
		"500"(view:'/error')
	}
}
