package com.graduationproject.isn.repositories;

import com.graduationproject.isn.domain.entity.WebsiteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebsiteRepository extends CrudRepository<WebsiteEntity, UUID> {

    List<WebsiteEntity> findAllByProductsName(@Param("productName") String productName);

}
