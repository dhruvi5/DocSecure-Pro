//package com.airtel.crud.service;
////
////import com.airtel.crud.employee.Employee;
////import org.springframework.stereotype.Service;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Optional;
////
////@Service
////public class emp_service_impl implements emp_service {
////
////    private final List<Employee> employees = new ArrayList<>();
////
////    @Override
////    public String upsert(Employee emp) {
////        Optional<Employee> existingEmp = employees.stream()
////                .filter(e -> e.getId().equals(emp.getId()))
////                .findFirst();
////        existingEmp.ifPresent(employees::remove);
////        employees.add(emp);
////        return "Employee upserted successfully";
////    }
////
////    @Override
////    public Employee getById(Long eid) {
////        return employees.stream()
////                .filter(e -> eid.equals(e.getId())) // Use the safe comparison with eid
////                .findFirst()
////                .orElse(null);
////    }
////
////
////    @Override
////    public List<Employee> getAllEmployees() {
////        return employees;
////    }
////
////    @Override
////    public String deleteById(Long eid) {
////        Optional<Employee> existingEmp = employees.stream()
////                .filter(e -> e.getId().equals(eid))
////                .findFirst();
////        if (existingEmp.isPresent()) {
////            employees.remove(existingEmp.get());
////            return "Employee deleted successfully";
////        }
////        return "Employee not found";
////    }
////}
//
//import com.airtel.crud.employee.Employee;
//import com.airtel.crud.repo.EmployeeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class ServiceImplement implements EmployeeService {
//
//
//
//    @Autowired
//    private EmployeeRepo employeeRepository;
//
//    @Override
//    public String upsert(Employee emp) {
//        employeeRepository.save(emp);
//        return "Employee upserted successfully";
//    }
//
//
//
//    @Override
//    public Employee getById(Long eid) {
//        return employeeRepository.findById(eid).orElse(null);
//    }
//
//    @Override
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
//
//    @Override
//    public String deleteById(Long eid) {
//        if (employeeRepository.existsById(eid)) {
//            employeeRepository.deleteById(eid);
//            return "Employee deleted successfully";
//        } else {
//            return "Employee not found";
//        }
//    }
//
//
//
//}


package com.airtel.crud.service;

import com.airtel.crud.dto.Employee;
import com.airtel.crud.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImplement implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Override
    public String upsert(Employee emp) {
        employeeRepository.save(emp);
        return "Employee upserted successfully";
    }

    @Override
    public Employee getById(Long eid) {
        return employeeRepository.findById(eid).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String deleteById(Long eid) {
        if (employeeRepository.existsById(eid)) {
            employeeRepository.deleteById(eid);
            return "Employee deleted successfully";
        } else {
            return "Employee not found";
        }
    }
}
