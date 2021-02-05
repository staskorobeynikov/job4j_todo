package todolist.servlets;

import com.google.gson.Gson;
import todolist.memory.DBStore;
import todolist.models.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = DBStore.getInstance().findCategories();
        String json = new Gson().toJson(categories);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
