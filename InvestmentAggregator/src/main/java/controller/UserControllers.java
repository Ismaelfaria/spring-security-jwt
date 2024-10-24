package controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Users;
import services.UserServices;

@RestController
@RequestMapping("/v1/users")
public class UserControllers {

	private UserServices userService;

	public UserControllers(UserServices userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<CreateUserDto> CreateUser(@RequestBody CreateUserDto userDto){
		
		var saveEntity = userService.CreateUser(userDto);
		
		return ResponseEntity.created(URI.create("/v1/users" + saveEntity.toString())).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Users> UpdateUser(@PathVariable("id") String id,@RequestBody UpdateUserDto userDto){
		
	   userService.UpdateUser(id, userDto);
	   
	   return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> DeleteUser(@PathVariable("id") String id){
		userService.DeletedUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Users> GetUserById(@PathVariable("id") String id){
		var user = userService.GetById(id);
		
		if(user.isPresent()) {
			
			return ResponseEntity.ok(user.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Users>> GetAllUsers(){
		var users = userService.GetAllUsers();
		
		return ResponseEntity.ok(users);
	}
	
}
