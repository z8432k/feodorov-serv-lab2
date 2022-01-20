package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;


@WebServlet(name="rooms", urlPatterns = "/rooms")
public class RoomsServlet extends DataServlet {
    @Override
    public void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<a href='/lab2'>Home</a>");
        out.println("<hr />");

        out.println("<html><head><meta charset=\"utf-8\"></head><body>");
        out.println("<h1>Rooms</h1>");

        out.println("<table border='1'>");

        out.println("<thead><tr><th>Room</th><th>actions</th></tr></thead><tbody>");

        for (String s : data.getRooms()) {
            out.println("<tr><td>"+ s +"</td><td>");

            out.println("<form path='rooms' method='POST'><input type='hidden' name='method' value='delete' /><input type='hidden' name='room' value='"+ s +"'/><button>RM</button></form>");

            out.println("</td></tr>");
        }

        out.println("</tbody></table>");

        out.println("<hr />");

        out.println("<form method='post' path='/rooms'><br />");
        out.println("<label>Room <input name='room' type='text' /></label>");
        out.println("<button type='submit'>ADD</button></form>");

        out.println("</body></html>");
    }

    @Override
    public void init() throws ServletException {
        super.init();

        data = new Data();
    }

    @Override
    public void doPost(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        String method = req.getParameter("method");
        String room = req.getParameter("room");

        if (method != null && method.equals("delete")) {

            if (room != null) {
                data.rmRoom(room);
            }

        }
        else {

            if (room != null) {
                data.addRoom(room);
            }

        }

        res.setStatus(302);
        res.setHeader("Location", "rooms");
    }
}
