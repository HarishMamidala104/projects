package com.ojas.hiring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.UserDTO;
import com.ojas.hiring.dto.UserHistoryDTO;
//import com.ojas.hiring.entity.CustomDate;
import com.ojas.hiring.entity.ForgotPassword;
import com.ojas.hiring.entity.User;
import com.ojas.hiring.entity.UserHistory;
import com.ojas.hiring.exceptions.EmployeeIdChecking;
import com.ojas.hiring.exceptions.PasswordMismatchException;
import com.ojas.hiring.exceptions.UserNotFound;
import com.ojas.hiring.repo.UserHistoryRepo;
//import com.ojas.hiring.repo.UserHistoryRepo;
import com.ojas.hiring.repo.UserRepository;
import com.ojas.hiring.service.LogoutService;
import com.ojas.hiring.service.OhamsAppTrackingService;
import com.ojas.hiring.serviceImpl.KeycloakServiceImpl;
import com.ojas.hiring.utils.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserHistoryRepo userHistoryRepo;

	@Autowired
	KeycloakServiceImpl keycloakServiceImpl;
	
	 @Autowired
	    private LogoutService logoutService;
	
	@Autowired
	private OhamsAppTrackingService ohmasapptrackingserviceimpl;

	@Value("${spring.profiles.active}")
	String authenticationType;
	
             /**
              * This Code is currently Deactivated.
              * For the future Reference,
              * Instead of this functionality Keycloak functionality has been Used
              * 
              */

	
				/*
				 * @PostMapping("/registration") public ResponseEntity<String>
				 * loginUser(@Valid @RequestBody UserDTO userDTO) {
				 * 
				 * userDTO.setVisibility(1); Optional<User> findByemployeeId =
				 * userRepository.findByemployeeId(userDTO.getEmployeeId()); if
				 * (findByemployeeId.isPresent()) { findByemployeeId.get(); throw new
				 * InvalidUserId("employee Id is already existed...Please try with another employee Id"
				 * );
				 * 
				 * }
				 * 
				 * Optional<User> findByemailaddress =
				 * userRepository.findByemailaddress(userDTO.getEmail()); if
				 * (findByemailaddress.isPresent()) { findByemailaddress.get(); throw new
				 * EmailChecking("email is already existed...Please try with another email Id");
				 * } String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
				 * userDTO.setPassword(encodedPassword); LocalDate currentDate =
				 * LocalDate.now(); DateTimeFormatter formatter =
				 * DateTimeFormatter.ofPattern("dd MMM, yyyy"); String formattedDate =
				 * currentDate.format(formatter); String firstName = userDTO.getGiven_name() ==
				 * null ? "" : userDTO.getGiven_name(); String lastName = userDTO.getLastName()
				 * == null ? "" : userDTO.getLastName();
				 * userDTO.setUserName(firstName.concat(lastName)); User savedUser =
				 * userRepository.save(userDTO.toUserEntity()); UserHistoryDTO userHistoryDTO =
				 * new UserHistoryDTO(); userHistoryDTO.setEmployeeId(userDTO.getEmployeeId());
				 * userHistoryDTO.setEmailAddress(userDTO.getEmail());
				 * userHistoryDTO.setUserId(userDTO.getId());
				 * userHistoryDTO.setUserName(userDTO.getUserName());
				 * userHistoryDTO.setRole(userDTO.getRole());
				 * userHistoryDTO.setStartDate(formattedDate);
				 * userHistoryRepo.save(userHistoryDTO.toUserHistory()); String string =
				 * "Successfully registered"; return ResponseEntity.ok(string);
				 * 
				 * }
				 */
	@PostMapping("/login")
	public UserDTO getLoginDetails(@RequestParam("employeeId") String employeeId,
			@RequestParam("password") String password,@AuthenticationPrincipal Jwt jwt) {
		
		System.out.println(jwt);
		Long empId = null;
		
		// Get the JWT claims map
        Map<String, Object> claims = jwt.getClaims();
 
        // Use the utility method to get specific claims
        String username = (String) JwtUtils.getClaimValue(claims, "preferred_username");
        String email = (String) JwtUtils.getClaimValue(claims, "email");
        Object resourceAccess = JwtUtils.getClaimValue(claims, "resource_access");
		
		try {
			empId = Long.parseLong(employeeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
//		Optional<User> findByemployeeId = userRepository.findByemployeeId(empId);
		if (!authenticationType.equalsIgnoreCase("BASIC")) {
			UserDTO authenticatedUser = keycloakServiceImpl.authenticateUser(employeeId, password);
			System.out.println("userObject ====> " + authenticatedUser.toString());
//			authenticatedUser.setRole(findByemployeeId.get().getRole());
			System.out.println(authenticatedUser
					+ "========================================================================================");
			System.out
					.println("Is it Ok : " + authenticatedUser.getEmployeeId() + "\t" + authenticatedUser.getGiven_name());
			
			  if (authenticatedUser != null) {
			        // Log user activity as successful login
				  System.out.println(username);
				  authenticatedUser.getUserName();
			  ohmasapptrackingserviceimpl.logUserActivity( "Login", "Success", "Authentication", 1,username );
     
			        return authenticatedUser;
			    } else {
			        // Log user activity as failed login
			    	ohmasapptrackingserviceimpl.logUserActivity( "Login", "Failure", "Authentication", 0,username);

			        throw new UserNotFound("Incorrect Password or EmployeeId");
			    }
			
			
			
			//return authenticatedUser;
		} else {
			Optional<User> findByemployeeId = userRepository.findByemployeeId(empId);
			if (findByemployeeId.isPresent()) {
				User user = findByemployeeId.get();
				boolean matches = passwordEncoder.matches(password, user.getPassword());
				if (matches) {
//					return "Successfully logined";
					return mapUserToDTO(user);
				}
				throw new UserNotFound("Incorrect Password");
			}
		}

		throw new UserNotFound("EmployeeId is not found");

	}
	
	@PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        boolean logoutSuccessful = logoutService.logout(token);

        if (logoutSuccessful) {
        	  String name = SecurityContextHolder.getContext().getAuthentication().getName();
        	ohmasapptrackingserviceimpl.logUserActivity( "logout", "Failed", "Authentication", 1,name);
            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.badRequest().body("Logout failed");
        }
    }
	
private UserDTO mapUserToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUserName(user.getUserName());
		userDTO.setGiven_name(user.getGiven_name());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmailaddress());
		userDTO.setPassword(user.getPassword());
		userDTO.setConfirmPassword(user.getConfirmPassword());
		userDTO.setEmployeeId(user.getEmployeeId());
		userDTO.setVisibility(user.getVisibility());
		userDTO.setToken(user.getToken());
		userDTO.setRefreshToken(user.getRefreshToken());
		userDTO.setKeycloakId(user.getKeycloakId());
		userDTO.setRoles(user.getRoles());
		userDTO.setRole(user.getRole());
		userDTO.setGender(user.getGender());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		// Map other fields as needed

		return userDTO;
	}

