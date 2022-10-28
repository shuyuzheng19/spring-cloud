package com.zsy.producer.repository;

import com.zsy.producer.entity.Permission;
import com.zsy.producer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 郑书宇
 * @create 2022/10/26 20:03
 * @desc
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Optional<Permission> findByName(String name);
}
