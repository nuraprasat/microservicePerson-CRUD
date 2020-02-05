package com.service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorisationServerConfig extends AuthorizationServerConfigurerAdapter {

	static final String CLIENT_ID = "myclient";
	static final String CLIENT_SECRET = "secret";
	static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final String CHECK_ENDPOINT = "http://localhost:8080/oauth/check_token";
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
	
    @Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		   security.checkTokenAccess("isAuthenticated()");
	}
	
	// Configure the token store and authentication manager
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//@formatter:off
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
		//@formatter:on
	}

	// Configure a client store. In-memory for simplicity, but consider other
	// options for real apps.
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//@formatter:off
		clients
			.inMemory()
				.withClient(CLIENT_ID)
				.secret(CLIENT_SECRET)
				.authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials", "refresh_token")
				.scopes("read")
				.accessTokenValiditySeconds(86400); // 24 hours
		//@formatter:on
	}

	// A token store bean. In-memory token store
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
}
