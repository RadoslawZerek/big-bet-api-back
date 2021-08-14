package com.radoslawzerek.bigbetbackend.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radoslawzerek.bigbetbackend.dateadapter.LocalDateAdapter;
import com.radoslawzerek.bigbetbackend.dateadapter.LocalDateTimeAdapter;
import com.radoslawzerek.bigbetbackend.dateadapter.ZonedDateTimeAdapter;
import com.radoslawzerek.bigbetbackend.dto.BetDto;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import com.radoslawzerek.bigbetbackend.mapper.BetMapper;
import com.radoslawzerek.bigbetbackend.service.BetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BetController.class)
@ExtendWith(SpringExtension.class)
public class BetControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BetService service;

    @MockBean
    BetMapper mapper;


    @Test
    public void testGetAllBets() throws Exception {
        //Given
        List<BetDto> bets = returnTestBetDtoList();
        BetDto bet1 = bets.get(0);
        BetDto bet2 = bets.get(1);

        when(service.getAllBets()).thenReturn(new ArrayList<>());
        when(mapper.mapToBetDtoList(anyList())).thenReturn(bets);

        //When & Then
        mockMvc.perform(get("/v1/bigbet/allBets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].id", is(bet1.getId()), Long.class))
                .andExpect(jsonPath("$.list[1].id", is(bet2.getId()), Long.class));
    }

    @Test
    public void testGetBetsOfUser() throws Exception {
        //Given
        List<BetDto> bets = returnTestBetDtoList();


        BetDto bet1 = bets.get(0);
        BetDto bet2 = bets.get(1);

        when(service.getAllBetsOfUser(1L)).thenReturn(new ArrayList<>());
        when(mapper.mapToBetDtoList(anyList())).thenReturn(bets);


        //When & Then
        mockMvc.perform(get("/v1/bigbet/bets/1/false").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].id", is(bet1.getId()), Long.class))
                .andExpect(jsonPath("$.list[1].id", is(bet2.getId()), Long.class));
    }

    @Test
    public void testAddBet() throws Exception {
        //Given
        List<BetDto> betDtoList = returnTestBetDtoList();
        BetDto betDto = betDtoList.get(0);

        Bet bet = returnTestBet();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        String jsonContent = gson.toJson(betDto);

        when(mapper.mapToBet(any(BetDto.class))).thenReturn(bet);

        //When & Then
        mockMvc.perform(post("/v1/bigbet/addBet").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(service, times(1)).addBet(bet);

    }

    @Test
    public void testDeleteBet() throws Exception {
        //Given
        Long betId = 1L;

        //When & Then
        mockMvc.perform(delete("/v1/bigbet/" + betId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteBet(betId);
    }

    public List<BetDto> returnTestBetDtoList() {

        UserDto user1 = new UserDto(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.parse("2000-12-20"), new BigDecimal("100"));

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

        BetDto bet1 = new BetDto(1L, user1, betProspect1, LocalDateTime.now(), Winner.HOME_TEAM,
                betProspect1.getH2h().get(0), new BigDecimal("20.00"),
                false, null, false, null);

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

        BetDto bet2 = new BetDto(2L, user1, betProspect2, LocalDateTime.now(), Winner.AWAY_TEAM,
                betProspect2.getH2h().get(0), new BigDecimal("30.00"), true,
                Winner.AWAY_TEAM, true,
                betProspect2.getH2h().get(0).multiply(new BigDecimal("30.00")).setScale(2, RoundingMode.HALF_UP));


        List<BetDto> bets = new ArrayList<>();
        bets.add(bet1);
        bets.add(bet2);

        return bets;
    }

    public Bet returnTestBet() {

        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        Integer sport_key1 = 2014;
        List<String> teams1 = new ArrayList<>();
        teams1.add("Valencia");
        teams1.add("Athletic Bilbao");
        ZonedDateTime commence_time1 = ZonedDateTime.parse("2020-12-20T13:00:00Z");
        List<BigDecimal> h2h1 = new ArrayList<>();
        h2h1.add(new BigDecimal("3.56"));
        h2h1.add(new BigDecimal("3.94"));
        h2h1.add(new BigDecimal("4.18"));

        BetProspect betProspect = new BetProspect(1L, sport_key1, teams1, commence_time1, h2h1);

        return new Bet(1L, user, betProspect, LocalDateTime.now(), Winner.HOME_TEAM,
                betProspect.getH2h().get(0), new BigDecimal("20.00"),
                false, null, false, null);
    }
}