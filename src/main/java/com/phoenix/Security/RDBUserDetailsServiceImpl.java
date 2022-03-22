package com.phoenix.Security;

import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Authority;
import com.phoenix.data.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class RDBUserDetailsServiceImpl implements UserDetailsService
{

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        query for user details from DB
        AppUser user = appUserRepository.findByEmail(username).orElse(null);

//        check that user exists
        if(user == null){
            throw new UsernameNotFoundException("Oga, you no get account");
        }

//        return UserDetails
        return new User(user.getEmail(),user.getPassword(),
                getAuthorities(user.getAuthorities()));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Authority> authorities){
        return authorities.stream().map(authority ->
                new SimpleGrantedAuthority(authority
                        .name())).collect(Collectors.toList());
    }
}
