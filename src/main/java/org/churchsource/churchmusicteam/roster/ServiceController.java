package org.churchsource.churchmusicteam.roster;

import com.sun.tools.internal.xjc.reader.Const;
import lombok.extern.slf4j.Slf4j;
import org.churchsource.churchmusicteam.model.type.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

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

  @GetMapping
  @PreAuthorize("hasAuthority('ViewService')")
  public List<ServiceFullViewModel> getServiceByDateAndType(
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
          @RequestParam(required = false) ServiceType type) {
    List<Service> foundChurchServices;
    if(date == null) {
      date = getDateOfFirstSundayFromNow();
      foundChurchServices = servicesRepository.findServiceByServiceDateAndType(date, type);
      List<Service> otherChurchServicesBeforeFirstSunday = servicesRepository.findServiceBetweenDates(LocalDate.now(), date);
      foundChurchServices.addAll(otherChurchServicesBeforeFirstSunday);
    } else {
      foundChurchServices = servicesRepository.findServiceByServiceDateAndType(date, type);
    }
    foundChurchServices.sort((Service c1, Service c2) -> c1.getServiceDate().compareTo(c2.getServiceDate()));
    return convertListOfServicesToListOfServicesViewModels(foundChurchServices);
  }

  private LocalDate getDateOfFirstSundayFromNow() {
    LocalDate date = LocalDate.now();
    if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      return date;
    }
    LocalDate firstSundayFromNow = date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    return firstSundayFromNow;
  }

  private List<ServiceFullViewModel> convertListOfServicesToListOfServicesViewModels(List<Service> churchServices) {
    List<ServiceFullViewModel> serviceViewModels = churchServices.stream()
            .map(churchService -> serviceFactory.createServiceFullViewModelFromEntity(churchService))
            .collect(Collectors.toList());
    return serviceViewModels;
  }

  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasAuthority('EditMusicTeamRole')")
  public ServiceFullViewModel addService(@RequestBody ServiceBackingForm form) {
    Service foundChurchService = getServiceByDateAndType(form);

    if(foundChurchService != null) {
      throw new NonUniqueResultException("Service with that date and type exists already");
    }
    Service createdService = servicesRepository.save(serviceFactory.createServiceEntityFromBackingForm(form));
    if(createdService != null) {
      return serviceFactory.createServiceFullViewModelFromEntity(createdService);
    } else {
      return null;
    }
  }

  private Service getServiceByDateAndType(ServiceBackingForm form) {
    List<Service> foundChurchServices = servicesRepository.findServiceByServiceDateAndType(form.getServiceDate(), form.getServiceType());
    //there can only be one service in list when type is also specified.
    Service foundChurchService = null;
    if(foundChurchServices!= null && foundChurchServices.size() > 0 ) {
      foundChurchService = foundChurchServices.get(0);
    }
    return foundChurchService;
  }

  @RequestMapping(value = {"","{id}"}, method = RequestMethod.PUT)
  //TODO temp fix for cross origin. this must be fixed later
  @CrossOrigin
  @PreAuthorize("hasAuthority('EditMusicTeamRole')")
  public ServiceFullViewModel updateService(@PathVariable(required=false) Long id, @RequestBody ServiceBackingForm form) throws Exception {
    //TODO add validator to check if all fields are valid
    if(form == null) {
      throw new Exception ("Invalid Request"); // TODO to be replaced with validator
    }

    if(form.getId() != null && id != form.getId()) {
      throw new Exception ("If ID is in Path and message body they must be equal. "); // TODO to be replaced with validator
    }

    if(id == null) { //if id is null, form.getId() will alo be null
      Service foundChurchService = getServiceByDateAndType(form);
      if (foundChurchService != null) {
        id = foundChurchService.getId();
      } else {
        Service createdService = servicesRepository.save(serviceFactory.createServiceEntityFromBackingForm(form));
        return serviceFactory.createServiceFullViewModelFromEntity(createdService);
      }
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
