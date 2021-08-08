package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.DeletedBetDto;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import com.radoslawzerek.bigbetbackend.mapper.DeletedBetMapper;
import com.radoslawzerek.bigbetbackend.service.DeletedBetService;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeletedBetController.class)
@ExtendWith(SpringExtension.class)
public class DeletedBetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DeletedBetService service;

    @MockBean
    DeletedBetMapper mapper;

    @Test
    public void testGetAllDeletedBets() throws Exception {
        //Given
        DeletedBetDto deletedBet1 = new DeletedBetDto(1L, 1L, "Barcelona", "Real Madrid",
                ZonedDateTime.parse("2020-12-13T16:30:50Z"), Winner.HOME_TEAM, new BigDecimal("2"),
                new BigDecimal("20"), LocalDateTime.parse("2020-12-09T20:00:50"));

        DeletedBetDto deletedBet2 = new DeletedBetDto(2L, 1L, "Valencia", "Getafe",
                ZonedDateTime.parse("2020-12-14T16:30:50Z"), Winner.HOME_TEAM, new BigDecimal("2"),
                new BigDecimal("20"), LocalDateTime.parse("2020-12-10T20:00:50"));

        List<DeletedBetDto> deletedBetDtos = new ArrayList<>();
        deletedBetDtos.add(deletedBet1);
        deletedBetDtos.add(deletedBet2);

        when(service.getAllDeletedBets()).thenReturn(new ArrayList<>());
        when(mapper.mapToDeletedBetDtoList(anyList())).thenReturn(deletedBetDtos);

        //Then & When

        mockMvc.perform(get("/v1/bigbet/deleted_bets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(deletedBet1.getId()), Long.class))
                .andExpect(jsonPath("$[0].id", is(deletedBet1.getId()), Long.class));
    }

    @Test
    public void testGetAllDeletedBetsOfUser() throws Exception {
        //Given
        DeletedBetDto deletedBet1 = new DeletedBetDto(1L, 1L, "Barcelona", "Real Madrid",
                ZonedDateTime.parse("2020-12-13T16:30:50Z"), Winner.HOME_TEAM, new BigDecimal("2"),
                new BigDecimal("20"), LocalDateTime.parse("2020-12-09T20:00:50"));

        DeletedBetDto deletedBet2 = new DeletedBetDto(2L, 1L, "Valencia", "Getafe",
                ZonedDateTime.parse("2020-12-14T16:30:50Z"), Winner.HOME_TEAM, new BigDecimal("2"),
                new BigDecimal("20"), LocalDateTime.parse("2020-12-10T20:00:50"));

        List<DeletedBetDto> deletedBetDtos = new ArrayList<>();
        deletedBetDtos.add(deletedBet1);
        deletedBetDtos.add(deletedBet2);

        when(service.getDeletedBetsOfUser(1L)).thenReturn(new ArrayList<>());
        when(mapper.mapToDeletedBetDtoList(anyList())).thenReturn(deletedBetDtos);

        //Then & When

        mockMvc.perform(get("/v1/bigbet/deleted_bets/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(deletedBet1.getId()), Long.class))
                .andExpect(jsonPath("$[0].id", is(deletedBet1.getId()), Long.class));
    }
}