package todolist.servlets;

import com.google.gson.Gson;
import todolist.logic.Validate;
import todolist.logic.ValidateService;
import todolist.models.Item;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FilterItemsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> list = ValidateService.getInstance().showFilterItems();
        String json = new Gson().toJson(list);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
