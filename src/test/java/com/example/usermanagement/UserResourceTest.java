package com.example.usermanagement;

import com.example.usermanagement.core.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserResourceTest {

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User("testuser", "test@example.com", "password123");
        testUser.setId(1L);
    }

    @Test
    public void testUserCreation() {
        assertNotNull(testUser);
        assertEquals("testuser", testUser.getUsername());
        assertEquals("test@example.com", testUser.getEmail());
        assertEquals("password123", testUser.getPasswordHash());
        assertEquals(Long.valueOf(1L), testUser.getId());
    }

    @Test
    public void testUserGettersAndSetters() {
        testUser.setUsername("newusername");
        testUser.setEmail("new@example.com");
        testUser.setPasswordHash("newpassword");
        
        assertEquals("newusername", testUser.getUsername());
        assertEquals("new@example.com", testUser.getEmail());
        assertEquals("newpassword", testUser.getPasswordHash());
    }

    @Test
    public void testUserPrincipalName() {
        assertEquals("testuser", testUser.getName());
    }

    @Test
    public void testUserConstructorWithParameters() {
        User user = new User("john", "john@example.com", "secret123");
        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("secret123", user.getPasswordHash());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    public void testUserDefaultConstructor() {
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPasswordHash());
        assertNull(user.getId());
    }
}