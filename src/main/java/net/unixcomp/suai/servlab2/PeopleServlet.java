package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name="people", urlPatterns = "/people")
public class PeopleServlet extends BaseServlet {
    private Data data;

    @Override
    public void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<a href='/lab2'>Home</a>");
        out.println("<hr />");

        out.println("<html><head><meta charset=\"utf-8\"></head><body>");
        out.println("<h1>People</h1>");

        out.println("<table border='1'>");

        out.println("<thead><tr><th>Person</th><th>actions</th></tr></thead><tbody>");

        for (String s : data.getPeople()) {
            out.println("<tr><td>"+ s +"</td><td>");


            out.println("<form path='people' method='POST'><input type='hidden' name='method' value='delete' /><input type='hidden' name='person' value='"+ s +"'/><button>RM</button></form>");

            out.println("</td></tr>");
        }

        out.println("</tbody></table>");

        out.println("<hr />");

        out.println("<form method='post' path='people'><br />");
        out.println("<label>Person <input name='person' type='text' /></label>");
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
        String person = req.getParameter("person");

        if (method != null && method.equals("delete")) {

            if (person != null) {
                data.rmPerson(person);
            }

        }
        else {

            if (person != null) {
                data.addPerson(person);
            }

        }

        res.setStatus(302);
        res.setHeader("Location", "people");
    }
}
