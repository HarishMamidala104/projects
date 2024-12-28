package com.ojas.hiring.utils;

import java.util.Map;

public class JwtUtils {
	public static Object getClaimValue(Map<String, Object> claims, String key) {
        return claims.getOrDefault(key, null);
    }
}
