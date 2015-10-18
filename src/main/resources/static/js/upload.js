$(document).ready(function() {


    var client = null;
    var uploadaction = $('#id_form').attr('action');


    function uploadFile() {
        //Wieder unser File Objekt
        var file = document.getElementById("id_hfinput").files;
        //FormData Objekt erzeugen
        var formData = new FormData();
        //XMLHttpRequest Objekt erzeugen
        client = new XMLHttpRequest();


        if (!file)
            return;

        //Fügt dem formData Objekt unser File Objekt hinzu
        formData.append("files", file);

        client.onerror = function(e) {
            alert("onError");
        };

        client.onload = function(e) {
            //    document.getElementById("prozent").innerHTML = "100%";
            setprogress(0);
        };

        client.upload.onprogress = function(e) {
            var p = Math.round(100 / e.total * e.loaded);
            setprogress(p);
            //       document.getElementById("progress").value = p;            
            //      document.getElementById("prozent").innerHTML = p + "%";
        };

        client.onabort = function(e) {
            alert("Upload abgebrochen");
        };

        client.open("POST", uploadaction);
        client.send(formData);
    }

    function setprogress(in_value) {

        var prog = document.getElementById("id_uploadprogress");
        prog.attr({
            'aria-valuenow': in_value,
            'style': 'width:' + in_value + '%'
        });
    }






});
