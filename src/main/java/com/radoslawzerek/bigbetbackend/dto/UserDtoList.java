package com.radoslawzerek.bigbetbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDtoList {
    private List<UserDto> userList;
}
