<%-- 
    Document   : index
    Created on : 12-dic-2014, 19:35:56
    Author     : Alberto
--%>

<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aplicaci&oacute;n de blogs</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    </head>
    <body>
        <%
            List<Entity> posts = (List<Entity>) request.getAttribute("posts");
        %>
        <form method="POST" action="${pageContext.request.contextPath}/AddPost">
            <input type="hidden" name="type" value="new"/>
            <input type="submit" value="A&ntilde;adir entrada"/>
        </form>
        <table>
            <%
                if (posts.isEmpty()) {
            %>
            <tr><td>No existen elementos</td></tr>
            <% } else
                for (Entity p : posts) {
            %>
            <tr>
                <td><h2><a href="<%= getServletContext().getContextPath()%>/SeePost?id=<%= p.getKey().getId()%>">
                            <%= URLDecoder.decode(p.getProperty("titulo").toString(), "UTF-8") %></a></h2></td>
                <td><img src="<%= p.getProperty("imagen").toString() != null ? p.getProperty("imagen").toString().split(";")[0] : "#"%>"/></td>
                <td><%= p.getProperty("texto").toString().length() >= 150 ? URLDecoder.decode(p.getProperty("texto").toString(), "UTF-8")
                        .substring(0, 147) + "..." : URLDecoder.decode(p.getProperty("texto").toString(), "UTF-8") %></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>

