package org.churchsource.churchmusicteam.roster;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.churchsource.churchmusicteam.model.ChurchMusicTeamEntity;
import org.churchsource.churchmusicteam.model.type.ServiceType;
import org.churchsource.churchmusicteam.person.Person;
import org.churchsource.churchmusicteam.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NamedQueries({
    @NamedQuery(name = ServiceNamedQueryConstants.NAME_FIND_SERVICE_BY_ID,
        query = ServiceNamedQueryConstants.QUERY_FIND_SERVICE_BY_ID),
})

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name="Service")
public class Service extends ChurchMusicTeamEntity<Long> implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  private LocalDate serviceDate;

  @Enumerated(EnumType.STRING)
  private ServiceType serviceType;

  @OneToMany(mappedBy = "service", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Roster> roster = new ArrayList<Roster>();


  @Builder(builderMethodName = "aService")
  public Service(Long id, Date created, Date modified, Boolean deleted, LocalDate serviceDate,
                ServiceType serviceType, Role role, Person person) {
    super(id, created, modified, deleted);
    this.serviceDate = serviceDate;
    this.serviceType = serviceType;
    this.roster = roster;
  }
}

