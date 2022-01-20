package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;


@WebServlet(name="rooms", urlPatterns = "/rooms")
public class RoomsServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<a href='/lab2'>Home</a>");
        out.println("<hr />");

        out.println("<html><head><meta charset=\"utf-8\"></head><body>");
        out.println("<h1>Rooms</h1>");

        printSetTable(out, rooms, "name", "rooms", "name");

        out.println("<hr />");
        addForm(out, "name", "Room Name", "/rooms");

        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        processForm(req, res, rooms, "name", "rooms");
    }
}
