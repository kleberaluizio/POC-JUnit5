package barriga.service;

import barriga.domain.User;
import barriga.exceptions.ValidationException;
import barriga.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static barriga.domain.builders.UserBuilder.aUser;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock // Define que será mockado
    private UserRepository repository;
    @InjectMocks // Define que será injetados os mocks necessários
    private UserService service;
    private String EMAIL;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        EMAIL = "mail@mail.com";
    }

    @Test
    public void shouldReturnEmptyWhenUserNotExist() {
        Mockito.when(repository.getUserByEmail(EMAIL)).thenReturn(Optional.empty());
        Optional<User> userOptional = service.findUserByEmail(EMAIL);

        Assertions.assertTrue(userOptional.isEmpty());
    }

    @Test
    public void shouldReturnUserWhenUserExists() {
        UserRepository repository = mock(UserRepository.class);
        service = new UserService(repository);
        Mockito.when(repository.getUserByEmail(EMAIL))
                .thenReturn(Optional.of(aUser().now()));

        Optional<User> existingOptional = service.findUserByEmail(EMAIL);
        Assertions.assertTrue(existingOptional.isPresent());

        Mockito.verify(repository).getUserByEmail(EMAIL);
    }

    @Test
    @DisplayName("USING TIMES")
    public void shouldReturnUserWhenUserExists1() {
        UserRepository repository = mock(UserRepository.class);
        service = new UserService(repository);

        Mockito.when(repository.getUserByEmail(EMAIL))
                .thenReturn(Optional.of(aUser().now()));

        Optional<User> existingOptional = service.findUserByEmail(EMAIL);
        Assertions.assertTrue(existingOptional.isPresent());

        // Verifica que o método foi invocado a quantidade de vezes indicada em times
        Mockito.verify(repository, Mockito.times(1)).getUserByEmail(EMAIL);

        // Verifica que o método foi invocado ao menos uma vez para o parametro fornecido
        Mockito.verify(repository, Mockito.atLeastOnce()).getUserByEmail(EMAIL);

        // Verifica que o método NUNCA foi invocado com o parametro "a@a.com"
        Mockito.verify(repository, Mockito.never()).getUserByEmail("a@a.com");

        // Verifica se realmente houve alguma iteração além das que eu estava esperando
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("MULTIPLES RETURNS")
    public void shouldReturnUserWhenUserExists2() {
        Mockito.when(repository.getUserByEmail(EMAIL))
                .thenReturn(Optional.of(aUser().now()), Optional.of(aUser().now()), null);

        Optional<User> existingOptional = service.findUserByEmail(EMAIL);
        System.out.println(existingOptional);
        existingOptional = service.findUserByEmail(EMAIL);
        System.out.println(existingOptional);
        existingOptional = service.findUserByEmail(EMAIL);
        System.out.println(existingOptional);

        Assertions.assertTrue(existingOptional.isPresent());
    }

    @Test
    public void shouldSaveUserWithSuccess() {
        // GIVEN
        User userToSave = aUser().withId(null).now();

        // WHEN
        //when(repository.getUserByEmail(userToSave.getEmail())).thenReturn(Optional.empty());
        when(repository.save(userToSave)).thenReturn(aUser().now());

        // THEN
        User savedUser = service.save(userToSave);
        Assertions.assertNotNull(savedUser.getId());

        verify(repository).getUserByEmail(userToSave.getEmail());
        //verify(repository).save(userToSave);
    }
}
