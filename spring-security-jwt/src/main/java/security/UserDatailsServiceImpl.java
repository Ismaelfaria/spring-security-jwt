package security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import repository.UserRepository;

//UserDetailsService, que é uma interface do Spring Security usada para carregar dados do usuário durante a autenticação.
@Service
public class UserDatailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserDatailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	
	//Chamado pelo Spring Security quando precisa carregar um usuário para autenticação.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUsername(username)
				//Se o usuário for encontrado, ele é mapeado para uma instância de UserAuthenticated, que implementa UserDetails.
				.map(user -> new UserAuthenticated(user))
				//Se o usuário não for encontrado, lança uma UsernameNotFoundException, indicando que o nome de usuário não existe.
				.orElseThrow(
						() -> new UsernameNotFoundException("User Not Found with username: " + username));
				
	}
	

}
