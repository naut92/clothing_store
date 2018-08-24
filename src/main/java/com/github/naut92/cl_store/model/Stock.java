package com.github.naut92.cl_store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock", schema = "public", catalog = "cl_store")
public class Stock implements Serializable {
    private static final long serialVersionUID = 5124000706092599751L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "clothes_name")
    @NonNull
    private String clothesName;

    @OneToMany(mappedBy = "stockByClothes")
    private Collection<ClothesInStock> stockByClothes;

}