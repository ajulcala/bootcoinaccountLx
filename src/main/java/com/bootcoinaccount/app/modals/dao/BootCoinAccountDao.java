package com.bootcoinaccount.app.modals.dao;

import com.bootcoinaccount.app.modals.documents.BootCoinAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BootCoinAccountDao extends ReactiveMongoRepository<BootCoinAccount, String> {
    Flux<BootCoinAccount> findByNroPhone(String nroPhone);
}
