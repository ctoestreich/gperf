<%@ page import="groovy.json.JsonSlurper; com.perf.Result" %>
<div class="job jobrunner">
    <%
        def obj = new JsonSlurper()
    %>
    <g:if test="${result?.toString()?.contains(Result.class.name.toString())}">
        <%
        Result rslt = ((Result) obj.parseText(result))
        %>
        <b>Name:</b> ${rslt?.testName}<br>
        <b>Execution Time:</b> ${rslt?.executionTime}<br>
        <b>Error:</b> ${rslt?.isError}<br>
        <b>Details:</b> ${rslt?.details?.encodeAsHTML()}
    </g:if>
    <g:else>
        <b>Data:</b> ${result}
    </g:else>
</div>