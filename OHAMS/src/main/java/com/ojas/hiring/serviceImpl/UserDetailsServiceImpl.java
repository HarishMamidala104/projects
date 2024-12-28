package com.ojas.hiring.serviceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.User;
import com.ojas.hiring.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userObj = userRepository.findByemployeeId(Long.parseLong(username));
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("user"));
        if(userObj.isPresent()) {
        	User user = userObj.get();
        	   Set<GrantedAuthority> grantedAuthorities = Arrays.stream(user.getRole().split(","))
        	            .map(role -> new SimpleGrantedAuthority(role.trim()))
        	            .collect(Collectors.toSet());
        	   System.out.println("Authorities: " + grantedAuthorities);
        	return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                    grantedAuthorities);
        } 
        else {
        	return null;
        }
        	
	}

}
