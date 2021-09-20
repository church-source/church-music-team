package org.churchsource.churchmusicteam.person;

import lombok.*;
import org.churchsource.churchmusicteam.model.ChurchMusicTeamEntity;
import org.churchsource.churchmusicteam.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@NamedQueries({
    @NamedQuery(name = PeopleNamedQueryConstants.NAME_GET_ALL_PEOPLE,
        query = PeopleNamedQueryConstants.QUERY_GET_ALL_PEOPLE),
    @NamedQuery(name = PeopleNamedQueryConstants.NAME_FIND_PERSON_BY_ID,
        query = PeopleNamedQueryConstants.QUERY_FIND_PERSON_BY_ID),
    @NamedQuery(name = PeopleNamedQueryConstants.NAME_FIND_PERSON_BY_NAME,
        query = PeopleNamedQueryConstants.QUERY_FIND_PERSON_BY_NAME),
})

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name="Person")
public class Person extends ChurchMusicTeamEntity<Long> implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  private String name;

  private Boolean resigned;

  @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinTable(name = "person_role",
          joinColumns = { @JoinColumn(name = "person") },
          inverseJoinColumns = { @JoinColumn(name = "role") })
  private Set<Role> roles = new HashSet<Role>();

  @Builder(builderMethodName = "aPerson")
  public Person(Long id, Date created, Date modified, Boolean deleted, String name, Boolean resigned, Set<Role> roles) {
    super(id, created, modified, deleted);
    this.name = name;
    this.resigned = resigned;
    this.roles = roles;
  }
}

