package com.mkt.corevalue.authentication.util;

public interface AutheticationConstant {
	/*
	 * static final String HEADER_STRING = "Authorization"; static final String
	 * TOKEN_PREFIX = "Bearer"; static final int ACCESS_TOKEN_VALIDITY_SECONDS =
	 * 360000; static final String TOKEN_ISSUER = "IGCOREVALUE";
	 */
	public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "iat";
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";
}
