package com.github.advertising.repository;

import com.github.advertising.entity.CarouselImage;
import com.github.advertising.entity.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselImageRepo extends JpaRepository<CarouselImage, Long> {

    List<CarouselImage> findByStatus(EntityStatus status);

    @Modifying
    @Query(value = "update CarouselImage ci set ci.image=:image where ci.id=:id")
    void update(
            @Param(value = "id") Long id,
            @Param(value = "image") String image
    );

    @Modifying
    @Query(value = "update CarouselImage ci set ci.status=:status where ci.id=:id")
    void remove(
            @Param(value = "id") Long id,
            @Param(value = "status") EntityStatus status
    );

}
