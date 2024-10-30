package security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import model.User;

//Interface fornece métodos para recuperar informações sobre o usuário, que o Spring Security usará para autenticação e autorização.
public class UserAuthenticated implements UserDetails{
	
	private final User user;

	public UserAuthenticated(User user) {
	    this.user = user;
	  }

	//Este método retorna as autoridades (ou permissões) do usuário, que determinam o que ele pode fazer na aplicação.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Cria uma lista com uma autoridade única chamada "read".
		return List.of(() -> "read");
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	

}
