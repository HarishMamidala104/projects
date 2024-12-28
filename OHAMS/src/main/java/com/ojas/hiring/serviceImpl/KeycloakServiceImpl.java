package com.ojas.hiring.serviceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.ojas.hiring.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojas.hiring.dto.AccessToken;
import com.ojas.hiring.dto.KeycloakRole;
import com.ojas.hiring.dto.KeycloakUser;
import com.ojas.hiring.entity.Roles;
import com.ojas.hiring.entity.User;
import com.ojas.hiring.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeycloakServiceImpl {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${keycloak.token.api.url}")
	String tokenUri;

	String getUsersUri = "/users";

	@Value("${keycloak.roles.api.url}")
	String getRolesUri;

	@Autowired
	UserRepository userRepository;
	 
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public UserDTO authenticateUser(String employeeId, String password) {

		String token = null, refreshToken = null;
//		User user = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("PRIVATE-TOKEN", "xyz");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", "ohams");
		params.add("username", employeeId);
		params.add("password", password);
		System.out.println(employeeId);
		System.out.println(password);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
		ResponseEntity<AccessToken> accessTokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, entity,
				AccessToken.class);
		

		if (accessTokenResponse.getStatusCode() == HttpStatus.OK) {
			AccessToken accessToken = (AccessToken) accessTokenResponse.getBody();
			
			
			System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+accessToken);
			if (accessToken == null) {
				throw new IllegalStateException(
						"No keycloak user has been found with the given username : " + employeeId);
			} else {
				token = accessToken.getAccess_token();
				refreshToken = accessToken.getRefresh_token();
				String roles = "";
				
				String[] parts = token.split("\\.");
				String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode rootNode = objectMapper.readTree(payloadJson);
					JsonNode resourceAccessNode = rootNode.path("resource_access");
					JsonNode recruitexNode = resourceAccessNode.path("ohams");
					JsonNode rolesNode = recruitexNode.path("roles");
					System.out.println("RolesNOde is "+rolesNode);
					if (rolesNode.isArray()) {
						for (JsonNode role : rolesNode) {
							System.out.println(role.asText());
							//roles.add(role.asText());
							if(roles.length() > 1) {
								roles += ",";
							}
							roles += role.asText();
						}
					}
					String email = rootNode.path("email").asText();
					String firstName = rootNode.path("given_name").asText();
					String lastName = rootNode.path("family_name").asText();
					String userName = rootNode.path("preferred_username").asText();

					System.out.println("adfasdjfkasjjlkfasjkljlkjflkajkfajklj;fajjas;==================================================================="+email);
//					Optional<User> user = userRepository.findByemailaddress(email);
//					user.get().setToken(token);
//					user.get().setRefreshToken(refreshToken);
//					user.get().setRoles(roles);
//					return user.get();
					System.out.println(rootNode+"============================================================");
					
					UserDTO userDTO = new UserDTO();
					userDTO.setToken(token);
					userDTO.setRefreshToken(refreshToken);
					userDTO.setUserName(userName);
					userDTO.setRoles(roles);
					userDTO.setLastName(lastName);
					userDTO.setGiven_name(firstName);;
					userDTO.setEmail(email);
					return userDTO;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	
	 public boolean isTokenExpired(String token) {
	        try {
	            String[] parts = token.split("\\.");
	            if (parts.length != 3) {
	                throw new IllegalArgumentException("Invalid JWT token format.");
	            }

	            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode rootNode = objectMapper.readTree(payloadJson);

	            long exp = rootNode.path("exp").asLong();
	            long currentTime = System.currentTimeMillis() / 1000;

	            return currentTime >= exp;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return true;
	        }
	
	 }
	 	 
	public void loadKeycloakUsers() {

		List<KeycloakUser> allKeycloakUsers = new ArrayList<KeycloakUser>();
		try {
			ResponseEntity<?> tokenEntityResponse = getAccessToken();
			AccessToken accessToken = (AccessToken) tokenEntityResponse.getBody();
			String token = accessToken.getAccess_token();
			List<User> toBeUpdatedUsers = new ArrayList<>();
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());

			allKeycloakUsers = getKeycloakUsers(token);
			for (KeycloakUser keycloakUser : allKeycloakUsers) {
				String email = keycloakUser.getEmail();
				
//		        String url = KEYCLOAK_SERVER_URL + "/admin/realms/" + REALM + "/users";
//RestTemplate rt=new RestTemplate();
//Object forObject = rt.getForObject(url,Object.class );
				Optional<User> existingUser = userRepository.findByemailaddress(email);
				if (existingUser.isPresent()) {
					if (existingUser.get().getKeycloakId() == null) {
						existingUser.get().setGiven_name(keycloakUser.getFirstName());//hv to focus
						existingUser.get().setLastName(keycloakUser.getLastName());
						existingUser.get().setKeycloakId(keycloakUser.getId());
						existingUser.get().setRole(keycloakUser.getRole());
//						existingUser.get().setModifiedDate(new Date());
						toBeUpdatedUsers.add(existingUser.get());
					}
				} else {
					User newUser = new User();

					newUser.setUserName(keycloakUser.getUsername());
					newUser.setGiven_name(keycloakUser.getFirstName());
					newUser.setLastName(keycloakUser.getLastName());
					String encodedPassword = passwordEncoder.encode("test");
					newUser.setPassword(encodedPassword);
					newUser.setEmailaddress(keycloakUser.getEmail());
					newUser.setRole(keycloakUser.getRole());
					newUser.setKeycloakId(keycloakUser.getId());
					newUser.setVisibility(1);
					toBeUpdatedUsers.add(newUser);
				}
			}

			if (toBeUpdatedUsers.size() > 0) {
				userRepository.saveAll(toBeUpdatedUsers);
			} else {
				log.info("No new keycloak user has been found to add into database.");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private ResponseEntity<?> getAccessToken() {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "password");
			params.add("client_id", "ohams");
			params.add("username", "somashekar");
			params.add("password", "bssm12");
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

			return restTemplate.exchange(tokenUri, HttpMethod.POST, entity, AccessToken.class);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("An error occurred while fetching the token : " + e.getMessage());
		}

	}

	private ResponseEntity<?> getKeycloakUsers(String getUsersUri, String accessToken) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", "Bearer " + accessToken);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<List<KeycloakUser>> response = restTemplate.exchange(getUsersUri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<KeycloakUser>>() {
					});
			return response;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("An error occurred while fetching the keycloak users : " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<KeycloakUser> getKeycloakUsers(String token) {

		List<KeycloakUser> allKeycloakUsers = new ArrayList<KeycloakUser>();
		List<KeycloakRole> keycloakRoles = null;
		System.out.println("ENTERED INTO ROLES API.............");
		try {
			if (token == null) {
				ResponseEntity<?> tokenEntityResponse = getAccessToken();
				AccessToken accessToken = (AccessToken) tokenEntityResponse.getBody();
				token = accessToken.getAccess_token();
			}

			ResponseEntity<?> keycloakRolesResponse = getKeycloakRoles(token);
			keycloakRoles = (List<KeycloakRole>) keycloakRolesResponse.getBody();
			List<String> roleNames = keycloakRoles.stream().map(KeycloakRole::getName).collect(Collectors.toList());
			
			for (String roleName : roleNames) {
				getUsersUri = getRolesUri + "/" + roleName + "/users";
				ResponseEntity<?> keycloakUsersEntityResponse = getKeycloakUsers(getUsersUri, token);
				List<KeycloakUser> keycloakUsers = (List<KeycloakUser>) keycloakUsersEntityResponse.getBody();
				for (KeycloakUser user : keycloakUsers) {
					user.setRole(roleName);
				}
				allKeycloakUsers.addAll(keycloakUsers);
			}
		} catch (Exception e) {

		}
		return allKeycloakUsers;
	}

	private ResponseEntity<?> getKeycloakRoles(String accessToken) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", "Bearer " + accessToken);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<List<KeycloakRole>> response = restTemplate.exchange(getRolesUri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<KeycloakRole>>() {
					});
			return response;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("An error occurred while fetching the keycloak roles : " + e.getMessage());
		}
	}
	
	
	

}
