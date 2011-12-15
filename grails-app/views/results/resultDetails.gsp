<%@ page import="com.perf.Result; com.perf.PerformanceConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Job Details</title>
</head>

<body>
<h3>${params?.queue}</h3>
<g:render template="/templates/navigation"/>
Total Items: ${itemCount}<br>
<g:each in="${results}" var="result">
  <div class="job jobrunner">
    <g:if test="${result.toString().contains('&')}">
      <%
        def map = [:]
        result.toString().findAll(/([^&=]+)=([^&]+)/) { full, name, value ->  map[name] = value }
      %>
      <g:each in="${map}">
        <b>${it?.key?.encodeAsHTML()}</b>: ${it?.value?.encodeAsHTML()}<br>
      </g:each>
    </g:if>
    <g:else>
      ${result}
    </g:else>
  </div>
</g:each>
<g:paginate controller="results" action="resultDetails" total="${itemCount}" params="[queue:params?.queue]"></g:paginate>
</body>
</html>