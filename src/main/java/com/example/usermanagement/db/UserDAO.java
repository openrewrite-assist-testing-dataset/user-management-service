package com.example.usermanagement.db;

import com.example.usermanagement.core.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDAO {

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<User> findById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM users WHERE username = :username")
    Optional<User> findByUsername(@Bind("username") String username);

    @SqlQuery("SELECT * FROM users WHERE email = :email")
    Optional<User> findByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM users ORDER BY created_at DESC")
    List<User> findAll();

    @SqlUpdate("INSERT INTO users (username, email, password_hash, created_at, updated_at) " +
               "VALUES (:username, :email, :passwordHash, :createdAt, :updatedAt)")
    @GetGeneratedKeys
    Long insert(@BindBean User user);

    @SqlUpdate("UPDATE users SET username = :username, email = :email, " +
               "password_hash = :passwordHash, updated_at = :updatedAt WHERE id = :id")
    int update(@BindBean User user);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    int delete(@Bind("id") Long id);
}