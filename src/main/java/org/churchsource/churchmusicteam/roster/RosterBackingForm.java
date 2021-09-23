package org.churchsource.churchmusicteam.roster;

import lombok.*;
import org.churchsource.churchmusicteam.person.Person;
import org.churchsource.churchmusicteam.role.Role;

import java.io.Serializable;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RosterBackingForm implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  private Service service;

  private Role role;

  private Person person;

  @Builder(builderMethodName = "aRosterBackingForm")
  public RosterBackingForm(Service service, Role role, Person person) {
    this.service = service;
    this.role = role;
    this.person = person;
  }
}

