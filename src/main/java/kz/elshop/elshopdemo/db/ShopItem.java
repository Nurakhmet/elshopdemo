package kz.elshop.elshopdemo.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int amount;
    private int stars;
    private String pictureUrl;
}
