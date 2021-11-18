package com.bootcoinaccount.app.modals.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("BootCoinAccount")
public class BootCoinAccount {
    @Id
    private String id;

    @NotNull
    private TypeDocument typeDocument;

    @NotEmpty
    private String nroDocument;

    @NotNull
    private String nroPhone;

    @NotEmpty
    private String email;

    @NotNull
    private Double amountCoin;

    private LocalDateTime createAt;

    public enum TypeDocument {
        DNI,
        PASAPORTE,
        CEX
    }
}
