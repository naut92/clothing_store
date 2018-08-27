package com.github.naut92.cl_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes")
public class ClothesInStoreOrInStock implements Serializable {
    private static final long serialVersionUID = 5124000706092599751L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "size")
    private Long size;
    @Basic
    @Column(name = "cost")
    private String cost;
    @Basic
    @Column(name = "color", columnDefinition="enum('white', 'blue', 'red', 'green', 'black')")
    //@Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;
    @Basic
    @Column(name = "type", columnDefinition="enum('dress', 'pants', 'skirt', 'vest', 'shirt')")
    //@Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeClothes type;
    @Basic
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clothes_all",
     //foreign key for ClothesInStoreOrInStockRepository Entity in clothes_all table
     joinColumns = @JoinColumn(name = "clothes_id"),
      //foreign key for other side - StoreOrStock Entity in clothes_all table
     inverseJoinColumns = @JoinColumn(name = "store_stok_id"))
     private Collection<StoreOrStock> clothesByStoreOrStock;








    //@Basic
    //@Column(name = "in_store")
    //private Boolean inStore;
    //@Basic
    //@Column(name = "in_stock")
    //private Boolean inStock;


    //@OneToMany(mappedBy = "clothesBySize")
    //private Collection<Size> clothesBySize;
/*
    @OneToMany(mappedBy = "clothesByColor")
    private Collection<Color> clothesByColor;
*/
    //@OneToMany(mappedBy = "clothesByType")
    //private Collection<Type> clothesByType;



/*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private StoreOrStock storeOrStockByClothes;

    /*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "name", referencedColumnName = "clothes_name", insertable = false, updatable = false)
    private Stock stockByClothes;
*/
}