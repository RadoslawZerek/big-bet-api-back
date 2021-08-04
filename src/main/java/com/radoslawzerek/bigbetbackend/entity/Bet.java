package com.radoslawzerek.bigbetbackend.entity;

import com.radoslawzerek.bigbetbackend.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NamedNativeQuery(
        name = "Bet.getAllBetsOfUser",
        query = "SELECT * FROM BETS " +
                "JOIN BET_PROSPECTS BP ON BETS.BET_PROSPECT_ID = BP.ID " +
                "WHERE USER_ID = :USER_ID " +
                "ORDER BY BP.COMMENCE_TIME DESC",
        resultClass = Bet.class
)

@NamedNativeQuery(
        name = "Bet.getPendingBetsOfUser",
        query = "SELECT * FROM BETS " +
                "JOIN BET_PROSPECTS BP ON BETS.BET_PROSPECT_ID = BP.ID " +
                "WHERE (FINALIZED = FALSE AND USER_ID = :USER_ID) " +
                "ORDER BY BP.COMMENCE_TIME DESC",
        resultClass = Bet.class
)

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "BETS")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "BET_PROSPECT_ID")
    private BetProspect betProspect;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPPED_WINNER")
    private Winner tippedWinner;

    @Column(name = "ODD")
    private BigDecimal odd;

    @Column(name = "STAKE")
    private BigDecimal stake;

    @Column(name = "FINALIZED")
    private boolean finalized;

    @Enumerated(EnumType.STRING)
    @Column(name = "WINNER")
    private Winner winner;

    @Column(name = "WON")
    private boolean won;

    @Column(name = "CASH_WIN")
    private BigDecimal cashWin;

}
