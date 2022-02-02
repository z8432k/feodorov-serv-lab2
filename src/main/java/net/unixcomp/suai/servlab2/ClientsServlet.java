package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name="clients", urlPatterns = "/clients")
public class ClientsServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<a href='/lab2'>Home</a>");
        out.println("<hr />");

        out.println("<html><head><meta charset='utf-8'></head><body>");
        out.println("<h1>Clients</h1>");

        printSetTable(out, clients, "name", "clients", "name");

        out.println("<hr />");
        addForm(out, "name", "Client Name", "/clients");

        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        processForm(req, res, clients, "name", "clients");
    }
}
/*EOF*/
