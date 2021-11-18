package com.bootcoinaccount.app.controllers;

import com.bootcoinaccount.app.modals.documents.BootCoinAccount;
import com.bootcoinaccount.app.services.BootCoinAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
@Slf4j
@RestController
@RequestMapping("/bootcoint")
public class BootCoinAccountController {
    @Autowired
    private BootCoinAccountService service;

    @GetMapping("/list")
    public Flux<BootCoinAccount> findAll(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<BootCoinAccount> findById(@PathVariable String id){
        return service.findById(id);
    }

    @GetMapping("/find/phone/{number}")
    public Mono<BootCoinAccount> findByNroPhone(@PathVariable String number){
        return service.findByNroPhone(number).next();
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<BootCoinAccount>> create(@RequestBody BootCoinAccount bootCoinAccount){
        log.info("Buscando ....");
        return service.findByNroPhone(bootCoinAccount.getNroPhone()).collectList().flatMap(c -> {
                    if(c.isEmpty()){
                        log.info("Enviando nueva cuenta");
                        bootCoinAccount.setCreateAt(LocalDateTime.now());
                        return service.create(bootCoinAccount);
                    }else{
                        log.info("ya existe un nuemro con esa cuenta cuenta");
                        return Mono.empty();
                    }
                })
                .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<BootCoinAccount>> update(@RequestBody BootCoinAccount monederoAccount) {
        log.info("buscando tarjeta de credito");
        return service.findById(monederoAccount.getId())
                .flatMap(cc -> {
                    cc.setEmail(monederoAccount.getEmail());
                    cc.setNroDocument(monederoAccount.getNroDocument());
                    cc.setNroPhone(monederoAccount.getNroPhone());
                    cc.setTypeDocument(monederoAccount.getTypeDocument());
                    cc.setAmountCoin(monederoAccount.getAmountCoin());
                    return service.create(cc);
                })
                .map(cc->new ResponseEntity<>(cc , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        log.info("eliminando ....");
        return service.delete(id)
                .filter(da -> da)
                .map(da -> new ResponseEntity<>("Account Deleted", HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
