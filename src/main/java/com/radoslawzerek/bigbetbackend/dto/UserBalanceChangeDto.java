package com.radoslawzerek.bigbetbackend.dto;

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

public class UserBalanceChangeDto {

    private Long id;
    private Long userId;
    private BigDecimal oldValue;
    private BigDecimal newValue;
    private LocalDateTime changeTime;
}
