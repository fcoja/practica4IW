<%-- 
    Document   : seepost
    Created on : 28-ene-2015, 17:40:01
    Author     : Alberto
--%>

<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        Entity e = (Entity) request.getAttribute("post");
        List<Entity> comments = (List<Entity>) e.getProperty("comments");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= URLDecoder.decode(e.getProperty("titulo").toString(), "UTF-8") %></title>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
        <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <script>
            $(document).ready(function () {
                $("#map").load(function () {
                    $("#map").contents().find("#panel").hide();
                    $("#map").contents().find("#address").val(document.getElementById('city').value);
                    $("#map")[0].contentWindow.codeAddress();
                });
            });

            var request;

            function addComment() {
                var user = document.getElementById('userId').value;
                var comment = document.getElementById('commentId').value;
                document.getElementById('userId').value = '';
                document.getElementById('commentId').value = '';
                if (request)
                    request.abort();
                request = $.ajax({
                    url: "${pageContext.request.contextPath}/AddComment",
                    type: "POST",
                    data: {
                        username: user,
                        commentText: comment,
                        id: $("#postId").val()
                    },
                    success: function (data, textStatus, jqXHR) {
                        $("#comments").append(data);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert('Error: ' + errorThrown);
                    }
                });
            }
        </script>
    </head>
    <body>
        <div>
            <h1><%= URLDecoder.decode(e.getProperty("ciudad").toString(), "UTF-8") %>
                - <%= URLDecoder.decode(e.getProperty("titulo").toString(), "UTF-8") %></h1>
                <img src="<%= e.getProperty("imagen") != null ? e.getProperty("imagen").toString().split(";")[0] : "#"%>"/>
        </div>
            <input id="city" type="hidden" value="<%= URLDecoder.decode(e.getProperty("ciudad").toString(), "UTF-8") %>"/>
        <div>
            Created by: <%= URLDecoder.decode(e.getProperty("autor").toString(), "UTF-8") %>
            at <%= e.getProperty("fecha").toString() %>
        </div>
        <div>
            <p><%= URLDecoder.decode(e.getProperty("texto").toString(), "UTF-8") %></p>
        </div>
        <div>
            <%
                String[] images = e.getProperty("imagen").toString().split(";");
                boolean ignore = true;
                for (String i : images) {
                    if (ignore) {
                        ignore = false;
                        continue;
                    }
            %>
            <img src="<%= i%>"/>
            <%
                }
            %>
        </div>
        <iframe id="map" height="400px" width="100%" frameborder="0" scrolling="no"
                src="${pageContext.request.contextPath}/map.html"></iframe>
        <br/><h3>Comentarios:</h3><br/>
            <div id="comments">
                <%
                    for (Entity c : comments) {
                %>
                <div>
                    <b><%= URLDecoder.decode(c.getProperty("autor").toString(), "UTF-8") %>:</b><br/>
                    <small><%= c.getProperty("fecha").toString() %></small><br/>
                    <%= URLDecoder.decode(c.getProperty("texto").toString(), "UTF-8") %>
                </div>
                <%
                    }
                %>
            </div><br/>
            <div>Usuario:<br/><input id="userId" type="text" name="user"/></div>
            <div>Comentario:<br/><textarea id="commentId" cols="75" rows="3" name="comment"></textarea></div>
            <input type="submit" value="A&ntilde;adir" onclick="addComment()"/>
            <input id="postId" type="hidden" value="<%= e.getProperty("id").toString() %>"/>
    </body>
</html>