//	@GetMapping("/login")
//	public User getLoginDetails(@RequestParam("employeeId") long employeeId,
//			@RequestParam("password") String password) {
//		System.out.println("Login");
//		Optional<User> findByemployeeId = userRepository.findByemployeeId(employeeId);
//		if (findByemployeeId.isPresent()) {
//			User user = findByemployeeId.get();
//			boolean matches = passwordEncoder.matches(password, user.getPassword());
//			if (matches) {
////				return "Successfully logined";
//				return user;
//			}
//			throw new UserNotFound("Incorrect Password");
//		}
//		throw new UserNotFound("EmployeeId is not found");
//
//	}

	@PostMapping("/forgotPassword")
	public String addForgotPasswordDetails(@RequestBody ForgotPassword forgotPassword) {
		Optional<User> findByemployeeId = userRepository.findByemployeeId(forgotPassword.getEmployeeId());
		if (findByemployeeId.isPresent()) {
			if (forgotPassword.getNewpassword().equals(forgotPassword.getConfirmPassword())) {
				String encode = passwordEncoder.encode(forgotPassword.getNewpassword());
				User user = findByemployeeId.get();
				user.setPassword(encode);
				userRepository.save(user);
				return "Password changed successfully";
			}
			throw new PasswordMismatchException("Password and Confirmed password are Mismatch");
		}
		return "Please provide Email_Address in login page";
	}

	@GetMapping("/getUserRolesHistoryById")
	public List<UserHistoryDTO> getUserRolesHistory(@RequestParam("id") long id) {
		List<UserHistory> userRolesHistoryByEmployeeId = userHistoryRepo.getUserRolesHistoryByEmployeeId(id);
		List<UserHistoryDTO> userHistoryDTOs = userRolesHistoryByEmployeeId.stream().map(this::convertToDTO)
				.collect(Collectors.toList());
		return userHistoryDTOs;
	}

	private UserDTO convertToUserDTO(User user) {
		return UserDTO.builder().id(user.getId()).userName(user.getUserName()).given_name(user.getGiven_name())
				.lastName(user.getLastName()).email(user.getEmailaddress()).password(user.getPassword()) // Note:
																											// Consider
																											// excluding
																											// sensitive
																											// fields
																											// like
																											// password
				.confirmPassword(user.getConfirmPassword()) // If needed
				.employeeId(user.getEmployeeId()).visibility(user.getVisibility()).token(user.getToken())
				.refreshToken(user.getRefreshToken()).keycloakId(user.getKeycloakId()).roles(user.getRoles())
				.role(user.getRole()).gender(user.getGender()).phoneNumber(user.getPhoneNumber()).build();
	}

	private UserHistoryDTO convertToDTO(UserHistory userHistory) {
		UserHistoryDTO dto = new UserHistoryDTO();
		dto.setEmployeeId(userHistory.getEmployeeId());
		dto.setUserName(userHistory.getUserName());
		dto.setEmailAddress(userHistory.getEmailaddress());
		dto.setRole(userHistory.getRole());
		dto.setStartDate(userHistory.getStartDate());
		dto.setEndDate(userHistory.getEndDate());
		dto.setDuration(userHistory.getDuration());
		return dto;
	}

