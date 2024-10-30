package security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	//Será usada para gerar tokens JWT
	private JwtService jwtService;
	
	//método é responsável por autenticar um usuário.
	//Authentication como parâmetro, que geralmente contém as credenciais do usuário,
	//(como nome de usuário e senha) e as autoridades (permissões).
	public String authenticate(Authentication authentication) {
		//pega as informações de autenticação e cria um token JWT correspondente.
		return jwtService.generateToken(authentication);
	}
	
}
