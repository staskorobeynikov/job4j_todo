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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class FilterItemsServletTest {

    @Test
    public void whenTestMethodDoPostDataIsJsonType() throws IOException {
        Validate validate = new ValidateStub();
        Item item = new Item();
        item.setId(1);
        item.setDesc("Learn Spring");
        item.setCreated(new Timestamp(1585054800000L));
        item.setDone(true);
        // validate.addItem(item);

        StringWriter stringWriter = new StringWriter();
        stringWriter.write("");
        PrintWriter writer = new PrintWriter(stringWriter);

        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(ValidateService.getInstance()).thenReturn(validate);
        when(resp.getWriter()).thenReturn(writer);

        new FilterItemsServlet().doPost(req, resp);

        String string = stringWriter.toString();
        writer.flush();

        assertThat(string, is("[]"));
        assertThat(validate.findAll().get(0).toString(), is("Item: id=1, desc=Learn Spring, done=true"));
    }
}