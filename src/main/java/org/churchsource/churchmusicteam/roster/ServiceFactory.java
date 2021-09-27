package org.churchsource.churchmusicteam.roster;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceFactory {

    @Autowired
    private RosterFactory rosterFactory;

    public Service createServiceEntityFromBackingForm(ServiceBackingForm pbForm) {
        Service service = new Service();
        BeanUtils.copyProperties(pbForm, service, "deleted, roster");
        List<RosterBackingForm> rosterBackingForms = pbForm.getRoster();
        List<Roster> roster = new ArrayList<Roster>();
        for(RosterBackingForm rbf : rosterBackingForms) {
            Roster r = rosterFactory.createFilledOutRosterEntityFromBackingForm(rbf);
            r.setService(service);
            roster.add(r);
        }
        service.setRoster(roster);
        return service;
    }

    public ServiceFullViewModel createServiceFullViewModelFromEntity(Service churchService) {
        ServiceFullViewModel serviceFullViewModel = new ServiceFullViewModel();
        BeanUtils.copyProperties(churchService, serviceFullViewModel, "deleted, created, modified");
        List<Roster> roster = churchService.getRoster();
        List<RosterFullViewModel> rosterFullViewModels = new ArrayList<RosterFullViewModel>();//serviceFullViewModel.getSongItems();
        for(Roster r : roster) {
            rosterFullViewModels.add(rosterFactory.createRosterFullViewModelFromEntity(r));
        }
        serviceFullViewModel.setRoster(rosterFullViewModels);
        return serviceFullViewModel;
    }
}
