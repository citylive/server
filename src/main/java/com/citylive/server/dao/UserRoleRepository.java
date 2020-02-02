package com.citylive.server.dao;

import com.citylive.server.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, String> {
}
