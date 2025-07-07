package com.example.usermanagement.api;

import com.example.usermanagement.core.User;
import com.example.usermanagement.db.UserDAO;
import io.dropwizard.auth.Auth;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GET
    public List<User> getUsers(@Auth User authenticatedUser) {
        return userDAO.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id, @Auth User authenticatedUser) {
        Optional<User> user = userDAO.findById(id);
        if (user.isPresent()) {
            return Response.ok(user.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createUser(@Valid User user, @Auth User authenticatedUser) {
        if (userDAO.findByUsername(user.getUsername()).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Username already exists")
                    .build();
        }
        
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Email already exists")
                    .build();
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        
        Long id = userDAO.insert(user);
        user.setId(id);
        
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid User user, @Auth User authenticatedUser) {
        Optional<User> existingUser = userDAO.findById(id);
        if (!existingUser.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        user.setId(id);
        user.setUpdatedAt(Instant.now());
        
        if (user.getPasswordHash() != null) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        
        userDAO.update(user);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id, @Auth User authenticatedUser) {
        int deleted = userDAO.delete(id);
        if (deleted > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}