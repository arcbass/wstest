package chat;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Arnau
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // get request parameters for userID and password
        String user = request.getParameter("user");

        if (!user.equals("")) {
            
            //set the username as attribute
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30 * 60);
            
            // cookie with the username
            Cookie userName = new Cookie("user", user);
            response.addCookie(userName);
            
            //Get the encoded URL string
            String encodedURL = response.encodeRedirectURL("chat.jsp");
            response.sendRedirect(encodedURL);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Write a name</font>");
            rd.include(request, response);
        }
    }

}
