package todolist.servlets;

import com.google.gson.Gson;
import todolist.logic.ValidateService;
import todolist.models.Item;
import todolist.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("desc");
        String[] ids = req.getParameterValues("cIds[]");
        Item item = new Item(desc);
        User user = (User) req.getSession().getAttribute("user");
        item.setUser(user);
        Item addItem = ValidateService.getInstance().addItem(item, ids);
        String json = new Gson().toJson(addItem);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
