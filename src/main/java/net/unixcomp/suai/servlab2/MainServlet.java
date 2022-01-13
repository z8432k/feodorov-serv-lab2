package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(name="test", urlPatterns = "/hello-servlet")
public class MainServlet extends HttpServlet {
    private String message;

    @Override
    public void init() {
        message = "Hello from servlet!";
    }

    @Override
    public void doGet(HttpServletRequest req, @NotNull HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        // Hello
        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        // noop
    }
}
