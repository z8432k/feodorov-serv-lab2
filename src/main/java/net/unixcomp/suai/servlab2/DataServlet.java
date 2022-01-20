package net.unixcomp.suai.servlab2;

import javax.servlet.http.HttpServlet;

public class DataServlet extends HttpServlet  {
    protected Data data;

    public DataServlet() {
        data = new Data();
    }
}
