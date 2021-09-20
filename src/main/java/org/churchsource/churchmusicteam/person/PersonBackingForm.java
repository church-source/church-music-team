package org.churchsource.churchmusicteam.person;

import lombok.*;
import org.churchsource.churchmusicteam.role.Role;

import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PersonBackingForm {

  private Long id;

  private String name;

  private Boolean resigned;

  Set<Role> roles;

  @Builder(builderMethodName = "aPersonBackingForm")
  public PersonBackingForm(Long id, String name, Boolean resigned, Set<Role> roles) {
    this.id=id;
    this.name = name;
    this.resigned = resigned;
    this.roles = roles;
  }
}

