package com.piper.valley.models.repository;

import com.piper.valley.models.domain.PhysicalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PhysicalProductRepository extends JpaRepository<PhysicalProduct, Long> {
	Optional<PhysicalProduct>findById(Long id);
}
