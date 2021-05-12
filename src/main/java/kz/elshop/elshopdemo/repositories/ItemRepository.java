package kz.elshop.elshopdemo.repositories;

import kz.elshop.elshopdemo.entities.Brands;
import kz.elshop.elshopdemo.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Items, Long> {

    List<Items> findAllByInTopPageIsTrue();
    List<Items> findAllByInTopPageIsFalse();

    List<Items> findAllByNameContaining(String name);
//    List<Items> findAllByNameContainingOrderByPriceAsc(String name);
//    List<Items> findAllByNameContainingOrderByPriceDesc(String name);
//    List<Items> findAllByNameContainingAndPriceBetweenOrderByPriceAsc(String name,double price1, double price2);
//    List<Items> findAllByNameContainingAndPriceAfterOrderByPriceAsc(String name,double price1);
//    List<Items> findAllByNameContainingAndPriceBeforeOrderByPriceAsc(String name,double price2);
//    List<Items> findAllByNameContainingAndPriceBetweenOrderByPriceDesc(String name,double price1, double price2);
//    List<Items> findAllByNameContainingAndPriceAfterOrderByPriceDesc(String name,double price1);
//    List<Items> findAllByNameContainingAndPriceBeforeOrderByPriceDesc(String name,double price2);
//    List<Items> findAllByPriceBetweenOrderByPriceAsc(double price1, double price2);
//    List<Items> findAllByPriceBetweenOrderByPriceDesc(double price1, double price2);

    List<Items> findAllByPriceBetweenAndNameContainingAndBrand_IdOrderByPriceAsc(double price1, double price2, String name,Long brands_id);
    List<Items> findAllByPriceBetweenAndNameContainingAndBrand_IdOrderByPriceDesc(double price1, double price2, String name,Long brands_id);

    List<Items> findAllByBrand_IdOrderByPriceDesc(Long brands_id);
//    List<Items> findAllByNameContainingAndBrand_Id(String name, Long brandsId);
//    List<Items> findAllByNameContainingAndBrand_IdOrderByPriceAsc(String name, Long brandsId);
//    List<Items> findAllByNameContainingAndBrand_IdOrderByPriceDesc(String name, Long brandsId);
//    List<Items> findAllByNameContainingAndBrand_IdAndPriceBetweenOrderByPriceAsc(String name, Long brandsId, double price1, double price2);
//    List<Items> findAllByNameContainingAndBrand_IdAndPriceAfterOrderByPriceAsc(String name, Long brandsId,double price1);
//    List<Items> findAllByNameContainingAndBrand_IdAndPriceBeforeOrderByPriceAsc(String name, Long brandsId, double price2);
//    List<Items> findAllByNameContainingAndBrand_IdAndPriceBetweenOrderByPriceDesc(String name, Long brandsId, double price1, double price2);
//    List<Items> findAllByNameContainingAndBrand_IdAndPriceAfterOrderByPriceDesc(String name, Long brandsId,double price1);
//    List<Items> findAllByNameContainingAndBrand_IdAndPriceBeforeOrderByPriceDesc(String name, Long brandsId,double price2);
//    List<Items> findAllByPriceBetweenAndBrand_IdOrderByPriceAsc(double price1, double price2, Long brandsId);
//    List<Items> findAllByPriceBetweenAndBrand_IdOrderByPriceDesc(double price1, double price2, Long brandsId);

//    List<Items> findAllByNameContainingAndBrand_Id
    List<Items> findAllByBrand_IdOrderByPriceAsc(Long brandsId);
    List<Items> findAllByBrand_IdAndNameContainingAndPriceBetweenOrderByPriceAsc(Long brand_id , String name, double price1, double price2);
    List<Items> findAllByBrand_IdAndNameContainingAndPriceBetweenOrderByPriceDesc(Long brand_id , String name, double price1, double price2);












}
