package com.radoslawzerek.bigbetbackend.pojo;

import com.radoslawzerek.bigbetbackend.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpFeedback {
    private UserDto user;
    private String message;
}
