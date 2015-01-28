<%-- 
    Document   : addpost
    Created on : 17-dic-2014, 14:44:14
    Author     : Alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>A&ntilde;adir blog</title>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
        <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <style>
            label > input{ /* HIDE RADIO */
                visibility: hidden; /* Makes input not-clickable */
                position: absolute; /* Remove input from document flow */
            }
            label > input + img{ /* IMAGE STYLES */
                cursor:pointer;
                border:2px solid transparent;
            }
            label > input:checked + img{ /* (RADIO CHECKED) IMAGE STYLES */
                border:2px solid #f00;
            }
        </style>
        <script>
            var request;

            $(document).ready(function () {
                $("form").submit(function (e) {
                    $("#warning").html("");
                    if ($("#userId").val() === '' || $("#titleId").val() === '' || $("#addressHidden")
                            .val() === '') {
                        e.preventDefault();
                        alert("Por favor, rellene todos los elementos.");
                        $("#warning").html("Por favor, rellene todos los elementos.");
                    }
                });
            });

            function setCity(address) {
                if (request)
                    request.abort();
                $("#imagePanel").html("Buscando...");
                document.getElementById('addressHidden').value = address;
                request = $.ajax({
                    url: "${pageContext.request.contextPath}/getImages",
                    type: 'POST',
                    data: address,
                    success: function (data, textStatus, jqXHR) {
                        $("#imagePanel").html(data);
                    },
                    error: function () {
                        $("#imagePanel").html('Error al enviar el nombre de la ciudad');
                    }
                });
            }

        </script>
    </head>
    <body>
        <form id="add" method="POST" action="${pageContext.request.contextPath}/AddPost">
            <input type="hidden" name="type" value="commit"/>
            <div>Usuario:<br/><input id="userId" type="text" name="user"/></div>
            <div>T&iacute;tulo:<br/><input id="titleId" type="text" name="title"/></div>
            <div>
                Descripci&oacute;n:<br/>
                <textarea id="descId" cols="150" rows="10" name="description"></textarea>
            </div>
            <input id="addressHidden" type="hidden" name="city" value=""/>
            <iframe id="iFrame" height="400px" width="100%" frameborder="0" scrolling="no"
                    src="${pageContext.request.contextPath}/map.html"></iframe>
            <div id="imagePanel">Busque una ciudad</div>
            <div id="warning"></div>
            <div>
                <input type="submit" value="Crear"/>
            </div>
        </form>
    </body>
</html>
