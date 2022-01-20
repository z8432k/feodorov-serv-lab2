package net.unixcomp.suai.servlab2;

import javax.servlet.http.HttpServlet;

public class DataServlet extends HttpServlet  {
    protected static  Data data;

    static {
        data = new Data();
    }
}
