<%@ page import="com.uhg.perf.Result; com.perf.PerformanceConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Job Details</title>
</head>

<body>
<h3>All Keys</h3>
<g:render template="/templates/navigation"/>
Total Items: ${keyCount}<br>
<g:each in="${keys}" var="key">
  <div class="job jobrunner">
    <g:if test="${key.type == 'list'}">
      <g:link controller="results" action="resultDetails" params="[queue:key.key, type:key.type]"><b>${key?.key}</b></g:link> ${key?.value} items <small><i>list results take you to another page.</i></small>
    </g:if>
    <g:else>
      <b>${key.key}</b> - ${key?.value}
    </g:else>
  </div>
</g:each>
<g:paginate controller="results" action="resultKeys" total="${keyCount}"></g:paginate>

<script type="text/javascript">
  $(function() {
    $("#back").click(function() {
      document.location.href = "${createLink(controller: 'performance', action:'index')}";
    });
  });
</script>
</body>
</html>