package kz.elshop.elshopdemo.db;

import java.util.ArrayList;

public class DBManager {
    private static ArrayList<ShopItem> shopItems = new ArrayList<>();

    static {
        shopItems.add(new ShopItem(1L, "Iphone 12 Pro Max", "Iphone 12 Pro Max, 512 GB, 5G Networks, Display 6.7, Frequency 6 GHZ", 2499, 10,5,"https://mobile-review.com/news/wp-content/uploads/iphone-12-4k-240fps.jpg"));
        shopItems.add(new ShopItem(2L, "MEIZU 16 Pro", "MEIZU 16 PRO, Screen 6.2, 2232x1080 pixels, 8GB CPU RAM", 599, 10,4,"https://mobile-review.com/articles/2019/image/press-meizu-16s-pro/off/1.jpg"));
        shopItems.add(new ShopItem(3L, "Samsung Galaxy 20", "Samsung Galaxy 20, Galaxy fold, Flip 5G, Full hd, 256Gb memory", 799, 10,4,"https://images.samsung.com/nz/smartphones/galaxy-note20/buy/005-galaxynote20-mysticbronze-mo-720.jpg"));
    }

    private static Long id = 4L;

    public static void addItems(ShopItem shopItem){
        shopItem.setId(id);
        shopItems.add(shopItem);
        id++;
    }

    public static ArrayList<ShopItem> getItems(){
        return shopItems;
    }
}
