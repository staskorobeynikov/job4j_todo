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
import todolist.models.Item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class AddItemServletTest {

    @Test
    public void whenTestMethodPostThanAddNewItemInStore() {
        Validate validate = new ValidateStub();

        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("desc")).thenReturn("Learn Hibernate");

        new AddItemServlet().doPost(req, resp);
        Item test = validate.findAll().get(0);

        assertThat(test.getId(), is(1));
        assertThat(test.getDesc(), is("Learn Hibernate"));
        assertThat(test.isDone(), is(false));
    }
}