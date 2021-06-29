package com.market.product.entity;

import com.market.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = ALL, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();

    @Builder
    public Category(Long id, @NotNull String name, Category parent, List<Category> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = children;
    }
}
