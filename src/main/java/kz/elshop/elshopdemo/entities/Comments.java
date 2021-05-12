package kz.elshop.elshopdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "addedDate")
    private java.sql.Timestamp addedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Items item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users author;
}
