package com.radoslawzerek.bigbetbackend.dto;

import com.radoslawzerek.bigbetbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private LocalDate created;
    private BigDecimal balance;
}
