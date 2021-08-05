package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.UserBalanceChangeDto;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserBalanceChangeMapper {

    public UserBalanceChangeDto mapToUserBalanceChangeDto(UserBalanceChange userBalanceChange) {
        return new UserBalanceChangeDto(userBalanceChange.getId(), userBalanceChange.getUser().getId(),
                userBalanceChange.getOldValue(), userBalanceChange.getNewValue(), userBalanceChange.getChangeTime());
    }

    public List<UserBalanceChangeDto> mapToUserBalanceChangeDtoList(List<UserBalanceChange> userBalanceChangeList) {
        return userBalanceChangeList.stream().map(this::mapToUserBalanceChangeDto)
                .collect(Collectors.toList());
    }
}
