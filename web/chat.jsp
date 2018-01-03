<%-- 
    Document   : chat
    Created on : 28-dic-2017, 12:23:14
    Author     : arnau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link rel="stylesheet" href="css/chat.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        
        <script src="js/users_connected.js"></script>
        <script src="js/ws_conn.js"></script>    


        <style>

        </style>
    </head>
    <body>


        <div class="container-fluid">

            <div class="row content">
                <nav class="navbar navbar-inverse">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <a class="navbar-brand" href="#">Chat</a>
                        </div>

                        <ul class="nav navbar-nav navbar-right">                        
                            <li><a href="#"><span class="glyphicon glyphicon-user"></span> <%= session.getAttribute("user")%></a></li>

                        </ul>
                    </div>
                </nav>
                <div class="col-sm-3 sidenav">
                    <h4>Chat</h4>
                    <ul class="nav nav-pills  nav-stacked">
                        <!--<li class="active"><a data-toggle="pill" href="#home">Home</a></li>
                        <li><a data-toggle="pill" href="#menu1">Menu 1</a></li>
                        <li><a data-toggle="pill" href="#menu2">Menu 2</a></li>-->                        
                    </ul>      
                </div>

                <div class="col-sm-9">
                    <h4><small>RECENT POSTS</small></h4> 

                    <div class="tab-content" style="overflow-y: scroll; height:400px;">
                        <div id="home" class="tab-pane fade in active">
                            <h3>HOME</h3>
                            <p>Some content.</p>
                        </div>
                        <div id="menu1" class="tab-pane fade">
                            <h3>Menu 1</h3>
                            <p>Some content in menu 1.</p>
                        </div>
                        <div id="menu2" class="tab-pane fade">
                            <h3>Menu 2</h3>
                            <p>Some content in menu 2.</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">Comment:</label>
                        <textarea class="form-control" rows="5" id="comment"></textarea>
                        <button type="button" class="btn" onclick="sendMessage()">Send</button>
                        <br/><br/><br/>
                        <button type="button" class="btn" onclick="getImage()">Send Image</button>
                        <br/><br/><br/>

                        <form action="LogoutServlet" method="POST">
                            <input type="submit" value="LOGOUT" />
                        </form>

                        <canvas id="myCanvas" width="300" height="300"></canvas>
                    </div>



                </div>
            </div>
        </div>
        <script>
            sendUserLogin('<%=session.getAttribute("user")%>', '<%=session.getId()%>');
        </script>
        <script src="js/binarydata.js"></script>
        
    </body>
</html>
