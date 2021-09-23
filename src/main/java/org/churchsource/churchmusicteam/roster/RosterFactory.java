package org.churchsource.churchmusicteam.roster;

import org.churchsource.churchmusicteam.person.PeopleFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RosterFactory {

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private PeopleFactory peopleFactory;

    public Roster createRosterEntityFromBackingForm(RosterBackingForm pbForm) {
        Roster roster = new Roster();
        BeanUtils.copyProperties(pbForm, roster);
        return roster;
    }

    public RosterFullViewModel createRosterFullViewModelFromEntity(Roster roster) {
        RosterFullViewModel rosterFullViewModel = new RosterFullViewModel();
        BeanUtils.copyProperties(roster, rosterFullViewModel);
        rosterFullViewModel.setPerson(peopleFactory.createPersonInRosterViewModelFromEntity(roster.getPerson()));
        return rosterFullViewModel;
    }
}
