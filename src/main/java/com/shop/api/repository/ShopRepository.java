package com.shop.api.repository;

import com.shop.api.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity,String> {
}
