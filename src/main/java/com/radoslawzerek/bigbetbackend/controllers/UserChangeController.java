package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.UserBalanceChangeDto;
import com.radoslawzerek.bigbetbackend.dto.UserDataChangeDto;
import com.radoslawzerek.bigbetbackend.mapper.UserBalanceChangeMapper;
import com.radoslawzerek.bigbetbackend.mapper.UserDataChangeMapper;
import com.radoslawzerek.bigbetbackend.service.UserChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class UserChangeController {

    @Autowired
    private final UserChangeService service;

    @Autowired
    private final UserDataChangeMapper dataChangeMapper;

    @Autowired
    private final UserBalanceChangeMapper balanceChangeMapper;

    @GetMapping("/user_data_changes")
    public List<UserDataChangeDto> getAllUsersDataChanges() {
        return dataChangeMapper.mapToUserDataChangeDtoList(service.getAllUsersDataChanges());
    }

    @GetMapping("/user_data_changes/{userId}")
    public List<UserDataChangeDto> getDataChangesOfUser(@PathVariable Long userId) {
        return dataChangeMapper.mapToUserDataChangeDtoList(service.getDataChangesOfUser(userId));
    }

    @GetMapping("/user_balance_changes")
    public List<UserBalanceChangeDto> getAllUsersBalanceChanges() {
        return balanceChangeMapper.mapToUserBalanceChangeDtoList(service.getAllUsersBalanceChanges());
    }

    @GetMapping("/user_balance_changes/{userId}")
    public List<UserBalanceChangeDto> getBalanceChangesOfUser(@PathVariable Long userId) {
        return balanceChangeMapper.mapToUserBalanceChangeDtoList(service.getBalanceChangesOfUser(userId));
    }
}
