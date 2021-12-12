package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto;

import java.math.BigDecimal;

public class UserDto {
    private Long id;
    private String login;
    private BigDecimal balance;

    public UserDto(){

    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
