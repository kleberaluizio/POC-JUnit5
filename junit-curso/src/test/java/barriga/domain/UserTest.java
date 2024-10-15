package barriga.domain;

import barriga.domain.builders.UserBuilder;
import barriga.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Domain: User")
class UserTest {

    @Test
    @DisplayName("Should Create Valid User When Data Is Valid")
    public void shouldCreateValidUserWhenDataIsValid() {
        String name = "John John";
        String email = "john@john.com";
        String password = "password";
        User user = new User(null, name, email, password);

        assertEquals("John", user.getName());
        assertEquals("john@john.coma", user.getEmail());
        assertEquals(password, user.getPassword());

    }

    @Test
    @DisplayName("Refatora o teste anterior de modo a garantir que todos os assertions sejam executados")
    public void shouldCreateValidUserWhenDataIsValid_ASSERT_ALL() {
        User user = UserBuilder.aUser().withName("Paul").now();

        Assertions.assertAll("User",
            ()-> assertEquals("Paul", user.getName()),
            ()-> assertEquals("john@john.com", user.getEmail()),
            ()-> assertEquals("password", user.getPassword())
        );
    }

    @Test
    @DisplayName("Should reject user when name is not provided")
    public void shouldRejectUserWhenNameIsNotProvided() {
        ValidationException exception = assertThrows(ValidationException.class,  ()-> UserBuilder.aUser().withName(null).now());
        Assertions.assertEquals("Name cannot be null or empty", exception.getMessage());
    }
}