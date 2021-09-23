package org.churchsource.churchmusicteam.person;

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
public class PersonInRosterViewModel extends BaseViewModel<Long> implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  private String name;

  @Builder(builderMethodName = "aPersonFullViewModel")
  public PersonInRosterViewModel(Long id, String name) {
    super(id);
    this.name = name;
  }
}

