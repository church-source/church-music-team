package org.churchsource.churchmusicteam.roster;

import lombok.*;
import org.churchsource.churchmusicteam.person.PersonFullViewModel;
import org.churchsource.churchmusicteam.person.PersonInRosterViewModel;
import org.churchsource.churchmusicteam.role.Role;

import java.io.Serializable;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RosterFullViewModel implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  private Role role;

  private PersonInRosterViewModel person;

  @Builder(builderMethodName = "aSongItem")
  public RosterFullViewModel(Role role, PersonInRosterViewModel person) {
    this.role = role;
    this.person = person;
  }
}

