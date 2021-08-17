package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.UserBalanceChangeDto;
import com.radoslawzerek.bigbetbackend.dto.UserDataChangeDto;
import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
import com.radoslawzerek.bigbetbackend.mapper.UserBalanceChangeMapper;
import com.radoslawzerek.bigbetbackend.mapper.UserDataChangeMapper;
import com.radoslawzerek.bigbetbackend.service.UserChangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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

@WebMvcTest(UserChangeController.class)
@ExtendWith(SpringExtension.class)
public class UserChangeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserChangeService service;

    @MockBean
    UserDataChangeMapper dataChangeMapper;

    @MockBean
    UserBalanceChangeMapper balanceChangeMapper;

    @Test
    public void testGetAllUsersDataChanges() throws Exception {
        //Given
        UserDataChangeDto change1 = new UserDataChangeDto(1L, 1L, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());
        UserDataChangeDto change2 = new UserDataChangeDto(2L, 1L, ChangedValue.EMAIL,
                "Old_email", "New_email", LocalDateTime.now());

        List<UserDataChangeDto> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(service.getAllUsersDataChanges()).thenReturn(new ArrayList<>());
        when(dataChangeMapper.mapToUserDataChangeDtoList(anyList())).thenReturn(changes);

        //Then & When

        mockMvc.perform(get("/v1/user_data_changes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(change1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(change2.getId()), Long.class));
    }

    @Test
    public void testGetDataChangesOfUser() throws Exception {
        //Given
        UserDataChangeDto change1 = new UserDataChangeDto(1L, 1L, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());
        UserDataChangeDto change2 = new UserDataChangeDto(2L, 1L, ChangedValue.PASSWORD,
                "Old_password", "New_password", LocalDateTime.now());

        List<UserDataChangeDto> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(service.getDataChangesOfUser(1L)).thenReturn(new ArrayList<>());
        when(dataChangeMapper.mapToUserDataChangeDtoList(anyList())).thenReturn(changes);

        //Then & When

        mockMvc.perform(get("/v1/user_data_changes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(change1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(change2.getId()), Long.class));
    }

    @Test
    public void testGetAllUsersBalanceChanges() throws Exception {
        //Given
        UserBalanceChangeDto change1 = new UserBalanceChangeDto(1L, 1L,
                new BigDecimal("100"), new BigDecimal("50"), LocalDateTime.now());
        UserBalanceChangeDto change2 = new UserBalanceChangeDto(2L, 1L,
                new BigDecimal("150"), new BigDecimal("200"), LocalDateTime.now());

        List<UserBalanceChangeDto> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(service.getAllUsersBalanceChanges()).thenReturn(new ArrayList<>());
        when(balanceChangeMapper.mapToUserBalanceChangeDtoList(anyList())).thenReturn(changes);

        //Then & When

        mockMvc.perform(get("/v1/user_balance_changes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(change1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(change2.getId()), Long.class));
    }

    @Test
    public void testGetBalanceChangesOfUser() throws Exception {
        //Given
        UserBalanceChangeDto change1 = new UserBalanceChangeDto(1L, 1L,
                new BigDecimal("100"), new BigDecimal("50"), LocalDateTime.now());
        UserBalanceChangeDto change2 = new UserBalanceChangeDto(2L, 1L,
                new BigDecimal("150"), new BigDecimal("200"), LocalDateTime.now());

        List<UserBalanceChangeDto> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(service.getBalanceChangesOfUser(1L)).thenReturn(new ArrayList<>());
        when(balanceChangeMapper.mapToUserBalanceChangeDtoList(anyList())).thenReturn(changes);

        //Then & When

        mockMvc.perform(get("/v1/user_balance_changes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(change1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(change2.getId()), Long.class));
    }
}