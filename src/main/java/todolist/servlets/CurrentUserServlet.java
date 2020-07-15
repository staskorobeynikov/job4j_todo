package todolist.servlets;

import com.google.gson.Gson;
import todolist.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CurrentUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        String json = new Gson().toJson(new User("Anonymous", "", ""));
        if (user != null) {
            json = new Gson().toJson(user);
        }
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
