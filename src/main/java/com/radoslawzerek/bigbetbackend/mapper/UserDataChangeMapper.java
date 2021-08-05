package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.UserDataChangeDto;
import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDataChangeMapper {

    public UserDataChangeDto mapToUserDataChangeDto(UserDataChange userDataChange) {
        return new UserDataChangeDto(userDataChange.getId(), userDataChange.getUser().getId(),
                userDataChange.getChangedValue(), userDataChange.getOldValue(),
                userDataChange.getNewValue(), userDataChange.getChangeTime());
    }

    public List<UserDataChangeDto> mapToUserDataChangeDtoList(List<UserDataChange> userDataChangeList) {
        return userDataChangeList.stream().map(this::mapToUserDataChangeDto)
                .collect(Collectors.toList());
    }
}
