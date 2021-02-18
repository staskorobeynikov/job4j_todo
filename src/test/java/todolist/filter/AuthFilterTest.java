package todolist.filter;

import org.junit.Test;
import todolist.models.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthFilterTest {
    @Test
    public void whenRequestURIContainsAuthDo() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getRequestURI()).thenReturn("/auth.do");

        new AuthFilter().doFilter(req, response, chain);

        verify(chain).doFilter(req, response);
    }

    @Test
    public void whenRequestURIContainsRegDo() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getRequestURI()).thenReturn("/reg.do");

        new AuthFilter().doFilter(req, response, chain);

        verify(chain).doFilter(req, response);
    }

    @Test
    public void whenHttpSessionContainsAuthorizedUser() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getRequestURI()).thenReturn("/show");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());

        new AuthFilter().doFilter(req, response, chain);

        verify(chain).doFilter(req, response);
    }

    @Test
    public void whenHttpSessionNotContainsAuthorizedUser() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getRequestURI()).thenReturn("/show");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        new AuthFilter().doFilter(req, response, chain);

        verify(response).sendError(401, "Unauthorized user");
    }
}