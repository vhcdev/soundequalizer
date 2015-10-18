$(document).ready(function() {
    var viewitems = $("#id_ct_files");
    var fileinput = $("#id_hfinput");
    var uploadform = $('#id_form');
    var bt_selectfile = $("#id_bt_selectfiles");
    var bt_cancelupload = $('#id_bt_cancelupload');

    var inactiveCSS = 'text-muted';
    var activeCSS = 'text-primary';

    function activeElment(id, callbackClick) {

        $(id).removeAttr('disabled');
        $(id).click(callbackClick);
    }

    function deactiveElement(id) {
        $(id).removeClass(activeCSS);
        $(id).attr('disabled', 'disabled');
        $(id).click(function(event) {
            /*do no thing*/
        });
    }

    function showElement(id) {
        $(id).removeClass('hidden');
    }

    function hideElement(id) {
        $(id).addClass('hidden');
    }

    bt_cancelupload.click(function(event) {
        hideElement('#id_bt_cancelupload');
        showElement('#id_bt_selectfiles');
        deactiveElement('#id_bt_normalizer');
        fileinput.val('');
        viewitems.empty();

    });

    var normalizerClick = function() {
        //uploadform.submit();
        uploadFile();
    }

    /**
    dont upload more than 5 files 
    **/
    function handleFileSelect(evt) {
        var files = evt.target.files; // FileList object

        hideElement('#id_errormessage');
        if (files.length > 0) {

            viewitems.empty();
            activeElment('#id_bt_normalizer', normalizerClick);
            showElement('#id_bt_cancelupload');
            hideElement('#id_bt_selectfiles');

            var totalmb = 0.0;
            for (var i = 0, f; f = files[i]; i++) {
                var mb = f.size * 0.001 * 0.0009;
                totalmb = totalmb + mb;
                viewitems.append('<li class="list-group-item"> <span class="glyphicon glyphicon-upload blue" aria-hidden="true"></span>' + escape(f.name) + '<span class ="badge">' + mb.toFixed(2) + 'mb</span></li>');
            }
            viewitems.prepend('<li class="list-group-item"> <b>' + escape(files.length) + ' files </b>' + '<span class ="badge">' + totalmb.toFixed(2) + 'mb</span></li>');

        }
        /*else if (files.length > 5) {
            showElement('#id_errormessage');
            deactiveElement('#id_bt_normalizer');
        }    */
        else {
            deactiveElement('#id_bt_normalizer');
        }
    }

    document.getElementById('id_hfinput').addEventListener('change', handleFileSelect, false);

    /*open html5 file seletor */
    $("#id_bt_selectfiles").click(function(event) {

        $("#id_hfinput").trigger('click');
    });

    //upload progress code


    var client = null;
    var uploadaction = $('#id_form').attr('action');


    var progressbar = $('#id_progress_circle').circleProgress({
        size: 50,
        fill: {
            gradient: ["#31b0d5", "#31b0d5"]
        }
    });

    function setprogress(in_value) {
        var circleval = in_value / 100;
        progressbar.circleProgress({
            value: circleval,
            animationStartValue: progressbar.data('circle-progress').lastFrameValue
        });
        oldvalue = circleval;
    }

    function uploadFile() {
        //Wieder unser File Objekt
        var file = document.getElementById("id_hfinput").files;
        //FormData Objekt erzeugen
        var formData = new FormData();
        //XMLHttpRequest Objekt erzeugen
        client = new XMLHttpRequest();


        if (!file)
            return;

        deactiveElement('#id_bt_cancelupload');
        deactiveElement('#id_bt_selectfiles');
        deactiveElement('#id_bt_normalizer');

        $('#id_uploadprogress').removeClass('hidden');
        setprogress(0);

        for (var x = 0; x < file.length; x++) {
            formData.append("files[]", file[x]);
        }

        client.onerror = function(e) {
            alert("onError");
        };

        client.onload = function(e) {

            var msg = JSON.parse(e.currentTarget.responseText);
            if (msg.type == 'ERROR') {
                $('#id-errormsg').text(msg.text);
                $('#id-errormodal').modal();

                console.log(msg);

            } else {

                setprogress(100);
                console.log(msg);
                window.location = "../equalizing";
            }


        };

        client.upload.onprogress = function(e) {
            var p = Math.round(100 / e.total * e.loaded);
            setprogress(p);

        };

        client.onabort = function(e) {
            alert("Upload abgebrochen");
        };

        client.open("POST", uploadaction);
        client.send(formData);
    }

    var oldvalue = 0;


});
