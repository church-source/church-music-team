package org.churchsource.churchmusicteam.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.churchsource.churchmusicteam.model.type.ServiceType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ServiceBackingForm {

  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate serviceDate;

  @Enumerated(EnumType.STRING)
  private ServiceType serviceType;

  private List<RosterBackingForm> roster = new ArrayList<RosterBackingForm>();

  @Builder(builderMethodName = "aServiceBackingForm")
  public ServiceBackingForm(Long id, LocalDate serviceDate, ServiceType serviceType, List<RosterBackingForm> roster) {
    this.id=id;
    this.serviceDate = serviceDate;
    this.serviceType = serviceType;
    this.roster = roster;
  }
}

