package com.github.naut92.cl_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes_stock")
public class ClothesInStock implements Serializable {
    private static final long serialVersionUID = -481073315751589931L;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "name", referencedColumnName = "clothes_name", insertable = false, updatable = false)
    private Stock stockByClothes;
}
