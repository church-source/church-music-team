package org.churchsource.churchmusicteam.roster;

import org.churchsource.churchmusicteam.model.type.ServiceType;
import org.churchsource.churchmusicteam.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class ServicesRepository extends AbstractRepository<Service> {
  public Service findServiceById(Long id) throws NoResultException {
    return entityManager.createNamedQuery(ServiceNamedQueryConstants.NAME_FIND_SERVICE_BY_ID, Service.class)
        .setParameter("id", id)
        .getSingleResult();
  }

  public List<Service> findServiceByServiceDateAndType(LocalDate date, ServiceType type) throws NoResultException {
    return entityManager.createNamedQuery(ServiceNamedQueryConstants.NAME_FIND_SERVICE_BY_DATE_AND_TYPE, Service.class)
            .setParameter("date", date)
            .setParameter("type", type)
            .setParameter("includeDeleted", false)
            .getResultList();
  }

  public Service updateService(Service churchService) {
    Service existingChurchService = findServiceById(churchService.getId());
    Service updatedChurchService = new Service();
    existingChurchService.mergeEntities(churchService, updatedChurchService);
    return update(updatedChurchService);
  }

  public List<Service> findServiceBetweenDates(LocalDate startDate, LocalDate endDate) {
    return entityManager.createNamedQuery(ServiceNamedQueryConstants.NAME_GET_SERVICES_BETWEEN_DATES, Service.class)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .setParameter("includeDeleted", false)
            .getResultList();
  }
}
