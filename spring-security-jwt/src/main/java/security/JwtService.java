package security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	//JwtEncoder, ferramenta responsável por criar (ou codificar) um token JWT.
	private final JwtEncoder encoder;

	public JwtService(JwtEncoder encoder) {
		super();
		this.encoder = encoder;
	}
	
	//Método que gera um token JWT para um usuário autenticado.
	public String generateToken(Authentication authentication) {
		//Armazena o instante atual
		Instant now = Instant.now();
		//Define um tempo de expiração de 36000 segundos (10 horas).
		long expiry = 36000L;
		
		//Extração das Permissões do Usuário.
		String scope = 
				//Obtém as permissões (ou "authorities") do usuário.
					authentication
				.getAuthorities()
				//Cria um fluxo dos authorities.
				.stream()
				//Extrai o nome de cada permission.
				.map(GrantedAuthority::getAuthority)
				//Junta todas as permissões em uma única String, separadas por um espaço.
				.collect(Collectors
						.joining(" "));
		
		//O JwtClaimsSet é uma classe do Spring Security que representa o conjunto de claims (ou declarações) contidas em um token JWT. 
		JwtClaimsSet claims = JwtClaimsSet.builder()
				//Define o emissor do token.
				.issuer("spring-security-jwt")
				//Atribui a hora de emissão ao token.
				.issuedAt(now)
				//Define a data de expiração.
				.expiresAt(now.plusSeconds(expiry))
				//Define o sujeito do token como o nome de usuário.
				.subject(authentication.getName())
				//Define uma declaração personalizada chamada "scope" com as permissões do usuário.
				.claim("scope", scope)
				.build();
		
		//Codificação do Token
		return encoder.encode(
				//Converte o conjunto de claims em um token JWT usando o encoder.
				JwtEncoderParameters.from(claims))
				//Retorna o valor do token gerado como uma String.
				.getTokenValue();
		
	}
	
	
}
