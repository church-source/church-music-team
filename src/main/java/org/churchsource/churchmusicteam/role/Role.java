package org.churchsource.churchmusicteam.role;

import lombok.*;
import org.churchsource.churchmusicteam.model.ChurchMusicTeamEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = RoleNamedQueryConstants.NAME_FIND_ROLE_BY_ID,
                query = RoleNamedQueryConstants.QUERY_FIND_ROLE_BY_ID),
        @NamedQuery(name = RoleNamedQueryConstants.NAME_FIND_ROLE_BY_ROLENAME,
                query = RoleNamedQueryConstants.QUERY_FIND_ROLE_BY_ROLENAME),
        @NamedQuery(name = RoleNamedQueryConstants.NAME_GET_ALL_ROLES,
                query = RoleNamedQueryConstants.QUERY_GET_ALL_ROLES)
})

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Role extends ChurchMusicTeamEntity<Long> implements Serializable {

    private String rolename;

    private String label;

    public Role(final String rolename, final String label) {
        super();
        this.rolename = rolename;
        this.label = label;
    }

    @Builder(builderMethodName = "aRole")
    public Role(Long id, Date created, Date modified, Boolean deleted, final String rolename, String label) {
        super(id, created, modified, deleted);
        this.rolename = rolename;
        this.label = label;
    }

}