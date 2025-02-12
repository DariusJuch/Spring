package lt.techin.Movies_studio_2.security;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            .csrf(c -> c.disable())
//            .httpBasic(Customizer.withDefaults())
//            .authorizeHttpRequests(authorize -> authorize
//                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/movies").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/api/movies/{id}").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/api/actors").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/movies").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/movies").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/movies/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/actors").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//            );
//    return http.build();
//  }

  @Value("${jwt.public.key}")
  private RSAPublicKey key;

  @Value("${jwt.private.key}")
  private RSAPrivateKey priv;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/movies").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/movies/{id}").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/actors").hasAuthority("SCOPE_ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/movies").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/movies").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/movies/{id}").hasAuthority("SCOPE_ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/actors").hasAuthority("SCOPE_ROLE_ADMIN")
                    .anyRequest().authenticated()
            )
            .csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer((o -> o.jwt(Customizer.withDefaults())))
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling((exception) -> exception
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(key).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(key).privateKey(priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

}
