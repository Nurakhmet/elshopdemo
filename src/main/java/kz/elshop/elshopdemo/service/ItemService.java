package kz.elshop.elshopdemo.service;

import kz.elshop.elshopdemo.db.ShopItem;
import kz.elshop.elshopdemo.entities.*;

import java.util.List;

public interface ItemService {

    Items addItem(Items item);
    List<Items> getAllItems();
    Items getItem(Long id);
    void deleteItem(Items item);
    Items saveItem(Items item);
    List<Items> isTopPage();
    List<Items> isTopPageFalse();

    List<Items> byName(String name);
//    List<Items> byNamePriceBetweenAsc(String name, double price1, double price2);
//    List<Items> byNamePriceAfterAsc(String name, double price1);
//    List<Items> byNamePriceBeforeAsc(String name, double price2);
//    List<Items> byNamePriceBetweenDesc(String name, double price1, double price2);
//    List<Items> byNamePriceAfterDesc(String name, double price1);
//    List<Items> byNamePriceBeforeDesc(String name, double price2);
//    List<Items> byPriceBetweenAsc(double price1, double price2);
//    List<Items> byPriceBetweenDesc(double price1, double price2);
//    List<Items> byNamePriceAsc(String name);
//    List<Items> byNamePriceDesc(String name);
//    List<Items> byBrandIdPriceAsc(Long brandsId);

//    List<Items> byNameAndBrandId(String name, Long brandsId);
//    List<Items> byNameAndBrandIdPriceBetweenAsc(String name, double price1, double price2, Long brandsId);
//    List<Items> byNameAndBrandIdPriceAfterAsc(String name, double price1, Long brandsId);
//    List<Items> byNameAndBrandIdPriceBeforeAsc(String name, double price2, Long brandsId);
//    List<Items> byNameAndBrandIdPriceBetweenDesc(String name, double price1, double price2, Long brandsId);
//    List<Items> byNameAndBrandIdPriceAfterDesc(String name, double price1, Long brandsId);
//    List<Items> byNameAndBrandIdPriceBeforeDesc(String name, double price2, Long brandsId);
//    List<Items> byPriceBetweenAndBrandIdAsc(double price1, double price2, Long brandsId);
//    List<Items> byPriceBetweenAndBrandIdDesc(double price1, double price2, Long brandsId);
//    List<Items> byNameAndBrandIdPriceAsc(String name, Long brandsId);
//    List<Items> byNameAndBrandIdPriceDesc(String name, Long brandsId);

//    List<Items> findAllByPriceBetweenAndNameContainingAndBrand_IdOrderByPriceAsc(double price1, double price2, String name,Long brandsId);
//    List<Items> findAllByPriceBetweenAndNameContainingAndBrand_IdOrderByPriceDesc(double price1, double price2, String name,Long brandsId);
//
//    List<Items> findAllByBrand_IdOrderByPriceAsc(Long brandsId);
//    List<Items> findAllByBrand_IdOrderByPriceDesc(Long brandsId);

    List<Items> byBrandIdPriceAsc(Long brandsId);


    List<Items> byBrandIdNamePriceBetweenAsc(Long brand_id , String name, double price1, double price2);
    List<Items> byBrandIdNamePriceBetweenDesc(Long brand_id , String name, double price1, double price2);

    List<Countries> getAllCountries();
    Countries addCountry(Countries country);
    Countries saveCountries(Countries country);
    Countries getCountry(Long id);
    void deleteCountry(Countries countries);

    List<Brands> getAllBrands();
    Brands addBrands(Brands brand);
    Brands saveBrands(Brands brand);
    Brands getBrands(Long id);
    void deleteBrands(Brands brands);


    List<Categories> getAllCategories();
    Categories addCategories(Categories categories);
    Categories saveCategories(Categories categories);
    Categories getCategories(Long id);
    void deleteCategories(Categories categories);

    List<Pictures> getAllPictures();
    List<Pictures> findAllByItemId(Long id);
    Pictures addPictures(Pictures pictures);
    Pictures savePictures(Pictures pictures);
    Pictures getPictures(Long id);
    void deletePictures(Pictures pictures);

    void savePurchase(Purchase purchase);

    List<Purchase> allPurchases();

    Comments addComment(Comments comment);
    List<Comments> getAllComment();
    List<Comments> findAllByItemId1(Long id);
    Comments getComment(Long id);
    void deleteComment(Comments comment);
    Comments saveComment(Comments comment);
}
