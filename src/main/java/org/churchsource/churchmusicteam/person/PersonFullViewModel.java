package org.churchsource.churchmusicteam.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.churchsource.churchmusicteam.role.Role;
import org.churchsource.churchmusicteam.viewmodel.BaseViewModel;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PersonFullViewModel extends BaseViewModel<Long> implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  private String name;

  private Boolean resigned;

  //TODO note that the roles should also be made a ViewModel instead of referencing the entity.
  private Set<Role> roles;

  @Builder(builderMethodName = "aPersonFullViewModel")
  public PersonFullViewModel(Long id, String name, Boolean resigned, Set<Role> roles) {
    super(id);
    this.name = name;
    this.resigned = resigned;
    this.roles = roles;

  }
}

