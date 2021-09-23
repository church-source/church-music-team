package org.churchsource.churchmusicteam.roster;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.churchsource.churchmusicteam.person.Person;
import org.churchsource.churchmusicteam.role.Role;

import javax.persistence.*;
import java.io.Serializable;


/*@NamedQueries({
        @NamedQuery(name = SongNamedQueryConstants.NAME_COUNT_SERVICES_WHERE_SONG_WAS_CHOSEN,
                query = SongNamedQueryConstants.QUERY_COUNT_SERVICES_WHERE_SONG_WAS_CHOSEN),
})*/

@Getter
@Setter
@ToString(exclude="service")
@EqualsAndHashCode(exclude="service")
@NoArgsConstructor
@Entity
@Table(name="Roster")
@IdClass(RosterId.class)
public class Roster implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  @ManyToOne
  @JoinColumn(name = "service")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Id
  private Service service;

  @OneToOne
  @Id
  @JoinColumn(name="role",referencedColumnName="id")
  private Role role;

  @OneToOne
  @Id
  @JoinColumn(name="person",referencedColumnName="id")
  private Person person;

  @Builder(builderMethodName = "aRoster")
  public Roster(Service service, Role role, Person person) {
    this.service = service;
    this.role = role;
    this.person = person;
  }

}