//	@GetMapping("/getUserRolesHistory")
//	public List<UserHistory> getUserRolesHistory(@RequestParam ("employeeId") long employeeId){
//		List<UserHistory> userRolesHistoryByEmployeeId = userHistoryRepo.getUserRolesHistoryByEmployeeId(employeeId);
//        return 	userRolesHistoryByEmployeeId;
//	}

	@GetMapping("/getAllUserDetails")
	public List<UserDTO> getAllUserDetails() {
		List<User> allEmployeeDetails = userRepository.getAllUserDetails(1);
		List<UserDTO> userDTOs = allEmployeeDetails.stream().map(this::convertToUserDTO) // Map each User to UserDTO
																							// using convertToDTO method
				.collect(Collectors.toList());
		return userDTOs;
	}

	@DeleteMapping("/deleteUserById")
	public String deleteUser(@RequestParam("id") Integer id) {
		User user = userRepository.getById(id);
		user.setVisibility(0);
		userRepository.save(user);
		return "Successfully Deleted";

	}

	@PutMapping("/updateUserDetails")
	public String updateUserDetails(@RequestParam("employeeId") long employeeId, @RequestBody UserDTO userDTO)
			throws ParseException {
		Optional<User> findByemployeeId = userRepository.findByemployeeId(employeeId);
		User userDetails = findByemployeeId.get();

		if (userDTO.getEmail() != null) {
			userDetails.setEmailaddress(userDTO.getEmail());
		}
		if (userDTO.getPhoneNumber() != null) {
			userDetails.setPhoneNumber(userDTO.getPhoneNumber());
		}
		if (userDTO.getUserName() != null) {
			userDetails.setUserName(userDTO.getUserName());
		}
		if (userDTO.getRole() != null) {
			userDetails.setRole(userDTO.getRole());
		}

		userRepository.save(userDetails);
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String formattedDate = currentDate.format(formatter);
		UserHistory userHistory = userHistoryRepo.getByEmployeeId();
		userHistory.setEndDate(formattedDate);
		//
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
		java.util.Date parse = dateFormat.parse(userHistory.getStartDate());
		java.util.Date parse2 = dateFormat.parse(userHistory.getEndDate());
		LocalDateTime ofInstant = LocalDateTime.ofInstant(parse.toInstant(), ZoneId.systemDefault());
		LocalDateTime ofInstant2 = LocalDateTime.ofInstant(parse2.toInstant(), ZoneId.systemDefault());
		java.time.Duration between = java.time.Duration.between(ofInstant, ofInstant2);
		long totalDays = between.toDays();
		long years = totalDays / 365;
		long remainingDays = totalDays % 365;
		long months = remainingDays / 30;
		long days = remainingDays % 30;
		String string = (String.valueOf(years) + "Years," + String.valueOf(months) + "Months," + String.valueOf(days)
				+ "Days");
		userHistory.setDuration(string);
		//
		userHistoryRepo.save(userHistory);
		UserHistory userhistory = new UserHistory();
		userhistory.setEmployeeId(userDTO.getEmployeeId());
		userhistory.setEmailaddress(userDTO.getEmail());
		userhistory.setUser_id(userDetails.getId());
		userhistory.setUserName(userDTO.getUserName());
		userhistory.setRole(userDTO.getRole());
		userhistory.setStartDate(formattedDate);
		userHistoryRepo.save(userhistory);
		userRepository.save(userDetails);
		return "updated Successfully";

	}

	@GetMapping("/getEmployeeId")
	public List<String> getEmployeeId() {
		List<String> employeeIds = userRepository.getEmployeeIds();
		return employeeIds;

	}

	@GetMapping("/getEmployeeEmailIds")
	public List<String> getEmployeeEmailIds() {
		List<String> employeeEmailIds = userRepository.getEmployeeEmailIds();
		return employeeEmailIds;

	}

	@GetMapping("/getEmployeeName")
	public List<String> getEmployeeName() {
		List<String> employeeNames = userRepository.getEmployeeNames();
		return employeeNames;

	}

	@GetMapping("/getTagUserNames")
	public List<User> getTagUserNames() {
		return userRepository.getTagUsers();
	}

