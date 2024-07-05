package com.airtel.crud.repo;//package com.airtel.crud.repo;
//
//import com.airtel.crud.dto.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepo extends JpaRepository<User, String> {
//    Optional<User> findById(String username);
//
//    User findByUsername(String username);
//}
//


import com.airtel.crud.dto.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
}


