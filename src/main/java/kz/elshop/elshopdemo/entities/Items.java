package kz.elshop.elshopdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "t_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stars")
    private int stars;

    @Column(name = "smallPicUrl")
    private String smallPicUrl;

    @Column(name = "largePicUrl")
    private String largePicUrl;

    @Column(name = "addedDate")
    private java.sql.Timestamp addedDate;

    @Column(name = "inTopPage")
    private Boolean inTopPage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brands brand;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Categories> categories;

}
