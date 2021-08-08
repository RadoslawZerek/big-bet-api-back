package com.radoslawzerek.bigbetbackend.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.facade.BetProspectFacade;
import com.radoslawzerek.bigbetbackend.date.LocalDateTimeAdapter;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.service.BetProspectsRequestService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BetProspectController.class)
@ExtendWith(SpringExtension.class)
public class BetProspectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BetProspectFacade prospectFacade;

    @MockBean
    private BetProspectsRequestService prospectsRequestService;

    @Test
    public void getCurrentBetProspects() throws Exception {
        //Given
        Integer sport_key1 = 2014;
        List<String> teams1 = new ArrayList<>();
        teams1.add("Valencia");
        teams1.add("Athletic Bilbao");
        ZonedDateTime commence_time1 = ZonedDateTime.parse("2020-12-20T13:00:00Z");
        List<BigDecimal> h2h1 = new ArrayList<>();
        h2h1.add(new BigDecimal("3.56"));
        h2h1.add(new BigDecimal("3.94"));
        h2h1.add(new BigDecimal("4.18"));

        BetProspectDto betProspect1 = new BetProspectDto(1L, sport_key1, teams1, commence_time1, h2h1);

        Integer sport_key2 = 2014;
        List<String> teams2 = new ArrayList<>();
        teams2.add("Getafe");
        teams2.add("Sevilla");
        ZonedDateTime commence_time2 = ZonedDateTime.parse("2020-12-12T15:15:00Z");
        List<BigDecimal> h2h_2 = new ArrayList<>();
        h2h_2.add(new BigDecimal("4.32"));
        h2h_2.add(new BigDecimal("2.35"));
        h2h_2.add(new BigDecimal("2.79"));

        BetProspectDto betProspect2 = new BetProspectDto(1L, sport_key2, teams2, commence_time2, h2h_2);

        List<BetProspectDto> list = new ArrayList<>();
        list.add(betProspect1);
        list.add(betProspect2);

        List<String> leagues = new ArrayList<>();
        leagues.add("La Liga -Spain");

        BetProspectsRequestDto requestDto = new BetProspectsRequestDto(1L, 1L, leagues, LocalDateTime.now());

        when(prospectFacade.getCurrentBetProspectDtoList(any(BetProspectsRequestDto.class))).thenReturn(list);


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        String jsonContent = gson.toJson(requestDto);

        //When & Then
        mockMvc.perform(post("/v1/bigbet/prospects").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].id", is(betProspect1.getId()), Long.class))
                .andExpect(jsonPath("$.list[1].id", is(betProspect2.getId()), Long.class));
    }
}