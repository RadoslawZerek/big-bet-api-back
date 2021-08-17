package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.mapper.BetProspectsRequestMapper;
import com.radoslawzerek.bigbetbackend.service.BetProspectsRequestService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BetProspectsRequestController.class)
@ExtendWith(SpringExtension.class)
public class BetProspectsRequestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BetProspectsRequestService service;

    @MockBean
    private BetProspectsRequestMapper mapper;


    @Test
    public void testGetAllBetProspectsRequests() throws Exception {

        //Given
        List<String> leagues = new ArrayList<>();
        leagues.add("La Liga - Spain");

        BetProspectsRequestDto request1 = new BetProspectsRequestDto(1L, 1L, leagues, LocalDateTime.now());
        BetProspectsRequestDto request2 = new BetProspectsRequestDto(2L, 1L, leagues, LocalDateTime.now());

        List<BetProspectsRequestDto> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);

        when(service.getAllBetProspectsRequests()).thenReturn(new ArrayList<>());
        when(mapper.mapToBetProspectsRequestDtoList(anyList())).thenReturn(requests);

        //Then & When

        mockMvc.perform(get("/v1/requests").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(request1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(request2.getId()), Long.class));

    }

    @Test
    public void testGetBetProspectsRequestsOfUser() throws Exception {
        //Given
        List<String> leagues = new ArrayList<>();
        leagues.add("La Liga -Spain");

        BetProspectsRequestDto request1 = new BetProspectsRequestDto(1L, 1L, leagues, LocalDateTime.now());
        BetProspectsRequestDto request2 = new BetProspectsRequestDto(2L, 1L, leagues, LocalDateTime.now());

        List<BetProspectsRequestDto> list = new ArrayList<>();
        list.add(request1);
        list.add(request2);

        when(service.getBetProspectsRequestsOfUser(1L)).thenReturn(new ArrayList<>());
        when(mapper.mapToBetProspectsRequestDtoList(anyList())).thenReturn(list);

        //Then & When

        mockMvc.perform(get("/v1/betprospectsrequests/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(request1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(request2.getId()), Long.class));
    }
}