//	private static final String DEFAULT_ROLE = "ROLE_USER";
//	private static final String[] ADMIN_ACCESS = { "ROLE_ADMIN", "ROLE_MODERATOR" };
//	private static final String[] MODERATOR_ACCESS = { "ROLE_ADMIN", "ROLE_MODERATOR" };
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//

//
//	@PostMapping("/join")
//	public String joinGroup(@RequestBody User user) {
//		user.setRole(DEFAULT_ROLE);
//		String encryptPwd = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encryptPwd);
//		userRepository.save(user);
//		return user.getUsername();
//	}
//
//	// if loggedin user is ADMIN-> ADMIN or USER
//	// IF LOGGEDIN USER IS USER->USER
//
//	@GetMapping("/access/{userId}/{userRole}")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
//	public String giveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) {
//		User user = userRepository.findById(userId).get();
//		List<String> activeRoles = getRolesByLoggedInUser(principal);
//		String newRole = "";
//		if (activeRoles.contains(userRole)) {
//			newRole = user.getRole() + "," + userRole;
//			user.setRole(newRole);
//		}
//		userRepository.save(user);
//		return "Hi " + user.getUsername() + " New role assign to you by " + principal.getName();
//
//	}
//
//	private List<String> getRolesByLoggedInUser(Principal principal) {
//		String roles = getLoggedInUser(principal).getRole();
//		List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
//		if (assignRoles.contains("ROLE_ADMIN")) {
//			return Arrays.stream(ADMIN_ACCESS).collect(Collectors.toList());
//		}
//		if (assignRoles.contains("ROLE_MODERATOR")) {
//			return Arrays.stream(MODERATOR_ACCESS).collect(Collectors.toList());
//		}
//		return Collections.emptyList();
//
//	}
//
//	// First we want to know who is the logged user
//
//	private User getLoggedInUser(Principal principal) {
//		return null;
//
//	}

	@GetMapping("/checkByEmployeeId")
	public ResponseEntity checkByEmployeeId(@RequestParam("employeeId") long employeeId) {
		Optional<User> findByemployeeId = userRepository.findByemployeeId(employeeId);
		if (findByemployeeId.isPresent()) {
			throw new EmployeeIdChecking("EmployeeId is already existed...Please try with valid EmployeeId");
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@GetMapping("/getProfile")
	public ResponseEntity getprofileData(@RequestParam("employeeId") long employeeId) {
		Optional<User> findByemployeeId = userRepository.findByemployeeId(employeeId);
		User user = findByemployeeId.get();
		return new ResponseEntity(HttpStatus.OK);

	}

	/*
	 * @GetMapping("/details") public Map<String, Object> getUserDetails() { // Get
	 * the authentication object from SecurityContextHolder JwtAuthenticationToken
	 * authentication = (JwtAuthenticationToken)
	 * SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * // Get all claims from the JWT token Map<String, Object> claims =
	 * authentication.getTokenAttributes();
	 * 
	 * // Extract specific claims String email = (String) claims.get("email");
	 * List<String> roles = (List<String>) claims.get("roles");
	 * 
	 * // Print the email and roles System.out.println("Email: " + email); if (roles
	 * != null) { roles.forEach(role -> System.out.println("Role: " + role)); } else
	 * { System.out.println("No roles found."); }
	 * 
	 * // You can return the entire claims map or specific details as a response
	 * return claims; }
	 */

	@GetMapping("/details")
	public Map<String, Object> getUserDetails() {
		// Get the authentication object from SecurityContextHolder
		JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();

		// Get all claims from the JWT token
		Map<String, Object> claims = authentication.getTokenAttributes();

		// Extract specific claims
		String firstName = (String) claims.get("given_name");
		String preferredUsername = (String) claims.get("preferred_username");

		// Print the first name and preferred username
		System.out.println("First Name: " + firstName);
		System.out.println("Preferred Username: " + preferredUsername);

		// Return the user details (you can customize this to return only specific
		// fields)
		return Map.of("firstName", firstName, "preferredUsername", preferredUsername);
	}
}
