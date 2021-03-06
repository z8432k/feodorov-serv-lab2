package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;


@WebServlet(name="rent", urlPatterns = "/rent")
public class RentServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<a href='/lab2'>Home</a>");
        out.println("<hr />");

        out.println("<html><head><meta charset=\"utf-8\"></head><body>");
        out.println("<h1>Rent</h1>");

        out.println("<table border='1'>");

        out.println("<thead><tr><th>Person</th><th>Room</th><th>Actions</th></tr></thead><tbody>");

        rent.forEach((client, room) -> {
            out.println("<tr><td>"+ client +"</td><td>"+ room +"</td><td>");
            out.println("<form action='rent' method='POST'><input type='hidden' name='method' value='delete' /><input type='hidden' name='client' value='"+ client +"'/><button>Unrent</button></form>");
            out.println("</td></tr>");
        });

        out.println("</tbody></table>");
        out.println("<hr />");

        out.println("<form method='post' path='rent'><br />");

        out.println("<label>Client <select name='client'>"+ printOptions(clients, null) +"</select></label>");
        out.println("<label>Room <select name='room'>"+ printOptions(rooms, rent) +"</select></label>");

        out.println("<button type='submit'>rent</button></form>");
        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        String room = req.getParameter("room");
        String client = req.getParameter("client");
        String method = req.getParameter("method");

        if (method != null && method.equals("delete")) {

            if (client != null) {
                synchronized (BaseServlet.class) {
                    rent.remove(client);
                }
            }
            else {
                res.setStatus(400);
                PrintWriter out = res.getWriter();
                out.println("Bad request.");
            }

            res.setStatus(302);
            res.setHeader("Location", "rent");
        }
        else {
            if (room == null || client == null) {
                res.setStatus(400);
                PrintWriter out = res.getWriter();
                out.println("Bad request.");
            }
            else {
                if (!rent.containsValue(room)) {
                    synchronized (BaseServlet.class) {
                        rent.put(client, room);
                    }
                }

                res.setStatus(302);
                res.setHeader("Location", "rent");
            }        }
    }

    private String printOptions(@NotNull Set<String> set, HashMap<String, String> rent) {
        String out = "";

        for (String s : set) {
            if (rent != null && rent.containsValue(s)) {
                continue;
            }

            out += "<option>"+ s +"</option>";
        }

        return out;
    }
}
