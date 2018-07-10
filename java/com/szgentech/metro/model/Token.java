package com.szgentech.metro.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
	private String accessToken;
	private String tokenType;
	private String scope;
	private Integer expires;
	private String jti;

	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("token_type")
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@JsonProperty("expires_in")
	public Integer getExpires() {
		return expires;
	}

	public void setExpires(Integer expires) {
		this.expires = expires;
	}

	@JsonProperty("jti")
	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

}
