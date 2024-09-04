package com.graduationproject.isn.repositories;

import com.graduationproject.isn.domain.entity.WebsiteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebsiteRepository extends CrudRepository<WebsiteEntity, UUID> {

    /* @Query("""
        SELECT new com.graduationproject.isn.domain.entity.WebsiteEntity(paw.id, paw.url)
        FROM price_alert_websites paw
        JOIN price_alert_website_product pawp ON paw.id = pawp.website_id
        JOIN price_alert_products pap ON pawp.product_id = pap.id
        WHERE pap.id = :productUuid
        """)*/
    List<WebsiteEntity> findAllByProductsName(@Param("productName") String productName);

}
