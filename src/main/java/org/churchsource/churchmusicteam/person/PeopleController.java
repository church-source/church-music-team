package org.churchsource.churchmusicteam.person;

import lombok.extern.slf4j.Slf4j;
import org.churchsource.churchmusicteam.viewmodel.BaseViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/people")
@Slf4j
public class PeopleController {

  @Autowired
  private PeopleRepository peopleRepository;

  @Autowired
  private org.churchsource.churchmusicteam.person.PeopleFactory peopleFactory;

  @PersistenceContext
  private EntityManager entityManager;
  
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ViewMusicTeamPeople')")
  public PersonFullViewModel getPerson(@PathVariable Long id) {
    Person foundPerson = peopleRepository.findEntityById(id);
    if(foundPerson != null) {
      return peopleFactory.createPersonFullViewModelFromEntity(foundPerson);
    } else {
      return null;
    }
  }

  @GetMapping(path= "/find", params = "name")
  @PreAuthorize("hasAuthority('ViewMusicTeamPeople')")
  public PersonFullViewModel getPersonByName(@RequestParam String name) {
    Person foundPerson = peopleRepository.findPersonByName(name);
    if(foundPerson != null) {
      return peopleFactory.createPersonFullViewModelFromEntity(foundPerson);
    } else {
      return null;
    }
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ViewMusicTeamPeople')")
  public List<? extends BaseViewModel<Long>> getAllPeople(@RequestParam(required = false) String roleName) {
    List<Person> people = null;
    if(roleName == null) {
      people = peopleRepository.getAllPeople();
      return convertListOfPeopleToListOfPeopleViewModels(people);
    }
    else {
      people = peopleRepository.getAllPeopleWithGivenRoleName(roleName);
      return convertListOfPeopleToListOfPersonInRosterViewModel(people);
    }

  }

  private List<PersonFullViewModel> convertListOfPeopleToListOfPeopleViewModels(List<Person> people) {
    List<PersonFullViewModel> peopleViewModels = people.stream()
            .map(person -> peopleFactory.createPersonFullViewModelFromEntity(person))
            .collect(Collectors.toList());
    return peopleViewModels;
  }

  private List<PersonInRosterViewModel> convertListOfPeopleToListOfPersonInRosterViewModel(List<Person> people) {
    List<PersonInRosterViewModel> peopleViewModels = people.stream()
            .map(person -> peopleFactory.createPersonInRosterViewModelFromEntity(person))
            .collect(Collectors.toList());
    return peopleViewModels;
  }

  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasAuthority('AddMusicTeamPeople')")
  public PersonFullViewModel addPerson(@RequestBody PersonBackingForm form) {
    Person createdPerson = peopleRepository.save(peopleFactory.createPersonEntity(form));
    if(createdPerson != null) {
      return peopleFactory.createPersonFullViewModelFromEntity(createdPerson);
    } else {
      return null;
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasAuthority('AddMusicTeamPeople')")
  public HttpStatus deletePerson(@PathVariable Long id) {
    peopleRepository.deletePerson(id);
    return HttpStatus.NO_CONTENT;
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  //TODO temp fix for cross origin. this must be fixed later
  @CrossOrigin
  @PreAuthorize("hasAuthority('EditMusicTeamPeople')")
  public PersonFullViewModel updatePerson(@PathVariable Long id, @RequestBody PersonBackingForm form) throws Exception {
    //TODO add validator to check if all fields are valid
    if(id == null || form == null) {
      throw new Exception ("Invalid Request"); // TODO to be replaced with validator
    }
    if(form.getId() != null && id != form.getId()) {
      throw new Exception ("If ID is in Path and message body they must be equal. "); // TODO to be replaced with validator
    }
    form.setId(id);
    Person updatedPerson = peopleRepository.updatePerson(peopleFactory.createPersonEntity(form));
    if(updatedPerson != null) {
      return peopleFactory.createPersonFullViewModelFromEntity(updatedPerson);
    } else {
      return null;
    }
  }
}
