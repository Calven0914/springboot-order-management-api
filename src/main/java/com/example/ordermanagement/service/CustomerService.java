package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.CreateCustomerRequest;
import com.example.ordermanagement.dto.CustomerResponse;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.exception.BusinessException;
import com.example.ordermanagement.repository.CustomerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Customer email already exists");
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());

        return toResponse(customerRepository.save(customer));
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
    }
}

