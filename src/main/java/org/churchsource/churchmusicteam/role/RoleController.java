package org.churchsource.churchmusicteam.role;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/role")
@Slf4j
public class RoleController {

  @Autowired
  private org.churchsource.churchmusicteam.role.RoleRepository roleRepository;

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ViewMusicTeamRole')")
  public Role getRole(@PathVariable Long id) {
    return roleRepository.findRoleById(id);
  }

  @GetMapping(params = "name")
  @PreAuthorize("hasAuthority('ViewMusicTeamRole')")
  public Role findRole(@RequestParam String name) {
    return roleRepository.findRoleByRoleName(name);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ViewMusicTeamRole')")
  public List<Role> getRoles() {
    return roleRepository.getAllRoles();
  }

  @RequestMapping(method = RequestMethod.POST)
  @CrossOrigin
  @PreAuthorize("hasAuthority('AddMusicTeamRole')")
  public Role addRole(@RequestBody Role role) {
    //TODO not using a backing form for now
    return roleRepository.save(role);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
  @CrossOrigin
  @PreAuthorize("hasAuthority('EditMusicTeamRole')")
  public Role updateRole(@RequestBody Role form) {
    //TODO this is terrible, I need to add validation
    return roleRepository.updateRole(form);
  }
}
