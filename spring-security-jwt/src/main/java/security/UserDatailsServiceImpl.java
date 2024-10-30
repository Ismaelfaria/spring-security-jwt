package security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import repository.UserRepository;

@Service
public class UserDatailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserDatailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUsername(username)
				.map(user -> new UserAuthenticated(user))
				.orElseThrow(
						() -> new UsernameNotFoundException("User Not Found with username: " + username));
				
	}
	

}
