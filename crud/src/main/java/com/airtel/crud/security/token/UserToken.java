//package com.airtel.crud.security.token;
//
//import lombok.Getter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Getter
//public class UserToken extends AbstractAuthenticationToken {
//    private String principal;
//    private String credentials;
//
//    public UserToken(String principal, String credentials) {
//        super(new ArrayList<>());
//        this.principal = principal;
//        this.credentials = credentials;
//    }
//
//    @Override
//    public void eraseCredentials() {
//        this.credentials = null;
//    }
//}
package com.airtel.crud.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials; // Changed type to Object for flexibility

    public UserToken(Object principal, Object credentials) {
        super(null); // Passing null authorities here; you can adjust as per your needs
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false); // Initial authentication state
    }

    public UserToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // Must provide authorities to mark as authenticated
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
