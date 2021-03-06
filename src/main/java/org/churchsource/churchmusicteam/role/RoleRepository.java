package org.churchsource.churchmusicteam.role;

import org.churchsource.churchmusicteam.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@Transactional
public class RoleRepository extends AbstractRepository<Role> {

  public List<Role> getAllRoles() {
      return entityManager.createNamedQuery(RoleNamedQueryConstants.NAME_GET_ALL_ROLES, Role.class)
              .setParameter("includeDeleted", false)
              .getResultList();
  }

  public Role findRoleById(Long id) throws NoResultException {
    return entityManager.createNamedQuery(RoleNamedQueryConstants.NAME_FIND_ROLE_BY_ID, Role.class)
        .setParameter("id", id)
        .getSingleResult();
  }

  public Role findRoleByRoleName(String rolename) throws NoResultException {
    return entityManager.createNamedQuery(RoleNamedQueryConstants.NAME_FIND_ROLE_BY_ROLENAME, Role.class)
            .setParameter("rolename", rolename)
            .getSingleResult();
  }

  public Role updateRole(Role role) {
    Role existingRole = findRoleById(role.getId());
    Role updatedRole = new Role();
    existingRole.mergeEntities(role, updatedRole);
    return update(updatedRole);
  }

  public void deleteRole(Long roleId) {
    Role existingRoleToDelete = findRoleById(roleId);
    existingRoleToDelete.setDeleted(true);
    update(existingRoleToDelete);
  }
}
