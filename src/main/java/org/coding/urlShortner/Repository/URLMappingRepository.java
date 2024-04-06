package org.coding.urlShortner.Repository;

import org.coding.urlShortner.Entity.URLMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLMappingRepository extends JpaRepository<URLMapping, Long> {
    List<URLMapping> findByShortURL(String shortURL);

    List<URLMapping> findByOriginalURL(String originalURL);
}
