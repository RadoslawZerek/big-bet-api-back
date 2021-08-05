package com.radoslawzerek.bigbetbackend.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class TeamsMap {
    private final Map<String, Integer> teams;

    public TeamsMap() {
        teams = new HashMap<>();
        addLaLigaTeams();
        addPremierLeagueTeams();
        addSerieATeams();
    }

    public void addLaLigaTeams() {
        teams.put("Levante", 88);
        teams.put("Valladolid", 250);
        teams.put("Cádiz CF", 264);
        teams.put("Elche CF", 285);
        teams.put("Atlético Madrid", 78);
        teams.put("Valencia", 95);
        teams.put("Huesca", 299);
        teams.put("Sevilla", 559);
        teams.put("Alavés", 263);
        teams.put("Real Madrid", 86);
        teams.put("Barcelona", 81);
        teams.put("CA Osasuna", 79);
        teams.put("Athletic Bilbao", 77);
        teams.put("Getafe", 82);
        teams.put("Celta Vigo", 558);
        teams.put("Granada CF", 83);
        teams.put("Real Sociedad", 92);
        teams.put("Villarreal", 94);
        teams.put("Eibar", 278);
        teams.put("Real Betis", 90);
    }

    public void addPremierLeagueTeams() {
        teams.put("Crystal Palace", 354);
        teams.put("Newcastle United", 67);
        teams.put("Brighton and Hove Albion", 397);
        teams.put("Liverpool", 64);
        teams.put("Burnley", 328);
        teams.put("Manchester City", 65);
        teams.put("Everton", 62);
        teams.put("Leeds United", 341);
        teams.put("Sheffield United", 356);
        teams.put("West Bromwich Albion", 74);
        teams.put("Manchester United", 66);
        teams.put("Southampton", 340);
        teams.put("Chelsea", 61);
        teams.put("Tottenham Hotspur", 73);
        teams.put("Arsenal", 57);
        teams.put("Wolverhampton Wanderers", 76);
        teams.put("Fulham", 63);
        teams.put("Leicester City", 338);
        teams.put("Aston Villa", 58);
        teams.put("West Ham United", 563);
    }

    public void addSerieATeams() {
        teams.put("FC Internazionale", 108);
        teams.put("Sassuolo", 471);
        teams.put("Benevento", 1106);
        teams.put("Juventus", 109);
        teams.put("Atalanta BC", 102);
        teams.put("Hellas Verona FC", 450);
        teams.put("Lazio", 110);
        teams.put("Udinese", 115);
        teams.put("AC Milan", 98);
        teams.put("Fiorentina", 99);
        teams.put("Bologna", 103);
        teams.put("Crotone", 472);
        teams.put("Cagliari", 104);
        teams.put("Spezia", 488);
        teams.put("AS Roma", 100);
        teams.put("Napoli", 113);
        teams.put("Sampdoria", 584);
        teams.put("Torino", 586);
        teams.put("Genoa", 107);
        teams.put("Parma", 112);
    }
}
