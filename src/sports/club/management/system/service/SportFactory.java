package sports.club.management.system.service;

import sports.club.management.system.domain.Sport;

public class SportFactory {
    public static Sport createTeamSport(String name) {
        return new Sport(name,true);
    }

    public static Sport createIndividualSport(String name) {
        return new Sport(name,false);
    }

    public static Sport create(String name,boolean isTeamSport) {
        return new Sport(name,isTeamSport);
    }
}
