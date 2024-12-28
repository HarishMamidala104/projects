//package com.ojas.hiring.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.ojas.hiring.entity.User;
//import com.ojas.hiring.repo.UserRepository;
//
//public class GroupUserDetailsService implements UserDetailsService{
//
//	@Autowired
//	private UserRepository userReposistory;
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> user = userReposistory.findByusername(username);
//		
//		return user.map(GroupUserDetails::new)
//				.orElseThrow(()->new UsernameNotFoundException(username+"username doesnot exist"));
//	}
//
//}
