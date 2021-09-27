package org.churchsource.churchmusicteam.roster;

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

  public Service updateService(Service churchService) {
    Service existingChurchService = findServiceById(churchService.getId());
    Service updatedChurchService = new Service();
    existingChurchService.mergeEntities(churchService, updatedChurchService);
    return update(updatedChurchService);
  }
}
