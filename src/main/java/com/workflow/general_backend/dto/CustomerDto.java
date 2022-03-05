package com.workflow.general_backend.dto;


import com.workflow.general_backend.entity.Customer;

public class CustomerDto {
    private Customer customer;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
