package de.htw.messenger;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Vera
 */
public class RegisterControllerTest extends JerseyTest {


    public static final String DEFAULT_TEST_USERS = "[{\"name\":\"anna\",\"email\":\"anna@domain.com\",\"password\":\"123\"}]";

    @Override
    protected Application configure() {
        return new ResourceConfig(RegisterController.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        File tempFile = File.createTempFile("user", ".json");
        tempFile.deleteOnExit();
        System.out.println(tempFile.getAbsolutePath());
        UserService.userFile_(tempFile.getAbsolutePath());
        List<String> lines = Collections.singletonList(DEFAULT_TEST_USERS);
        Files.write(tempFile.toPath(), lines, Charset.forName("UTF-8"));
    }

    @Test
    public void registerExistingUser() {
        String user = "{\"name\":\"anna\",\"email\":\"anna@domain.com\",\"password\":\"123\"}";
        Entity<String> entity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("register").request().post(entity);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    public void registerNewUser() {
        String user = "{\"name\":\"anna\",\"email\":\"neu@domain.com\",\"password\":\"123\"}";
        Entity<String> entity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("register").request().post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}