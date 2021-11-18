package com.bootcoinaccount.app.services;

import com.bootcoinaccount.app.modals.documents.BootCoinAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootCoinAccountService {

    Flux<BootCoinAccount> findAll();

    Mono<BootCoinAccount> findById(String id);

    Mono<BootCoinAccount> create(BootCoinAccount bootCoinAccount);

    Flux<BootCoinAccount> findByNroPhone(String nroPhone);

    Mono<Boolean> delete(String id);
}
