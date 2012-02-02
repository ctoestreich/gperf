<div class="job jobrunner" id="commands">
  <span class="cell">
    <span class="buttons">
      <input class="edit" type="button" id="home" value="home" title="home"/>
      <input class="edit" type="button" id="flushdb" value="flushdb full" title="flushdb"/>
      <input class="edit" type="button" id="flushresults" value="flushdb results" title="flushresults"/>
      %{--<input class="edit" type="button" id="populate" value="repopulate sample data" title="populate"/>--}%
      <input class="edit" type="button" id="listkey" value="list keys" title="listkey"/>
    </span>
  </span>
  <span class="cell" id="buttonStatus" style="display:none">Status</span>
  <img id="spinner" style="display:none;" src="${resource(dir: 'images', file: 'spinner.gif')}">
</div>

<div id="confirmDialog" title="" style="display:none">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span id="confirmText"></span></p>
</div>

<script type="text/javascript">
  $(function() {

    var fadeTime = 1500;

    $("#home").click(function() {
      document.location.href = "${createLink(controller: 'performance', action:'index')}";
    });

    $("#listkey").click(function() {
      document.location.href = "${createLink(controller: 'results', action:'resultKeys')}";
    });


    $("#flushresults").click(function() {
      confirmAction('Flush Results', 'Are you sure you want to flush results?', function(close) {
        showSpinner();
        $("#confirmText").hide().html("Flushing results...").fadeIn(fadeTime, function() {
          $.ajax('${createLink(controller:'data', action:'flushresults')}').done(function(msg) {
            hideSpinner();
            $("#confirmText").html(msg).fadeOut(fadeTime, close);
          });
        });
      });
    });

    $("#flushdb").click(function() {
      confirmAction('Flush All', 'Are you sure you want to flush all?', function(close) {
        showSpinner();
        $("#confirmText").hide().html("Flushing all...").fadeIn(fadeTime, function() {
          $.ajax('${createLink(controller:'data', action:'flushdb')}').done(function(msg) {
            hideSpinner();
            $("#confirmText").html(msg).fadeOut(fadeTime, close);
          });
        });
      });
    });

    %{--$("#populate").click(function() {--}%
      %{--confirmAction('Flush All', 'Are you sure you want to flush all?', function(close) {--}%
        %{--showSpinner();--}%
        %{--$("#confirmText").hide().html("Populating...").fadeIn(fadeTime, function() {--}%
          %{--$.ajax('${createLink(controller:'data', action:'populateSampleData')}').done(function(msg) {--}%
            %{--hideSpinner();--}%
            %{--$("#confirmText").html(msg).fadeOut(fadeTime, close);--}%
          %{--});--}%
        %{--});--}%
      %{--});--}%
    %{--});--}%
  });

  function confirmAction(btnText, html, success) {
    $("#confirmText").html(html).show();
    $("#confirmDialog").attr('title', btnText);
    $("#confirmDialog").dialog({
                                 resizable: false,
                                 height:160,
                                 modal: true,
                                 buttons: {
                                   "Confirm": function() {
                                     var self = this;
                                     success(function() {
                                       $(self).dialog("close");
                                     });
                                   },
                                   Cancel: function() {
                                     $(this).dialog("close");
                                   }
                                 }
                               });
  }

  function showSpinner() {
    $("#spinner").show();
  }

  function hideSpinner() {
    $("#spinner").hide();
  }
</script>