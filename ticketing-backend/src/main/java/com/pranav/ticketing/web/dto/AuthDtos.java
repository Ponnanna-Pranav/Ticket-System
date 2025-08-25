package com.pranav.ticketing.web.dto;
public record RegisterReq(String email, String name, String password) {}
public record LoginReq(String email, String password) {}
public record TokenRes(String accessToken, String refreshToken) {}
