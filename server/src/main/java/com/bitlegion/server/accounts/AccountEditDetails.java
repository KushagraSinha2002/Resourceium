package com.bitlegion.server.accounts;

import java.util.Date;

import lombok.Data;

@Data
public class AccountEditDetails {

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String bio;

    private String country;

}
