package kz.elshop.elshopdemo.repositories;

import kz.elshop.elshopdemo.entities.Brands;
import kz.elshop.elshopdemo.entities.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brands, Long> {
    List<Brands> findAllByCountry_Id(Long id);

}
