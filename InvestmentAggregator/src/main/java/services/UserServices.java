package services;

import java.lang.foreign.Linker.Option;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import controller.CreateUserDto;
import controller.UpdateUserDto;
import entity.Users;
import repository.UserRepositorys;


public class UserServices {

	private UserRepositorys userRepository;

	public UserServices(UserRepositorys userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public UUID CreateUser(CreateUserDto createDto) {
		
		var userEntity = new Users(
				UUID.randomUUID(),
				createDto.username(),
				createDto.email(),
				createDto.password(),
				Instant.now(),
				null);
		
		var saveUser = userRepository.save(userEntity);
		
		return saveUser.getUserID();
	}
	
	public void UpdateUser(String id, UpdateUserDto updateDto ) {
		
		var user = userRepository.findById(UUID.fromString(id));
		
		if(user.isPresent()) {
			var entityUser = user.get();
			if(updateDto.username() != null) {
				entityUser.setUserName(updateDto.username());
			}
			if(updateDto.password() != null) {
				entityUser.setPassword(updateDto.password());
			}
			
			userRepository.save(entityUser);
		}
		
	}
	public Optional<Users> GetById(String id){
	
		return userRepository.findById(UUID.fromString(id));
	}
	
	public List<Users> GetAllUsers(){
		
		return userRepository.findAll();
	}
	
	public void DeletedUser(String id) {
		var idUser = UUID.fromString(id);
		var user = userRepository.findById(idUser);
		
		userRepository.deleteById(idUser);
	}
	
}
	 

