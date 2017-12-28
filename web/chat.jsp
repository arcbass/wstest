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

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <style>
            /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
            .row.content {height: 1500px}

            /* Set gray background color and 100% height */
            .sidenav {
                background-color: #f1f1f1;
                height: 100%;
            }

            /* Set black background color, white text and some padding */
            footer {
                background-color: #555;
                color: white;
                padding: 15px;
            }

            /* On small screens, set height to 'auto' for sidenav and grid */
            @media screen and (max-width: 767px) {
                .sidenav {
                    height: auto;
                    padding: 15px;
                }
                .row.content {height: auto;} 
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row content">
                <div class="col-sm-3 sidenav">
                    <h4>Chat</h4>
                    <ul class="nav nav-pills  nav-stacked">
                        <li class="active"><a data-toggle="pill" href="#home">Home</a></li>
                        <li><a data-toggle="pill" href="#menu1">Menu 1</a></li>
                        <li><a data-toggle="pill" href="#menu2">Menu 2</a></li>                        
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
                            <button type="button" class="btn">Send</button>
                        </div>



                    </div>
                </div>
            </div>

    </body>
</html>
