package com.radoslawzerek.bigbetbackend.entity;

import com.radoslawzerek.bigbetbackend.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@NamedNativeQuery(
        name = "DeletedBet.getDeletedBetsOfUser",
        query = "SELECT * FROM DELETED_BETS " +
                "WHERE USER_ID = :USER_ID " +
                "ORDER BY DELETE_TIME DESC",
        resultClass = DeletedBet.class
)

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "DELETED_BETS")
public class DeletedBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "HOME_TEAM")
    private String homeTeam;

    @Column(name = "AWAY_TEAM")
    private String awayTeam;

    @Column(name = "COMMENCE_TIME")
    private ZonedDateTime commence_time;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPPED_WINNER")
    private Winner tippedWinner;

    @Column(name = "ODD")
    private BigDecimal odd;

    @Column(name = "STAKE")
    private BigDecimal stake;

    @Column(name = "DELETE_TIME")
    private LocalDateTime deleteTime;

    public DeletedBet(Bet bet) {
        this.user = bet.getUser();
        this.homeTeam = bet.getBetProspect().getTeams().get(0);
        this.awayTeam = bet.getBetProspect().getTeams().get(1);
        this.commence_time = bet.getBetProspect().getCommence_time();
        this.tippedWinner = bet.getTippedWinner();
        this.odd = bet.getOdd();
        this.stake = bet.getStake();
        this.deleteTime = LocalDateTime.now();
    }
}
