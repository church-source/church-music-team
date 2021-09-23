package org.churchsource.churchmusicteam.roster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

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
  @PreAuthorize("hasAuthority('ViewService')")
  public ServiceFullViewModel getService(@PathVariable Long id) {
    Service foundChurchService = servicesRepository.findEntityById(id);
    if(foundChurchService != null) {
      return serviceFactory.createServiceFullViewModelFromEntity(foundChurchService);
    } else {
      return null;
    }
  }
}
