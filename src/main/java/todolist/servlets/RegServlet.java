package todolist.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import todolist.logic.ValidateService;
import todolist.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RegServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(RegServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean rsl = false;
        String username = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            ValidateService.getInstance().addUser(
                    new User(
                            username,
                            email,
                            password
                    )
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        if (rsl) {
            resp.sendRedirect(req.getContextPath() + "/auth.do");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/reg.do?error=true");
    }
}
