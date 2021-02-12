package todolist.servlets;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import todolist.logic.ValidateService;
import todolist.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class CurrentUserServletTest {
    @Test
    public void whenSessionContainsCurrentUser() throws IOException, ServletException {
        User user = new User("Anonymous", "root@local", "root");
        user.setId(1);

        StringWriter writer = new StringWriter();
        writer.write("");
        PrintWriter pWriter = new PrintWriter(writer);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);
        when(resp.getWriter()).thenReturn(pWriter);

        new CurrentUserServlet().doPost(req, resp);

        String string = writer.toString();
        writer.flush();

        Gson gson = new Gson();
        User json = gson.fromJson(string, User.class);

        assertThat(json, is(user));
    }

    @Test
    public void whenSessionContainsCurrentUser1() throws IOException, ServletException {
        User user = new User("Anonymous", "", "");
        user.setId(0);

        StringWriter writer = new StringWriter();
        writer.write("");
        PrintWriter pWriter = new PrintWriter(writer);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(null);
        when(resp.getWriter()).thenReturn(pWriter);

        new CurrentUserServlet().doPost(req, resp);

        String string = writer.toString();
        writer.flush();

        Gson gson = new Gson();
        User json = gson.fromJson(string, User.class);

        assertThat(json, is(user));
    }
}