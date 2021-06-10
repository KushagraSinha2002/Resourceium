package com.bitlegion.server.accounts;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String string;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void setString() {
        UUID uuid = UUID.randomUUID();
        this.string = uuid.toString();
    }

}
