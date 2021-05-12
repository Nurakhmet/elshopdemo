package kz.elshop.elshopdemo.service.impl;

import kz.elshop.elshopdemo.entities.*;
import kz.elshop.elshopdemo.repositories.*;
import kz.elshop.elshopdemo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Items addItem(Items item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Items> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Items getItem(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public void deleteItem(Items item) {
        itemRepository.delete(item);
    }

    @Override
    public Items saveItem(Items item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Items> isTopPage() {
        return itemRepository.findAllByInTopPageIsTrue();
    }

    @Override
    public List<Items> isTopPageFalse() {
        return itemRepository.findAllByInTopPageIsFalse();
    }

    @Override
    public List<Items> byName(String name) {
        return itemRepository.findAllByNameContaining(name);
    }


//    @Override
//    public List<Items> byNamePriceBetweenAsc(String name, double price1, double price2) {
//        return itemRepository.findAllByNameContainingAndPriceBetweenOrderByPriceAsc(name,price1,price2);
//    }
//
//    @Override
//    public List<Items> byNamePriceAfterAsc(String name, double price1) {
//        return itemRepository.findAllByNameContainingAndPriceAfterOrderByPriceAsc(name,price1);
//    }
//
//    @Override
//    public List<Items> byNamePriceBeforeAsc(String name, double price2) {
//        return itemRepository.findAllByNameContainingAndPriceBeforeOrderByPriceAsc(name,price2);
//    }
//
//    @Override
//    public List<Items> byNamePriceBetweenDesc(String name, double price1, double price2) {
//        return itemRepository.findAllByNameContainingAndPriceBetweenOrderByPriceDesc(name,price1,price2);
//    }
//
//    @Override
//    public List<Items> byNamePriceAfterDesc(String name, double price1) {
//        return itemRepository.findAllByNameContainingAndPriceAfterOrderByPriceDesc(name,price1);
//    }
//
//    @Override
//    public List<Items> byNamePriceBeforeDesc(String name, double price2) {
//        return itemRepository.findAllByNameContainingAndPriceBeforeOrderByPriceDesc(name,price2);
//    }
//
//    @Override
//    public List<Items> byPriceBetweenAsc(double price1, double price2) {
//        return itemRepository.findAllByPriceBetweenOrderByPriceAsc(price1,price2);
//    }
//
//    @Override
//    public List<Items> byPriceBetweenDesc(double price1, double price2) {
//        return itemRepository.findAllByPriceBetweenOrderByPriceDesc(price1,price2);
//    }
//
//    @Override
//    public List<Items> byNamePriceAsc(String name) {
//        return itemRepository.findAllByNameContainingOrderByPriceAsc(name);
//    }
//
//    @Override
//    public List<Items> byNamePriceDesc(String name) {
//        return itemRepository.findAllByNameContainingOrderByPriceDesc(name);
//    }

    @Override
    public List<Countries> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Countries addCountry(Countries country) {
        return countryRepository.save(country);
    }

    @Override
    public Countries saveCountries(Countries country) {
        return countryRepository.save(country);
    }

    @Override
    public Countries getCountry(Long id) {
        return countryRepository.getOne(id);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brands addBrands(Brands brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brands saveBrands(Brands brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brands getBrands(Long id) {
        return brandRepository.getOne(id);
    }

    @Override
    public void deleteCountry(Countries countries) {
        countryRepository.delete(countries);
    }

    @Override
    public void deleteBrands(Brands brands) {
        brandRepository.delete(brands);
    }



    @Override
    public List<Items> byBrandIdPriceAsc(Long brandsId) {
        return itemRepository.findAllByBrand_IdOrderByPriceAsc(brandsId);
    }

//    After brand including
//    @Override
//    public List<Items> byNameAndBrandId(String name, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_Id(name, brandsId);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceBetweenAsc(String name, double price1, double price2, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdAndPriceBetweenOrderByPriceAsc(name, brandsId,price1, price2);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceAfterAsc(String name, double price1, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdAndPriceAfterOrderByPriceAsc(name,brandsId, price1);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceBeforeAsc(String name, double price2, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdAndPriceBeforeOrderByPriceAsc(name,brandsId, price2);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceBetweenDesc(String name, double price1, double price2, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdAndPriceBetweenOrderByPriceDesc(name,brandsId, price1, price2 );
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceAfterDesc(String name, double price1, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdAndPriceAfterOrderByPriceDesc(name,brandsId, price1);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceBeforeDesc(String name, double price2, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdAndPriceBeforeOrderByPriceDesc(name,brandsId, price2);
//    }
//
//    @Override
//    public List<Items> byPriceBetweenAndBrandIdAsc(double price1, double price2, Long brandsId) {
//        return itemRepository.findAllByPriceBetweenAndBrand_IdOrderByPriceAsc(price1, price2, brandsId);
//    }
//
//    @Override
//    public List<Items> byPriceBetweenAndBrandIdDesc(double price1, double price2, Long brandsId) {
//        return itemRepository.findAllByPriceBetweenAndBrand_IdOrderByPriceDesc(price1, price2, brandsId);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceAsc(String name, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdOrderByPriceAsc(name, brandsId);
//    }
//
//    @Override
//    public List<Items> byNameAndBrandIdPriceDesc(String name, Long brandsId) {
//        return itemRepository.findAllByNameContainingAndBrand_IdOrderByPriceDesc(name, brandsId);
//    }


    @Override
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Categories addCategories(Categories categories) {
        return categoryRepository.save(categories);
    }

    @Override
    public Categories saveCategories(Categories categories) {
        return categoryRepository.save(categories);
    }

    @Override
    public Categories getCategories(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public void deleteCategories(Categories categories) {
        categoryRepository.delete(categories);
    }

    @Override
    public List<Items> byBrandIdNamePriceBetweenAsc(Long brand_id, String name, double price1, double price2) {
        return itemRepository.findAllByBrand_IdAndNameContainingAndPriceBetweenOrderByPriceAsc(brand_id,name,price1,price2);
    }

    @Override
    public List<Items> byBrandIdNamePriceBetweenDesc(Long brand_id, String name, double price1, double price2) {
        return itemRepository.findAllByBrand_IdAndNameContainingAndPriceBetweenOrderByPriceDesc(brand_id,name,price1,price2);
    }

    @Override
    public List<Pictures> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Pictures addPictures(Pictures pictures) {
        return pictureRepository.save(pictures);
    }

    @Override
    public Pictures savePictures(Pictures pictures) {
        return pictureRepository.save(pictures);
    }

    @Override
    public Pictures getPictures(Long id) {
        return pictureRepository.getOne(id);
    }

    @Override
    public void deletePictures(Pictures pictures) {
        pictureRepository.delete(pictures);
    }

    @Override
    public List<Pictures> findAllByItemId(Long id) {
        return pictureRepository.findAllByItem_id(id);
    }

    @Override
    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> allPurchases() {
        return purchaseRepository.findAll();
    }


    @Override
    public Comments addComment(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comments> getAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comments getComment(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public void deleteComment(Comments comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comments saveComment(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comments> findAllByItemId1(Long id) {
        return commentRepository.findAllByItem_id(id);
    }
}
