package org.churchsource.churchmusicteam.roster;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RosterId implements Serializable {
    private Long service;

    private Long role;

    private Long person;

    public RosterId(Long service, Long role, Long person) {
        this.service = service;
        this.role = role;
        this.person = person;
    }
}

