package com.airtel.crud.service;

import com.airtel.crud.dto.Employee;

import java.util.List;

public interface EmployeeService {
    String upsert(Employee emp);
    Employee getById(Long id);
    List<Employee> getAllEmployees();
    String deleteById(Long id);
}
