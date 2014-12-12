<%@ page import="groovy.json.JsonSlurper; groovy.json.JsonBuilder; com.perf.Result; com.perf.PerformanceConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Job Details</title>
</head>

<body>
<h3>${params?.queue}</h3>
<g:render template="/templates/navigation"/>
Total Items: ${itemCount}<br>
<g:render template="/templates/valueNavigation"/>

<g:each in="${results}" var="result">
    <g:render template="/templates/results" model="[result: result]" />
</g:each>
<bs:paginate controller="results" action="resultDetails" total="${itemCount}"
            params="[queue:params?.queue]" ></bs:paginate>
</body>
</html>