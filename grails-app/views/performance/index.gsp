<%@ page import="com.perf.Result; com.perf.PerformanceConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Jobs</title>
</head>

<body>
<g:render template="/templates/navigation"/>
<%
  def jobs = []
  grailsApplication.config.perf.runners.each { runner ->
    def config = grailsApplication.config.perf.runners[runner.key]
    jobs << [name: runner.key, description: config?.description?:'Unknown', maxWorkers: config?.maxWorkers ?: 1]
  }
%>

<g:each in="${jobs}" var="job">
  <div class="job jobrunner" id= ${job.name}>
    <span class="cell"><b>${job.description}</b></span>
    <span class="cell">
      Workers: <g:select name="${job.name}Workers" from="${1..job.maxWorkers}"/>
    </span>
    <span class="cell">
      <span class="buttons">
        <input class="save" type="button" id="${job.name}Start" value="Start" title="Start"/>
        <input class="delete" type="button" id="${job.name}Stop" value="Stop" title="Stop"/>
      </span>
    </span>
    <span class="cell" id="${job.name}Status"></span><img id="${job.name}Ajax" src="${resource(dir: 'images', file: 'spinner.gif')}">
    <span class="clear"></span>

    <div class="results">
      <span class="cell"><g:link action="resultDetails" controller="results" params="[queue:job.name+'Results']"><b>Successful</b></g:link></span>
      <span class="cell">Tests: <span id="${job.name}Count"></span></span>
      <span class="cell">Average: <span id="${job.name}Average"></span>&nbsp;ms</span>
    </div>

    <div class="results">
      <span class="cell"><g:link action="resultDetails" controller="results" params="[queue:job.name+'Error']"><b>Errors</b></g:link></span>
      <span class="cell">Tests: <span id="${job.name}Errors"></span></span>
    </div>
  </div>
</g:each>
<script type="text/javascript">
  function startJob(name) {
    alert('starting ' + name);
  }

  function stopJob(name) {
    alert('stopping ' + name);
  }

  (function ($) {
    $.widget('uhg.jobrunner', {
               run:function () {
                 var self = this;
                 self._processData(self);
               },
               _create:function () {
                 var self = this;
                 self.running = false;
                 self.stop = false;
                 self.start = false;
                 self.errored = false;
                 self.id = self.element.attr("id");
                 self._initButtons(self);
                 $.when($.ajax({
                                 method:"POST",
                                 dataType:"html",
                                 url:"${createLink(controller:'performance',action:'status')}",
                                 data:"id=" + self.id
                               })).then(function (data) {
                                          self._processStatus(self, data);
                                          if(data == "Running") {
                                            jQuery("#" + self.id + "Stop").show();
                                            jQuery("#" + self.id + "Start").hide();
                                          }
                                        }, function () {
                                          self._failure(self);
                                        });

               },
               _initButtons:function (self) {
                 jQuery("#" + self.id + "Start").click(function () {
                   self._startWorkers(self);
                 });
                 jQuery("#" + self.id + "Stop").click(function () {
                   self._stopWorkers(self);
                 });
                 jQuery("#" + self.id + "Stop").hide();
               },
               _processData:function (self) {
                 self._showLoading(self);
                 if(self.stop || self.errored) {
                   self._stopWorkers(self);
                 }
                 if(self.start) {
                   self._startWorkers(self);
                 }
                 $.when(self._getResults(self),
                        self._getStatus(self))
                         .done(self._hideLoading(self));
               },
               _showLoading:function (self) {
                 $("#" + self.id + "Ajax").show();
               },
               _hideLoading:function (self) {
                 $("#" + self.id + "Ajax").hide();
               },
               _stopWorkers:function (self) {
                 self._stop(self);
                 $.when($.ajax({
                                 method:"POST",
                                 dataType:"html",
                                 url:"${createLink(controller:'performance',action:'stopWorkers')}",
                                 data:"id=" + self.id
                               })).then(function (data) {
                                          self._processStatus(self, data);
                                        }, function () {
                                          self._failure(self);
                                        });
               },
               _startWorkers:function (self) {
                 self._start(self);
                 self.start = false;
                 $.when($.ajax({
                                 method:"POST",
                                 dataType:"html",
                                 url:"${createLink(controller:'performance',action:'startWorkers')}",
                                 data:"id=" + self.id + "&workers=" + $("#" + self.id + "Workers :selected").val()
                               })).then(function (data) {
                                          self._processStatus(self, data);
                                        }, function () {
                                          self._failure(self);
                                        });
               },
               _processResults:function (self, data) {
                 if(data) {
                   $("#" + self.id + "Count").html(data.cnt);
                   $("#" + self.id + "Average").html(data.avg);
                   $("#" + self.id + "Errors").html(data.err);
                 }
               },
               _getResults:function (self) {
                 var dfd = new jQuery.Deferred();
                 $.when($.ajax({
                                 method:"GET",
                                 dataType:"json",
                                 url:"${createLink(controller:'results',action:'statistics')}",
                                 data:"id=" + self.id
                               })).then(function (data) {
                                          self._processResults(self, data);
                                        }, function () {
                                          self._failure(self);
                                        });
                 return dfd;
               },
               _processStatus:function (self, data) {
                 $("#" + self.id + "Status").html(data);
                 if(data == "Error") {
                   self._stopWorkers();
                 }
               },
               _getStatus:function (self) {
                 var dfd = new jQuery.Deferred();
                 $.when($.ajax({
                                 method:"POST",
                                 dataType:"html",
                                 url:"${createLink(controller:'performance',action:'status')}",
                                 data:"id=" + self.id
                               })).then(function (data) {
                                          self._processStatus(self, data);
                                          status = data;
                                        }, function () {
                                          self._failure(self);
                                        });
                 return dfd;
               },
               _failure:function (self) {
                 console.log("error encountered");
                 self._stop(self);
                 clearInterval(self.interval);
               },
               _start:function (self) {
                 console.log(self.id, "Starting");
                 self.start = false;
                 self.running = true;
                 $("#" + self.id + "Stop").show();
                 $("#" + self.id + "Start").hide();
               },
               _stop:function (self) {
                 self.start = false;
                 self.stop = false;
                 self.running = false;
                 console.log(self.id, "Stopping");
                 $("#" + self.id + "Stop").hide();
                 $("#" + self.id + "Start").show();
               },
               setInterval:function (interval) {
                 var self = this;
                 self.interval = interval;
               }
             }
    )
  })
          (jQuery);

  jQuery(function () {
    jQuery(".jobrunner").jobrunner();
    var interval = setInterval(function () {
      jQuery(".jobrunner").jobrunner("run");
    }, 4000);
    jQuery(".jobrunner").jobrunner("setInterval", interval);

  });

</script>
%{--<g:results id="test123" queue="results" />--}%
</body>
</html>