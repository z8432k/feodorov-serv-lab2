package net.unixcomp.suai.servlab2;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseServlet extends HttpServlet  {
    protected static Set<String> rooms = new HashSet<>();
    protected static Set<String> clients = new HashSet<>();
    protected static HashMap<String, String> rent = new HashMap<>();

    static {
        for (int i = 1; i < 5; i++) {
            rooms.add("Room " + i);
            clients.add("Client " + i);
        }

        rent.put("Client 2", "Room 1");
    }

    protected void printSetTable(PrintWriter out, Set<String> set, String th, String path, String field) {
        out.println("<table border='1'>");

        out.println("<thead><tr><th>"+ th +"</th><th>actions</th></tr></thead><tbody>");

        for (String s : set) {
            out.println("<tr><td>"+ s +"</td><td>"+ delForm(path, field, s) +"</td></tr>");
        }

        out.println("</tbody></table>");
    }

    protected void addForm(PrintWriter out, String name, String label, String path) {
        out.println("<form method='post' path='"+ path +"'><br />");
        out.println("<label>"+ label +" <input name='"+ name +"' type='text' /></label>");
        out.println("<button type='submit'>GO</button></form>");
    }

    protected String delForm(String path, String field, String value) {
        String out = "";

        out += "<form action='"+ path +"' method='POST'><input type='hidden' name='method' value='delete' /><input type='hidden' name='"+ field +"' value='"+ value +"'/><button>del</button></form>";

        return out;
    }

    protected void processForm(HttpServletRequest req, HttpServletResponse res, Set<String> set, String field, String redir) {
        String method = req.getParameter("method");

        if (method != null && method.equals("delete")) {
            processDeleteForm(req, res, set, field, redir);
        }
        else {
            processAddForm(req, res, set, field, redir);
        }
    }

    protected void processAddForm(HttpServletRequest req, HttpServletResponse res, Set<String> set, String field, String redir) {
        String data = req.getParameter(field);

        if (data != null) {
            synchronized (BaseServlet.class) {
                set.add(data);
            }
        }

        res.setStatus(302);
        res.setHeader("Location", redir);
    }

    protected void processDeleteForm(HttpServletRequest req, HttpServletResponse res, Set<String> set, String field, String redir) {
        String data = req.getParameter(field);

        if (data != null) {
            synchronized (BaseServlet.class) {
                set.remove(data);
            }
        }

        res.setStatus(302);
        res.setHeader("Location", redir);
    }
}


