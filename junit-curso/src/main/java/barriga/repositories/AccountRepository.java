package barriga.repositories;

import barriga.domain.Account;
import barriga.domain.User;

import java.util.List;

public interface AccountRepository {

    Account save(Account account);

    List<Account> getAccountsByUser(User user);

    void deleteAccount(Account account);
}
