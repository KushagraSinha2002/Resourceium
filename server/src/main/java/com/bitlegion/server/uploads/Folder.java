package com.bitlegion.server.uploads;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bitlegion.server.accounts.Account;

@Entity
public class Folder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date DateOfUpload;

    //if the boolean alue is true then the folder is private and only for yhe user.
    private Boolean hidden;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}
