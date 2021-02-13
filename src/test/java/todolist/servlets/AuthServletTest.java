package todolist.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import todolist.logic.Validate;
import todolist.logic.ValidateService;
import todolist.logic.ValidateStub;
import todolist.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class AuthServletTest {
    @Test
    public void whenTestDoGetMethodForServletReturnLoginPage() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher(any())).thenReturn(dispatcher);

        new AuthServlet().doGet(req, resp);

        verify(req).getRequestDispatcher("login.html");
        verify(dispatcher).forward(req, resp);
    }

    @Test
    public void whenDoPostRedirectViewIndexPage() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        User user = new User("username", "email", "password");
        user.setId(1);
        validate.addUser(user);

        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("password");

        new AuthServlet().doPost(req, resp);

        verify(resp).sendRedirect(String.format("%s/index.html", req.getContextPath()));
    }

    @Test
    public void whenDoPostNotFoundUserInStoreRedirectViewLoginPage() throws ServletException, IOException {
        Validate validate = new ValidateStub();

        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("email")).thenReturn("email1");
        when(req.getParameter("password")).thenReturn("password1");
        when(req.getRequestDispatcher(any())).thenReturn(dispatcher);

        new AuthServlet().doPost(req, resp);

        verify(req).getRequestDispatcher("login.html");
    }

    @Test
    public void whenDoPostNotValidPasswordRedirectViewLoginPage() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        User user = new User("username", "email", "password");
        user.setId(1);
        validate.addUser(user);

        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("password1");
        when(req.getRequestDispatcher(any())).thenReturn(dispatcher);

        new AuthServlet().doPost(req, resp);

        verify(req).getRequestDispatcher("login.html");
    }
}