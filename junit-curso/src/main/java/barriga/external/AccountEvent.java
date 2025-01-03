package barriga.external;

import barriga.domain.Account;

public interface AccountEvent {
    enum EventType {CREATED, UPDATED, DELETED}
    void dispatch(Account account, EventType eventType);
}
