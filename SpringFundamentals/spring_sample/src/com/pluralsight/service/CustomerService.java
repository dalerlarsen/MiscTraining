package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Customer;

public interface CustomerService {

	public abstract List<Customer> findAll();

}