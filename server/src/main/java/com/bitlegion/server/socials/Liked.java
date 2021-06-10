package com.bitlegion.server.socials;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.uploads.Folder;

@Entity
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private Folder folder;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Folder getFolder() {
        return this.folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", account='" + getAccount() + "'" + ", folder='" + getFolder() + "'"
                + "}";
    }

}
