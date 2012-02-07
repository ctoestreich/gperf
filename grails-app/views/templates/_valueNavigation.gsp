<div class="job jobrunner" id="commands">
  <span class="cell">
    <span class="buttons">
      <input class="delete" type="button" id="values" value="Show/Hide Null Properties"/>
    </span>
  </span>
</div>

<script type="text/javascript">
  $(function() {
    $("#values").click(function() {
      $(".nullValue").toggle();
    });
  });
</script>