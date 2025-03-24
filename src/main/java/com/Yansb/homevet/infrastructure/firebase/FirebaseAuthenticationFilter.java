package com.Yansb.homevet.infrastructure.firebase;

import com.Yansb.homevet.infrastructure.entities.UserRole;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String idToken = request.getHeader("Authorization");
    if (idToken == null || idToken.isEmpty()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization token");
      return;
    }

    try {
      final var token = idToken.replace("Bearer ", "");

      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

      SecurityContextHolder.getContext()
          .setAuthentication(
              new FirebaseAuthenticationToken(idToken, decodedToken,
                  List.of(new SimpleGrantedAuthority(UserRole.USER.name()))));

      SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);

      var principal = SecurityContextHolder.getContext().getAuthentication().getCredentials();

      System.out.println(principal);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase ID-Token");
      return;
    }

    filterChain.doFilter(request, response);

  }

  // private static List<GrantedAuthority> getAuthoritiesFromToken(FirebaseToken
  // token) {
  // Object claims = token.getClaims().get("authorities");
  // List<String> permissions = (List<String>) claims;
  // List<GrantedAuthority> authorities = {UserRole.USER};
  // if (permissions != null && !permissions.isEmpty()) {
  // authorities = AuthorityUtils.createAuthorityList(permissions);
  // }
  // return authorities;
  // }
}
