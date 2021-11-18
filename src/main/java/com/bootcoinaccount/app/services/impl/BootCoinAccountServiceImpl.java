package com.bootcoinaccount.app.services.impl;

import com.bootcoinaccount.app.modals.dao.BootCoinAccountDao;
import com.bootcoinaccount.app.modals.documents.BootCoinAccount;
import com.bootcoinaccount.app.services.BootCoinAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BootCoinAccountServiceImpl implements BootCoinAccountService {
    @Autowired
    private BootCoinAccountDao dao;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Flux<BootCoinAccount> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<BootCoinAccount> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<BootCoinAccount> create(BootCoinAccount bootCoinAccount) {
        return dao.save(bootCoinAccount);
    }

    @Override
    public Flux<BootCoinAccount> findByNroPhone(String nroPhone) {
        return dao.findByNroPhone(nroPhone);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return dao.findById(id)
                .flatMap(money -> dao.delete(money).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
