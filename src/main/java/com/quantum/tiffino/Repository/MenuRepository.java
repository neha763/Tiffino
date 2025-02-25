package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Category;
import com.quantum.tiffino.Entity.Menu;
import com.quantum.tiffino.Entity.Region;
import com.quantum.tiffino.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Example of a custom query
    List<Menu> findByCategory(Category category);

    // Another example: Find menus by region
    List<Menu> findByRegion(Region region);

    // Or using a custom JPQL query
    @Query("SELECT m FROM Menu m WHERE m.startDate <= :currentDate AND m.endDate >= :currentDate")
    List<Menu> findActiveMenus(LocalDate currentDate);

    Optional<Menu> findFirstByRestaurantAndRegionOrderByStartDateDesc(Restaurant restaurant, Region region);
}
