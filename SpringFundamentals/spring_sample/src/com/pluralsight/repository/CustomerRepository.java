package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Customer;

public interface CustomerRepository {

	public abstract List<Customer> findAll();

}