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


@WebServlet(name="rights", urlPatterns = "/rights")
public class RightsServlet extends BaseServlet {
    private Data data;

    @Override
    public void init() throws ServletException {
        super.init();

        data = new Data();
    }

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

        data.getRights().forEach((client, rooms) -> {
            for (String room : rooms ) {
                out.println("<tr><td>"+ client +"</td><td>"+ room +"</td><td>");

                String field = ""++ ::

                out.println("<form action='rights' method='POST'><input type='hidden' name='method' value='delete' /><input type='hidden' name='rights' value=''/><button>REVOKE</button></form>");


                out.println("</td></tr>");
            }
        });

        out.println("</tbody></table>");
        out.println("<hr />");

        out.println("<form method='post' path='rights'><br />");

        out.println("<label>Person <select name='person'>"+ printOptions(data.getPeople(), null) +"</select></label><br />");
        out.println("<label>Room <select name='room'>"+ printOptions(data.getRooms(), null) +"</select></label><br />");

        out.println("<button type='submit'>GRANT</button></form>");
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
