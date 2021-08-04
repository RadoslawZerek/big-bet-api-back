package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.BetDto;
import com.radoslawzerek.bigbetbackend.entity.Bet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BetMapper {

    private final BetProspectMapper betProspectMapper;
    private final UserMapper userMapper;

    @Autowired
    public BetMapper(BetProspectMapper betProspectMapper, UserMapper userMapper) {
        this.betProspectMapper = betProspectMapper;
        this.userMapper = userMapper;
    }

    public Bet mapToBet(BetDto betDto) {
        return new Bet(betDto.getId(), userMapper.mapToUser(betDto.getUser()),
                betProspectMapper.mapToBetProspect(betDto.getBetProspect()), betDto.getCreated(),
                betDto.getTippedWinner(), betDto.getOdd(), betDto.getStake(), betDto.isFinalized(), betDto.getWinner(),
                betDto.isWon(), betDto.getCashWin());
    }

    public BetDto mapToBetDto(Bet bet) {
        return new BetDto(bet.getId(), userMapper.mapToUserDto(bet.getUser()),
                betProspectMapper.mapToBetProspectDto(bet.getBetProspect()), bet.getCreated(),
                bet.getTippedWinner(), bet.getOdd(), bet.getStake(), bet.isFinalized(), bet.getWinner(),
                bet.isWon(), bet.getCashWin());
    }

    public List<BetDto> mapToBetDtoList(List<Bet> betList) {
        return betList.stream()
                .map(this::mapToBetDto)
                .collect(Collectors.toList());
    }
}
