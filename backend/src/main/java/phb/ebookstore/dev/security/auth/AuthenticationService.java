package phb.ebookstore.dev.security.auth;

import phb.ebookstore.dev.entity.Token;
import phb.ebookstore.dev.entity.TokenType;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.Cart;
import phb.ebookstore.dev.repository.CartRepository;
import phb.ebookstore.dev.repository.TokenRepository;
import phb.ebookstore.dev.repository.UserRepository;
import phb.ebookstore.dev.security.config.JwtService;
import phb.ebookstore.dev.security.dto.AuthenticationRequest;
import phb.ebookstore.dev.security.dto.AuthenticationResponse;
import phb.ebookstore.dev.security.dto.RegisterRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
	  var user = User.builder()
  	        .firstName(request.getFirstname())
  	        .lastName(request.getLastname())
  	        .address(request.getAddress())
  	        .phoneNumber(request.getPhoneNumber())
  	        .email(request.getEmail())
  	        .pass_word(passwordEncoder.encode(request.getPassword()))
  	        .role(request.getRole())
  	        .createdAt(new Date())
  	        .build();
  	    var savedUser = userRepository.save(user);
  	    var cart = Cart.builder()
  	    		.user(user)
  	    		.build();
  	    cartRepository.save(cart);
  	    var jwtToken = jwtService.generateToken(user);
  	    var refreshToken = jwtService.generateRefreshToken(user);
  	    saveUserToken(savedUser, jwtToken);
  	    return AuthenticationResponse.builder()
  	        .accessToken(jwtToken)
  	            .refreshToken(refreshToken)
  	        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .role(user.getRole())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
  ////
  public Optional<User> findByEmail(String email) {
	return userRepository.findByEmail(email);
	
}
  
  public User getCurrentAuthenticatedIOKIUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String currentPrincipalName = authentication.getName();
		 return findByEmail(currentPrincipalName).get();

  }
  
  @Transactional
  public void logout(String token) {
      tokenRepository.findByToken(token).ifPresent(storedToken -> {
          storedToken.setRevoked(true);
          storedToken.setExpired(true);
          tokenRepository.save(storedToken);
      });
  }
}