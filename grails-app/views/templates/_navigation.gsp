<div class="row ">
    <div class="job jobrunner col-xs-12" id="commands">
        <span class="cell">
            <span class="buttons">
                <input class="edit btn btn-sm btn-info" type="button" id="home" value="home" title="home"/>
                <input class="edit btn btn-sm btn-info" type="button" id="flushdb" value="flushdb full"
                       title="flushdb"/>
                <input class="edit btn btn-sm btn-info" type="button" id="flushresults" value="flushdb results"
                       title="flushresults"/>
                %{--<input class="edit" type="button" id="populate" value="repopulate sample data" title="populate"/>--}%
                <input class="edit btn btn-sm btn-info" type="button" id="listkey" value="list keys" title="listkey"/>
            </span>
        </span>
        <span class="cell" id="buttonStatus" style="display:none">Status</span>
        <img id="spinner" style="display:none;" src="${resource(dir: 'images', file: 'spinner.gif')}">
    </div>
</div>

<div id="confirmDialog" title="" style="display:none">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span
            id="confirmText"></span></p>
</div>


<div class="modal fade" id="confirmDialogBootstrap">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="confirmButton">Confirm</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    $(function () {

        var fadeTime = 1500;

        $("#home").click(function () {
            document.location.href = "${createLink(controller: 'performance', action:'index')}";
        });

        $("#listkey").click(function () {
            document.location.href = "${createLink(controller: 'results', action:'resultKeys')}";
        });

        $("#flushresults").click(function () {

            confirmAction('Flush Results', 'Are you sure you want to flush results?', function (dialog, close) {
                dialog.enableButtons(false);
                dialog.getModalBody().hide().html("Flushing results...").fadeIn(fadeTime, function () {
                    $.ajax('${createLink(controller:'data', action:'flushresults')}').done(function (msg) {
                        dialog.getModalBody().html(msg).fadeOut(fadeTime, close);
                    });
                });
            });
        });

        $("#flushdb").click(function () {
            confirmAction('Flush All', 'Are you sure you want to flush all?', function (dialog, close) {
                dialog.enableButtons(false);
                dialog.getModalBody().hide().html("Flushing all...").fadeIn(fadeTime, function () {
                    $.ajax('${createLink(controller:'data', action:'flushdb')}').done(function (msg) {
                        dialog.getModalBody().html(msg).fadeOut(fadeTime, close);
                    });
                });
            });
        });
    });

    function confirmAction(title, html, success) {
        BootstrapDialog.show({
            title: title,
            message: html,
            buttons: [{
                label: 'Cancel',
                cssClass: 'btn-info',
                action: function(dialog) {
                    dialog.close();
                }
            }, {
                label: 'Confirm',
                autospin: true,
                cssClass: 'btn-primary',
                action: function(dialog) {
                    success(dialog, function() {
                        dialog.close();
                    });
                }
            }]
        });
    }
</script>