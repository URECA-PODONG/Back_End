package com.ureca.sole_paradise.user.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ureca.sole_paradise.user.config.ReferencedException;
import com.ureca.sole_paradise.user.config.ReferencedWarning;
import com.ureca.sole_paradise.user.db.dto.UserDTO;
import com.ureca.sole_paradise.user.service.UserService;



@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "userId") final Integer userId) {
        try {
            UserDTO user = userService.get(userId);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();  // 사용자 없으면 404 반환
        }
    }

    @PostMapping("/Register")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUser(@RequestBody @Valid final UserDTO userDTO) {
        final Integer createdUserId = userService.create(userDTO);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Integer> updateUser(@PathVariable(name = "userId") final Integer userId,
                                               @RequestBody @Valid final UserDTO userDTO) {
        try {
            userService.update(userId, userDTO);
            return ResponseEntity.ok(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();  // 사용자 없으면 404 반환
        }
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") final Integer userId) throws NotFoundException {
        final ReferencedWarning referencedWarning = userService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
    //@PostMapping("/Register")
   // public ResponseEntity<> Register(@RequestBody UserDTO userDTO) {
    	
    //}
    
}
