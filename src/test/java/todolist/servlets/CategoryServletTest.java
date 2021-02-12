package todolist.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import todolist.memory.DBStore;
import todolist.memory.MemoryStore;
import todolist.memory.Store;
import todolist.models.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(DBStore.class)
public class CategoryServletTest {
    @Test
    public void whenJSONContainsContent() throws IOException, ServletException {
        Store store = new MemoryStore();
        Category category = Category.of("Learning");
        category.setId(1);
        store.addCategory(category);

        StringWriter stringWriter = new StringWriter();
        stringWriter.write("");
        PrintWriter writer = new PrintWriter(stringWriter);

        PowerMockito.mockStatic(DBStore.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(DBStore.getInstance()).thenReturn(store);
        when(resp.getWriter()).thenReturn(writer);

        new CategoryServlet().doGet(req, resp);

        String string = stringWriter.toString();
        writer.flush();

        assertThat(string, is("[{\"id\":1,\"name\":\"Learning\"}]"));
    }
}