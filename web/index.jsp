<%-- 
    Document   : index
    Created on : 28-dic-2017, 11:03:43
    Author     : arnau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <h2>Chat Websockets</h2>
        <br/>
        
        <form class="form-inline" action="/action_page.php">
            <div class="form-group">
                <label for="email">Name:</label>
                <input type="email" class="form-control" id="email">
            </div>            
            <div class="checkbox">
                <label><input type="checkbox"> Remember me</label>
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </body>
</html>
