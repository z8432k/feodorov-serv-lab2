package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;


@WebServlet(name="rights", urlPatterns = "/rights")
public class RightsServlet extends DataServlet {
    @Override
    public void doGet(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<a href='/lab2'>Home</a>");
        out.println("<hr />");

        out.println("<html><head><meta charset=\"utf-8\"></head><body>");
        out.println("<h1>Rights</h1>");

        out.println("<table border='1'>");

        out.println("<thead><tr><th>Person</th><th>Room</th><th>Actions</th></tr></thead><tbody>");

        data.getRights().forEach((person, rooms) -> {
            for (String room : rooms ) {
                out.println("<tr><td>"+ person +"</td><td>"+ room +"</td><td>");

                out.println("<form action='rights' method='POST'><input type='hidden' name='method' value='delete' /><input type='hidden' name='person' value='"+ person +"'/><input type='hidden' name='room' value='"+ room +"'/><button>REVOKE</button></form>");


                out.println("</td></tr>");
            }
        });

        out.println("</tbody></table>");
        out.println("<hr />");

        out.println("<form method='post' path='rights'><br />");

        out.println("<label>Person <select name='person'>"+ options(data.getPeople()) +"</select></label><br />");
        out.println("<label>Room <select name='room'>"+ options(data.getRooms()) +"</select></label><br />");

        out.println("<button type='submit'>GRANT</button></form>");
        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        String room = req.getParameter("room");
        String person = req.getParameter("person");
        String method = req.getParameter("method");

        if (room == null || person == null) {
            res.setStatus(400);
            PrintWriter out = res.getWriter();
            out.println("Bad request.");

            return;
        }

        if (method != null && method.equals("delete")) {

            data.revoke(person, room);

            res.setStatus(302);
            res.setHeader("Location", "rights");
        }
        else {

            data.grant(person, room);

            res.setStatus(302);
            res.setHeader("Location", "rights");
        }
    }

    private String options(@NotNull Set<String> set) {
        String out = "";

        for (String s : set) {
            out += "<option>"+ s +"</option>";
        }

        return out;
    }
}
