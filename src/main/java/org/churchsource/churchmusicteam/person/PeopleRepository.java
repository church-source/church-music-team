package org.churchsource.churchmusicteam.person;

import org.churchsource.churchmusicteam.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class PeopleRepository extends AbstractRepository<Person> {

  public List<Person> getAllPeople() {
    return entityManager.createNamedQuery(PeopleNamedQueryConstants.NAME_GET_ALL_PEOPLE, Person.class)
        .setParameter("includeDeleted", false)
        .getResultList();
  }

  public List<Person> getAllPeopleWithGivenRoleName(String roleName) {
    String sql = "SELECT distinct p.* FROM Person p  " +
            "JOIN person_role pr on p.id = pr.Person " +
            "JOIN Role R on pr.Role = R.id " +
            "where R.rolename = 'roleName'";


    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Person> cq = cb.createQuery(Person.class);
    Root<Person> root = cq.from(Person.class);
    Join<Object, Object> role = (Join<Object, Object>) root.fetch("roles");

    ParameterExpression<String> rn = cb.parameter(String.class);
    cq.where(cb.like(role.get("rolename"), rn));

    TypedQuery<Person> q = entityManager.createQuery(cq);
    q.setParameter(rn, "%"+roleName+"%");
    List<Person> people = q.getResultList();

    return people;
  }

  public Person findPersonById(Long id) throws NoResultException {
    return entityManager.createNamedQuery(PeopleNamedQueryConstants.NAME_FIND_PERSON_BY_ID, Person.class)
        .setParameter("id", id)
        .getSingleResult();
  }

  public Person updatePerson(Person person) {
    Person existingPerson = findPersonById(person.getId());
    Person updatedPerson = new Person();
    existingPerson.mergeEntities(person, updatedPerson);
    return update(updatedPerson);
  }

  public void deletePerson(Long personId) {
    Person personToDelete = findPersonById(personId);
    personToDelete.setDeleted(true);
    update(personToDelete);
  }

  public Person findPersonByName(String name) throws NoResultException, NonUniqueResultException {
    //There can only be one person with a given name due to a unique constraint
    return entityManager.createNamedQuery(PeopleNamedQueryConstants.NAME_FIND_PERSON_BY_NAME, Person.class)
            .setParameter("name", name)
            .getSingleResult();
  }

  public boolean verifyPersonExists(UUID id) {
    throw new RuntimeException("Not Yet Implemented");
  }
}
