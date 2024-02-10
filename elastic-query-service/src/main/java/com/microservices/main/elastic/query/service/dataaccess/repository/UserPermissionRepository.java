package com.microservices.main.elastic.query.service.dataaccess.repository;

import com.microservices.main.elastic.query.service.dataaccess.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, UUID> {
    @Query(value = "select p.user_permission_id as id, u.username, d.document_id, p.permission_type " +
            "from users u " +
            " join user_permissions p on u.id = p.user_id " +
            "         join documents d on p.document_id = d.id " +
            "where u.id = p.user_id " +
            "and d.id = p.document_id " +
            "and u.username = 'app_user'", nativeQuery = true)
    Optional<List<UserPermission>> buscaAlgoEmparticular();
}
