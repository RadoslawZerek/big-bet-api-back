package com.radoslawzerek.bigbetbackend.dto;


import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDataChangeDto {

    private Long id;
    private Long userId;
    private ChangedValue changedValue;
    private String oldValue;
    private String newValue;
    private LocalDateTime changeTime;
}
