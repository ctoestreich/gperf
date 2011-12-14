<div class="job jobrunner" id="commands">
  <span class="cell">
    <span class="buttons">
      <input class="edit" type="button" id="home" value="home" title="home"/>
      <input class="edit" type="button" id="flushdb" value="flushdb full" title="flushdb"/>
      <input class="edit" type="button" id="flushresults" value="flushdb results" title="flushresults"/>
      <input class="edit" type="button" id="populate" value="repopulate sample data" title="populate"/>
      <input class="edit" type="button" id="listkey" value="list keys" title="listkey"/>
      %{--<input class="delete" type="button" id="${job}Stop" value="Stop" title="Stop"/>--}%
    </span>
  </span>
  <span class="cell" id="buttonStatus" style="display:none">Status</span>
  <img id="spinner" style="display:none;" src="${resource(dir: 'images', file: 'spinner.gif')}">
</div>
<script type="text/javascript">
  $(function() {
    $("#home").click(function() {
      document.location.href = "${createLink(controller: 'performance', action:'index')}";
    });

    $("#listkey").click(function() {
      document.location.href = "${createLink(controller: 'results', action:'resultKeys')}";
    });


    $("#flushresults").click(function() {
      if(confirm('are you sure you want to flush results?')) {
        showSpinner();
        $("#buttonStatus").html("flushing results...").fadeIn("slow");
        $.ajax('${createLink(controller:'data', action:'flushresults')}').done(function(msg) {
          hideSpinner();
          $("#buttonStatus").html(msg).fadeOut(3000);
        });
      }
    });

    $("#flushdb").click(function() {
      if(confirm('are you sure you want to flush all?')) {
        showSpinner();
        $("#buttonStatus").html("flushing all...").fadeIn("slow");
        $.ajax('${createLink(controller:'data', action:'flushdb')}').done(function(msg) {
          hideSpinner();
          $("#buttonStatus").html(msg).fadeOut(3000);
        });
      }
    });

    $("#populate").click(function() {
      showSpinner();
      $("#buttonStatus").html("populating...").fadeIn("slow");
      $.ajax('${createLink(controller:'data', action:'populateSampleConsumerData')}').done(function(msg) {
        hideSpinner();
        $("#buttonStatus").html(msg).fadeOut(3000);
      })
    });
  });

  function showSpinner() {
    $("#spinner").show();
  }

  function hideSpinner() {
    $("#spinner").hide();
  }
</script>