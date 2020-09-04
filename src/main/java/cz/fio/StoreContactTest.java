package cz.fio;


import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StoreContactTest extends Mockito {

    @Test
    public void doGet() throws IOException, SAXException {

        ServletRunner sr = new ServletRunner();
        sr.registerServlet( "contact", StoreContact.class.getName() );


        ServletUnitClient sc = sr.newClient();
        WebRequest request1 = new GetMethodWebRequest( "http://localhost:8080/contact" );
        request1.setParameter( "firstName", "jmeno" );
        request1.setParameter( "lastName", "pří\u00ADjmení\u00AD" );
        request1.setParameter( "email", "email@email.cz" );
        WebResponse response1 = sc.getResponse( request1 );
        assertNotNull( "No response received", response1 );
        assertEquals( "content type", "text/plain", response1.getContentType() );


        WebRequest request2 = new GetMethodWebRequest( "http://localhost:8080/contact" );
        request2.setParameter( "firstName", "Franta" );
        request2.setParameter( "lastName", "Novák" );
        request2.setParameter( "email", "franta.novak@email.cz" );
        WebResponse response2 = sc.getResponse( request2 );
        assertNotNull( "No response received", response2 );
        assertEquals( "content type", "text/plain", response2.getContentType() );

    }

    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("firstName")).thenReturn("me");
        when(request.getParameter("lastName")).thenReturn("secret");
        when(request.getParameter("email")).thenReturn("secret");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new StoreContact().doGet(request, response);

        verify(request, atLeast(1)).getParameter("firstName"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains(""));
    }
}
