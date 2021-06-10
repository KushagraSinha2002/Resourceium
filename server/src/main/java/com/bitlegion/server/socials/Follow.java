package com.bitlegion.server.socials;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bitlegion.server.accounts.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "follower_id", "following_id" }))
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private Account follower;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "following_id", referencedColumnName = "id")
    private Account following;

}
