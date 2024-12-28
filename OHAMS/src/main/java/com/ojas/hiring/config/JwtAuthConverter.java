package com.ojas.hiring.config;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        System.out.println(authorities);

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
       
        if (principleAttribute != null) {
            claimName = principleAttribute;
        }
        System.out.println("===================================================================================="+claimName);
        
     // Get all claims from the JWT token
      //  Map<String, Object> claims = authentication.getTokenAttributes();

       
        
        
        
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Map<String, Object>    resourceusers;
        Collection<String> resourceRoles;
        Collection<String> resourceUsers;

        if (jwt.getClaim("resource_access") == null) {
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(resourceId) == null) {
            return Set.of();
        }
        
        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        resourceRoles = (Collection<String>) resource.get("roles");
        resourceUsers = (Collection<String>) resource.get("users");
        resourceRoles.forEach(role -> System.out.println("Role: " + role));
     // Logging the users
        if (resourceUsers != null) {
            resourceUsers.forEach(user -> System.out.println("User: " + user));
        } else {
            System.out.println("No users found.");
        }
        List<String> list = resourceRoles.stream().toList();
        System.out.println("================================================dfaddfasfffffffffffffffffffffff================================");

list.forEach(c->System.out.println(c));
        System.out.println();
        return resourceRoles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}