package com.graduationproject.isn.repositories;

import com.graduationproject.isn.domain.entity.IdentityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityRepository extends JpaRepository<IdentityEntity, Long> {

    Optional<IdentityEntity> findByUsername(String username);

}
