<%@ page import="org.codehaus.groovy.grails.web.json.JSONObject; org.codehaus.groovy.grails.web.json.JSONArray; groovy.json.JsonSlurper; com.perf.Result" %>
<div class="job jobrunner">
    <g:if test="${result ==~ /.*com\.perf\.result\.[a-zA-Z]*Result.*/}">
        <%
        def rslt = new JSONObject(result.toString())
        %>
        <g:each in="${rslt.sort()}" var="prop">
          <span class="${!prop?.value || prop?.value?.toString()?.trim() == "null" ? "nullValue" : "value"}"><b>${prop.key.toString().capitalize()}:</b> ${prop?.value}<br></span>
        </g:each>     
    </g:if>
    <g:else>
        <b>Data:</b> ${result}
    </g:else>
</div>