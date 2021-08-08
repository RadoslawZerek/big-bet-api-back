package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.LogInAttemptDto;
import com.radoslawzerek.bigbetbackend.mapper.LogInAttemptMapper;
import com.radoslawzerek.bigbetbackend.service.LogInAttemptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogInAttemptController.class)
@ExtendWith(SpringExtension.class)
public class LogInAttemptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LogInAttemptService service;

    @MockBean
    LogInAttemptMapper mapper;

    @Test
    public void testGetAllLogInAttempts() throws Exception {
        //Given
        LogInAttemptDto attempt1 = new LogInAttemptDto(1L, 1L, "login1", false, LocalDateTime.now());
        LogInAttemptDto attempt2 = new LogInAttemptDto(2L, 1L, "login2", true, LocalDateTime.now());

        List<LogInAttemptDto> attempts = new ArrayList<>();
        attempts.add(attempt1);
        attempts.add(attempt2);

        when(service.getAllLogInAttempts()).thenReturn(new ArrayList<>());
        when(mapper.mapToLogInAttemptDtoList(anyList())).thenReturn(attempts);

        //Then & When

        mockMvc.perform(get("/v1/bigbet/login_attampts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(attempt1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(attempt2.getId()), Long.class));
    }

    @Test
    public void testGetLogInAttemptsOfUser() throws Exception {
        //Given
        LogInAttemptDto attempt1 = new LogInAttemptDto(1L, 1L, "login1", false, LocalDateTime.now());
        LogInAttemptDto attempt2 = new LogInAttemptDto(2L, 1L, "login2", true, LocalDateTime.now());

        List<LogInAttemptDto> attempts = new ArrayList<>();
        attempts.add(attempt1);
        attempts.add(attempt2);

        when(service.getAllLogInAttemptsOfUser(1L)).thenReturn(new ArrayList<>());
        when(mapper.mapToLogInAttemptDtoList(anyList())).thenReturn(attempts);

        //Then & When

        mockMvc.perform(get("/v1/bigbet/login_attampts/1/false").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(attempt1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(attempt2.getId()), Long.class));
    }
}