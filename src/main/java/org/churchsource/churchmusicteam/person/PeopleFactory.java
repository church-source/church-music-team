package org.churchsource.churchmusicteam.person;

import org.churchsource.churchmusicteam.role.Role;
import org.churchsource.churchmusicteam.role.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PeopleFactory {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    public Person createPersonEntity(PersonBackingForm pbForm) {
        Person aPerson = new Person();
        BeanUtils.copyProperties(pbForm, aPerson, "deleted, roles");
        Set<Role> pbFormRoles = pbForm.getRoles();
        Set<Role> finalRoles = new HashSet<Role>();
        if(pbFormRoles != null && pbFormRoles.isEmpty() == false) {
            for(Role role : pbFormRoles) {
                finalRoles.add(roleRepository.findRoleById(role.getId()));
            }
            aPerson.setRoles(finalRoles);
        } else {
            //TODO not sure what to do here. currently it will set the roles of the existing person to the new entity.
            try {
                Person existingPerson = peopleRepository.findPersonById(pbForm.getId());
                aPerson.setRoles(existingPerson.getRoles());
            } catch(Exception e) {
                // do nothing
            }
        }
        return aPerson;
    }

    public PersonFullViewModel createPersonFullViewModelFromEntity(Person person) {
        PersonFullViewModel personFullViewModel = new PersonFullViewModel();
        BeanUtils.copyProperties(person, personFullViewModel, "deleted, created, modified");
        return personFullViewModel;
    }

}
