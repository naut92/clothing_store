package com.github.naut92.cl_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "store_or_stock", schema = "public", catalog = "cl_store")
public class StoreOrStock implements Serializable {
    private static final long serialVersionUID = -481073315751589931L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "store_stock")
    private String storeOrStock;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clothes_all",
     //foreign key for StoreOrStock Entity in clothes_all table
     joinColumns = @JoinColumn(name = "store_stok_id"),
     //foreign key for other side - ClothesInStoreOrInStockRepository Entity in clothes_all table
     inverseJoinColumns = @JoinColumn(name = "clothes_id"))
     private Collection<ClothesInStoreOrInStock> storeOrStockByClothes;
}