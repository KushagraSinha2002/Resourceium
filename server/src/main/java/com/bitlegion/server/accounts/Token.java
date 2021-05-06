package com.bitlegion.server.accounts;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String string;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getString() {
        return this.string;
    }

    public void setString() {
        UUID uuid = UUID.randomUUID();
        this.string = uuid.toString();
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Token() {
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", string='" + getString() + "'" + ", account='" + getAccount() + "'"
                + "}";
    }

}
