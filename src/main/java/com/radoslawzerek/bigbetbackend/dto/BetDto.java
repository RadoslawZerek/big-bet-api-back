package com.radoslawzerek.bigbetbackend.dto;

import com.radoslawzerek.bigbetbackend.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BetDto {

    private Long id;
    private UserDto user;
    private BetProspectDto betProspect;
    private LocalDateTime created;
    private Winner tippedWinner;
    private BigDecimal odd;
    private BigDecimal stake;
    private boolean finalized;
    private Winner winner;
    private boolean won;
    private BigDecimal cashWin;

}

