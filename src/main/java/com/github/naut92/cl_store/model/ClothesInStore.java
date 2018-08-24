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
@Table(name = "clothes_store")
public class ClothesInStore implements Serializable {
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
    //@Basic
    //@Column(clothesName = "in_store")
    //private Boolean inStore;
    //@Basic
    //@Column(clothesName = "in_stock")
    //private Boolean inStock;


    //@OneToMany(mappedBy = "clothesBySize")
    //private Collection<Size> clothesBySize;
/*
    @OneToMany(mappedBy = "clothesByColor")
    private Collection<Color> clothesByColor;
*/
    //@OneToMany(mappedBy = "clothesByType")
    //private Collection<Type> clothesByType;

//*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Store storeByClothes;

    /*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "name", referencedColumnName = "clothes_name", insertable = false, updatable = false)
    private Stock stockByClothes;
*/
}