package todolist.servlets;

import todolist.logic.Validate;
import todolist.logic.ValidateService;
import todolist.models.Item;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddItemServlet extends HttpServlet {

    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("desc");
        validate.addItem(new Item(desc));
    }
}
