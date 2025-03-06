package com.Yansb.homevet.infrastructure.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import com.google.firebase.auth.FirebaseToken;
import com.nimbusds.jwt.JWTClaimNames;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers(HttpMethod.POST, "/user").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth -> oauth.jwt(
            jwt -> jwt.jwtAuthenticationConverter(new FirebaseJwtConverter())))
        .build();
  }

  static class FirebaseJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    public FirebaseJwtConverter() {
      super();
    }

    @Override
    public AbstractAuthenticationToken convert(final Jwt jwt) {
      return new JwtAuthenticationToken(jwt, getAuthoritiesFromToken(jwt), extractPrincipal(jwt));
    }

    private String extractPrincipal(final Jwt jwt) {
      return jwt.getClaimAsString(JWTClaimNames.SUBJECT);
    }

    private static Collection<? extends GrantedAuthority> getAuthoritiesFromToken(final Jwt jwt) {
      Object claims = jwt.getClaims().get("authorities");

      List<String> permissions = (List<String>) claims;
      List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;
      if (permissions != null && !permissions.isEmpty()) {
        authorities = AuthorityUtils.createAuthorityList(permissions);
      }
      return authorities;
    }

  }

}
