package controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import entity.User;
import services.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<CreateUserDto> createUser(@RequestBody CreateUserDto createUserDto){
		var userId = userService.createUser(createUserDto);
		
		return ResponseEntity.created(URI.create("/v1/users" + userId.toString())).build();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
		var user = userService.getUserById(userId);
		
		if(user.isPresent()){
			return ResponseEntity.ok(user.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers(){
		var users = userService.listUsers();
		
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto userUpdateDto){
		userService.updateUserById(userId, userUpdateDto);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
		userService.deleteById(userId);
		
		return ResponseEntity.noContent().build();
	}

}
