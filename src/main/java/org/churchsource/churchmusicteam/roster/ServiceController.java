package org.churchsource.churchmusicteam.roster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//TODO fix roles for auth (use special roles for music team, don't use service roles)

@RestController
@RequestMapping(value="/services")
@Slf4j
public class ServiceController {

  @Autowired
  private ServicesRepository servicesRepository;

  @Autowired
  private ServiceFactory serviceFactory;

  @PersistenceContext
  private EntityManager entityManager;
  
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ViewMusicTeamRole')")
  public ServiceFullViewModel getService(@PathVariable Long id) {
    Service foundChurchService = servicesRepository.findEntityById(id);
    if(foundChurchService != null) {
      return serviceFactory.createServiceFullViewModelFromEntity(foundChurchService);
    } else {
      return null;
    }
  }

  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasAuthority('EditMusicTeamRole')")
  public ServiceFullViewModel addService(@RequestBody ServiceBackingForm form) {
      System.out.println(form);
    Service createdService = servicesRepository.save(serviceFactory.createServiceEntityFromBackingForm(form));
    if(createdService != null) {
      return serviceFactory.createServiceFullViewModelFromEntity(createdService);
    } else {
      return null;
    }
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  //TODO temp fix for cross origin. this must be fixed later
  @CrossOrigin
  @PreAuthorize("hasAuthority('EditMusicTeamRole')")
  public ServiceFullViewModel updateService(@PathVariable Long id, @RequestBody ServiceBackingForm form) throws Exception {
    //TODO add validator to check if all fields are valid
    if(id == null || form == null) {
      throw new Exception ("Invalid Request"); // TODO to be replaced with validator
    }
    if(form.getId() != null && id != form.getId()) {
      throw new Exception ("If ID is in Path and message body they must be equal. "); // TODO to be replaced with validator
    }
    form.setId(id);
    Service updatedChurchService = servicesRepository.updateService(serviceFactory.createServiceEntityFromBackingForm(form));
    if(updatedChurchService != null) {
      return serviceFactory.createServiceFullViewModelFromEntity(updatedChurchService);
    } else {
      return null;
    }
  }
}
