package com.users.service;

import com.users.entities.User;
import com.users.entities.UserRole;
import com.users.exception.InvalidCredentialsException;
import com.users.exception.UserAlreadyExistsException;
import com.users.exception.UserNotFoundException;
import com.users.indto.UpdateUserRequest;
import com.users.indto.UserLoginRequest;
import com.users.indto.UserRequest;
import com.users.outdto.UpdateUserResponse;
import com.users.outdto.UserResponse;
import com.users.repositories.UserRepository;
import com.users.service.impl.UserServiceImpl;
import com.users.utils.Base64Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  private User user;
  private UserRequest userRequest;
  private UserResponse userResponse;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    user = new User();
    user.setId(1);
    user.setEmail("shruthimohan1708@gmail.com");
    user.setPassword(Base64Utils.encodePassword("Password@123"));
    user.setFirstName("Shruthi");
    user.setLastName("Mohan");
    user.setPhoneNumber("8434972888");
    user.setUserRole(UserRole.CUSTOMER);

    userRequest = new UserRequest();
    userRequest.setFirstName("Shruthi");
    userRequest.setLastName("Mohan");
    userRequest.setEmail("shruthimohan1708@gmail.com");
    userRequest.setPassword("Password@123");
    userRequest.setPhoneNumber("8434972888");
    userRequest.setUserRole(UserRole.CUSTOMER);

    userResponse = new UserResponse();
    userResponse.setId(1);
    userResponse.setFirstName("Shruthi");
    userResponse.setLastName("Mohan");
    userResponse.setEmail("shruthimohan1708@gmail.com");
    userResponse.setPhoneNumber("8434972888");
    userResponse.setUserRole(UserRole.CUSTOMER);
  }

  @Test
  void registerUserSuccessTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponse response = userService.registerUser(userRequest);

    assertEquals(userResponse.getId(), response.getId());
    assertEquals(userResponse.getFirstName(), response.getFirstName());
    assertEquals(userResponse.getLastName(), response.getLastName());
    assertEquals(userResponse.getEmail(), response.getEmail());
    assertEquals(userResponse.getPhoneNumber(), response.getPhoneNumber());
    assertEquals(userResponse.getUserRole(), response.getUserRole());
  }

  @Test
  void registerUserUserAlreadyExistsTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    assertThrows(UserAlreadyExistsException.class, () -> {
      userService.registerUser(userRequest);
    });
  }

  @Test
  void loginUserSuccessTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    UserResponse response = userService.loginUser(new UserLoginRequest("shruthimohan1708@gmail.com", "Password@123"));

    assertEquals(userResponse.getId(), response.getId());
    assertEquals(userResponse.getFirstName(), response.getFirstName());
    assertEquals(userResponse.getLastName(), response.getLastName());
    assertEquals(userResponse.getEmail(), response.getEmail());
    assertEquals(userResponse.getPhoneNumber(), response.getPhoneNumber());
    assertEquals(userResponse.getUserRole(), response.getUserRole());
  }

  @Test
  void loginUserInvalidCredentialsTest() {
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

    assertThrows(InvalidCredentialsException.class, () -> {
      userService.loginUser(new UserLoginRequest("shruthimohan1708@gmail.com", "WrongPassword"));
    });
  }

  @Test
  void getAllUsersSuccessTest() {
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    List<User> users = userService.getAllUsers();

    assertNotNull(users);
    assertFalse(users.isEmpty());
    assertEquals(1, users.size());
    assertEquals(user.getId(), users.get(0).getId());
  }

  @Test
  void findUserByIdSuccessTests() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    User foundUser = userService.findUserById(1);

    assertEquals(user.getId(), foundUser.getId());
    assertEquals(user.getEmail(), foundUser.getEmail());
  }

  @Test
  void findUserByIdUserNotFoundTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> {
      userService.findUserById(1);
    });
  }

  @Test
  void updateUserSuccessTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setFirstName("Shruthi");
    updateUserRequest.setLastName("Mohan");
    updateUserRequest.setPhoneNumber("8434972888");

    UpdateUserResponse updateUserResponse = new UpdateUserResponse();
    updateUserResponse.setId(1);
    updateUserResponse.setFirstName("Shruthi");
    updateUserResponse.setLastName("Mohan");
    updateUserResponse.setPhoneNumber("8434972888");

    UpdateUserResponse response = userService.updateUser(1, updateUserRequest);

    assertEquals(updateUserResponse.getId(), response.getId());
    assertEquals(updateUserResponse.getFirstName(), response.getFirstName());
    assertEquals(updateUserResponse.getLastName(), response.getLastName());
    assertEquals(updateUserResponse.getPhoneNumber(), response.getPhoneNumber());
  }

  @Test
  void deleteUserSuccessTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    userService.deleteUser(1);

    verify(userRepository, times(1)).delete(user);
  }

  @Test
  void deleteUserUserNotFoundTest() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> {
      userService.deleteUser(1);
    });
  }
}
