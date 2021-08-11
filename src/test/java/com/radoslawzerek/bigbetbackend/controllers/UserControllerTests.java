package com.radoslawzerek.bigbetbackend.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radoslawzerek.bigbetbackend.date.LocalDateAdapter;
import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.mapper.UserMapper;
import com.radoslawzerek.bigbetbackend.pojo.LogInFeedback;
import com.radoslawzerek.bigbetbackend.pojo.SignUpFeedback;
import com.radoslawzerek.bigbetbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService service;

    @MockBean
    UserMapper mapper;

    @Test
    public void testLogUserIn() throws Exception {
        //Given
        String login = "Test_user1";
        String password = "test_password";
        String message = "Log in successful";
        UserDto user = new UserDto(1L, login, "Test1@test.com", password,
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));

        LogInFeedback logInFeedback = new LogInFeedback(user, message);

        when(service.logUserIn(login, password)).thenReturn(logInFeedback);

        //When&Then

        mockMvc.perform(get("/v1/bigbet/user/" + login + "/" + password).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id", is(user.getId()), Long.class))
                .andExpect(jsonPath("$.message", is(message)));
    }

    @Test
    public void testSignUserUp() throws Exception {
        //Given
        String message = "Sign in successful";
        UserDto userDto = new UserDto(1L, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));
        User user = new User(1L, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));

        SignUpFeedback signInFeedback = new SignUpFeedback(userDto, message);

        when(mapper.mapToUser(any(UserDto.class))).thenReturn(user);
        when(service.signUserUp(user)).thenReturn(signInFeedback);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(post("/v1/bigbet/user").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.user.id", is(user.getId()), Long.class))
                .andExpect(jsonPath("$.message", is(message)));
    }

    @Test
    public void testGetUserById() throws Exception {

        //Given
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));
        User user = new User(userId, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));

        when(service.getUserById(userId)).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/bigbet/user/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())));
    }

    @Test
    public void testGetAllUsers() throws Exception {

        //Given
        UserDto userDto1 = new UserDto(1L, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));
        UserDto userDto2 = new UserDto(2L, "Test_user2", "Test2@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto2);

        when(service.getAllUsers()).thenReturn(new ArrayList<>());
        when(mapper.mapToUserDtoList(anyList())).thenReturn(userDtoList);

        //When & Then
        mockMvc.perform(get("/v1/bigbet/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userList.[0].id", is(userDto1.getId()), Long.class))
                .andExpect(jsonPath("$.userList.[0].username", is(userDto1.getUsername())))
                .andExpect(jsonPath("$.userList.[1].id", is(userDto2.getId()), Long.class))
                .andExpect(jsonPath("$.userList.[1].username", is(userDto2.getUsername())));

    }

    @Test
    public void testUpdateUserPassword() throws Exception {
        //Given
        Long userId = 1L;
        String newPassword = "NewPassword";
        UserDto userDto = new UserDto(userId, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));
        User user = new User(userId, "Test_user1", "Test1@test.com", "test_password",
                Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));

        when(service.updateUserPassword(userId, newPassword)).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(patch("/v1/bigbet/user/" + userId).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(newPassword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())));

    }

    @Test
    public void testCheckIfUserExists() throws Exception {
        //Given

        String login = "login";
        when(service.checkIfUserExists(login)).thenReturn(true);

        //When & Then
        mockMvc.perform(get("/v1/bigbet/user/check/" + login).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //Given
        Long userId = 1L;

        //When & Then
        mockMvc.perform(delete("/v1/bigbet/user/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteUser(userId);
    }
}