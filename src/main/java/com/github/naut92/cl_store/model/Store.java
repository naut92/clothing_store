package com.github.naut92.cl_store.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store", schema = "public", catalog = "cl_store")
public class Store implements Serializable {
    private static final long serialVersionUID = -481073315751589931L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "clothes_name")
    private String clothesName;

    //@Basic
    //@Column(name = "available")
    //private Boolean available;


    @OneToMany(mappedBy = "storeByClothes")
    private Collection<ClothesInStore> storeByClothes;
}