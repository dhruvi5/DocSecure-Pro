package com.airtel.crud.rest;

import com.airtel.crud.dto.CustomUser;
import com.airtel.crud.dto.Employee;
import com.airtel.crud.repo.UserRepo;
import com.airtel.crud.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@RequestMapping("/api")
//public class RestCont {
//    private static final Logger logger = LoggerFactory.getLogger(RestCont.class);
//
//    @Autowired
//    private EmployeeService e_s;
//
//    @PostMapping("/emp")
//    public ResponseEntity<String> createEmp(@RequestBody Employee e) {
//        String status = e_s.upsert(e);
//        return new ResponseEntity<>(status, HttpStatus.CREATED);
//    }
//
//    // Change login endpoint to return a JSON response
////    @GetMapping("/login")
////    public ResponseEntity<String> login() {
////        return new ResponseEntity<>("Login successful", HttpStatus.OK);
////    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
//        String username = credentials.get("username");
//        String password = credentials.get("password");
//        // Authenticate the user (pseudo code, replace with actual authentication)
//        boolean isAuthenticated = authenticateUser(username, password);
//        if (isAuthenticated) {
//            return new ResponseEntity<>("Login successful", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private boolean authenticateUser(String username, String password) {
//        // Retrieve user from database based on username
//        CustomUser user = userRepo.findByUsername(username);
//
//        if (user != null) {
//            // Compare provided password with encoded password from database
//            return passwordEncoder.matches(password, user.getPassword());
//        }
//
//        return false; // User not found or password doesn't match
//    }
//
//    @GetMapping("/emp/{eid}")
//    public ResponseEntity<Employee> getEmp(@PathVariable Long eid) {
//        Employee e = e_s.getById(eid);
//        if (e == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(e, HttpStatus.OK);
//    }
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    @GetMapping("/emp_2")
//    public ResponseEntity<List<Employee>> getAllEmployees() {
//        List<Employee> employees = employeeService.getAllEmployees();
//        return new ResponseEntity<>(employees, HttpStatus.OK);
//    }
//
//
//    @DeleteMapping("/emp/{eid}")
//    public ResponseEntity<String> delEmp(@PathVariable Long eid) {
//        String status = e_s.deleteById(eid);
//        return new ResponseEntity<>(status, HttpStatus.OK);
//    }
//
//    @PutMapping("/emp_3")
//    public ResponseEntity<String> updateEmp(@RequestBody Employee e) {
//        String status = e_s.upsert(e);
//        return new ResponseEntity<>(status, HttpStatus.OK);
//    }
//}

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api")
public class RestCont {
    private static final Logger logger = LoggerFactory.getLogger(RestCont.class);

    private final EmployeeService employeeService;
    private final UserRepo userRepo;

    @Autowired
    public RestCont(EmployeeService employeeService, UserRepo userRepo) {
        this.employeeService = employeeService;
        this.userRepo = userRepo;
    }

    @PostMapping("/emp")
    public ResponseEntity<String> createEmp(@RequestBody Employee e) {
        String status = employeeService.upsert(e);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }


    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        System.out.println("Received login request with credentials: " + credentials);
        logger.info("Received login request with username: {}", username);
        if ("dhruvi".equals(username) && "1234".equals(password)) {
            logger.info("User '{}' successfully logged in", username);
            return ResponseEntity.ok().build(); // Return HTTP 200 OK for successful login
        } else {
            logger.warn("Failed login attempt for user '{}'", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return HTTP 401 Unauthorized for failed login
        }
//        boolean isAuthenticated = authenticateUser(username, password);
//        if (isAuthenticated) {
//            return new ResponseEntity<>("Login successful", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
//        }
    }

    private boolean authenticateUser(String username, String password) {
        CustomUser user = userRepo.findByUsername(username);
        if (user != null && passwordEncoder != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    @GetMapping("/emp/{eid}")
    public ResponseEntity<Employee> getEmp(@PathVariable Long eid) {
        Employee e = employeeService.getById(eid);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping("/emp_2")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @DeleteMapping("/emp/{eid}")
    public ResponseEntity<String> delEmp(@PathVariable Long eid) {
        String status = employeeService.deleteById(eid);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/emp_3")
    public ResponseEntity<String> updateEmp(@RequestBody Employee e) {
        String status = employeeService.upsert(e);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
