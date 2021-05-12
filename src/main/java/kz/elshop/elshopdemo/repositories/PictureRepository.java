package kz.elshop.elshopdemo.repositories;

import kz.elshop.elshopdemo.entities.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Pictures, Long> {
    List<Pictures> findAllByItem_id(Long id);
}
