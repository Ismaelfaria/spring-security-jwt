package config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

//Indica que é uma classe que configura beans (componentes gerenciados pelo Spring)
@Configuration
//Habilita o Spring Security na aplicação
@EnableWebSecurity

public class SecurityConfig {

	  //Injeção de valor. Aqui, o Spring irá buscar o valor da chave pública do JWT no application.properties
	  @Value("${jwt.public.key}")
	  //Representa a chave pública para verificar assinaturas JWT.
	  private RSAPublicKey key;
	  @Value("${jwt.private.key}")
	  //Representa a chave privada para gerar assinaturas JWT.
	  private RSAPrivateKey priv;
	  
	  //Configura a cadeia de filtros de segurança da aplicação e define quais endpoints
	  //requerem autenticação e quais estão abertos ao público.
	  
	  //Define um bean gerenciado pelo Spring.
	  @Bean
	  //Configura a cadeia de filtros de segurança (SecurityFilterChain), 
	  //usando o objeto HttpSecurity para definir as políticas de segurança da aplicação.
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//Desativa a proteção CSRF (Cross-Site Request Forgery).
	    http.csrf(csrf -> csrf.disable())
	    	//Configura as políticas de autorização de requisições HTTP.
	        .authorizeHttpRequests(
	        		//Permite o acesso irrestrito ao endpoint /authenticate
	        		auth -> auth
	                	.requestMatchers("/authenticate").permitAll()
	                	//Exige que todas as outras requisições estejam autenticadas.
	                	.anyRequest().authenticated())
	        //Habilita a autenticação HTTP Basic com a configuração padrão do Spring Security.
	        .httpBasic(Customizer.withDefaults())
	        //Configura a aplicação como um servidor de recursos compatível com OAuth2.
	        .oauth2ResourceServer(
	        		//Define que o servidor de recursos OAuth2 usará autenticação baseada em JWT.
	            conf -> conf.jwt(
	                jwt -> jwt.decoder(jwtDecoder())));
	    //Constroi e retorna a cadeia de filtros configurada.
	    return http.build();
	  }

	  //Cria e retorna um codificador de senhas (PasswordEncoder).
	  @Bean
	  //Cria um codificador de senhas BCryptPasswordEncoder
	  PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

	  //Cria e retorna um JwtDecoder, que é usado para validar tokens JWT recebidos.
	  @Bean
	  //Decodificar os tokens JWT recebidos.
	  JwtDecoder jwtDecoder() {
	    return NimbusJwtDecoder.withPublicKey(this.key).build();
	  }

	  //Cria e retorna um JwtEncoder, que é responsável por assinar e emitir novos tokens JWT.
	  @Bean
	  JwtEncoder jwtEncoder() {
		 //Cria uma instância JWK (JSON Web Key) que contém tanto a chave pública quanto a privada.
	    JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
	    //Cria uma fonte de JWKs imutável, que armazena o JWK criado.
	    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
	    //Retorna um JwtEncoder que usará a JWK para assinar os tokens JWT.
	    return new NimbusJwtEncoder(jwks);
	  }
	
}
