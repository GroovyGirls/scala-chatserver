package de.htw.messenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Vera
 */
@Path("register")
public class RegisterController {
    Gson gson = new GsonBuilder().create();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPost(String jsonUser) {


        User user = gson.fromJson(jsonUser, User.class);
        System.out.println("start post");
        String json = gson.toJson(user);

        if (UserService.validateUser(user)) {
            UserService.addUser(user);
            return Response
                    .ok(json)
                    .build();
        }
        return Response
                .status(Response.Status.CONFLICT)
                .entity(json)
                .build();
    }
}

