package com.fpt.ppmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
//    public static final long EXPIRATION_TIME = 86400*1000*7; //1 tuần 1 ngày có 86400000 mili giây
    
    public static final long EXPIRATION_TIME = 60000;
}