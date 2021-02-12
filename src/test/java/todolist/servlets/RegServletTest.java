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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class RegServletTest {
    @Test
    public void whenTestDoGetMethodForServlet() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher(any())).thenReturn(dispatcher);

        new RegServlet().doGet(req, resp);

        verify(req).getRequestDispatcher("reg.html");
        verify(dispatcher).forward(req, resp);
    }

    @Test
    public void whenDoPostRedirectViewAuthDo() throws ServletException, IOException {
        Validate validate = new ValidateStub();

        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("name")).thenReturn("name1");
        when(req.getParameter("email")).thenReturn("email1");
        when(req.getParameter("password")).thenReturn("password1");

        new RegServlet().doPost(req, resp);

        verify(resp).sendRedirect(String.format("%s/auth.do", req.getContextPath()));
    }
}