package com.example.usermanagement;

import com.example.usermanagement.api.UserResource;
import com.example.usermanagement.auth.BasicAuthenticator;
import com.example.usermanagement.core.User;
import com.example.usermanagement.db.UserDAO;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class UserManagementApplication extends Application<UserManagementConfiguration> {

    public static void main(String[] args) throws Exception {
        new UserManagementApplication().run(args);
    }

    @Override
    public String getName() {
        return "user-management-service";
    }

    @Override
    public void initialize(Bootstrap<UserManagementConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<UserManagementConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(UserManagementConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(UserManagementConfiguration configuration, Environment environment) throws Exception {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        final BasicAuthenticator authenticator = new BasicAuthenticator(userDAO);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticator)
                        .setRealm("User Management")
                        .buildAuthFilter()));
        
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(new UserResource(userDAO));
    }
}