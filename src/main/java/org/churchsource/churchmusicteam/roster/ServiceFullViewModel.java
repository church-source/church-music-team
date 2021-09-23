package org.churchsource.churchmusicteam.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.churchsource.churchmusicteam.model.type.ServiceType;
import org.churchsource.churchmusicteam.viewmodel.BaseViewModel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ServiceFullViewModel extends BaseViewModel<Long> implements Serializable {

  private static final long serialVersionUID = -3479479691039681608L;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private LocalDate serviceDate;

  @Enumerated(EnumType.STRING)
  private ServiceType serviceType;

  //@JsonManagedReference
  private List<RosterFullViewModel> roster = new ArrayList<RosterFullViewModel>();

  @Builder(builderMethodName = "aServiceFullViewModel")
  public ServiceFullViewModel(Long id, LocalDate serviceDate, ServiceType serviceType, List<RosterFullViewModel> roster) {
    super(id);
    this.serviceDate = serviceDate;
    this.serviceType = serviceType;
    this.roster = roster;
  }
}

