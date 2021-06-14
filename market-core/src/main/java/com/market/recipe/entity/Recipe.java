package com.market.recipe.entity;


import com.market.common.domain.BaseTimeEntity;
import com.market.member.entity.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class Recipe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private int views = 0;

    @NotNull
    @Lob @Basic(fetch = LAZY)
    private String description;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Member member;

    @OneToMany(mappedBy = "recipe", cascade = ALL, orphanRemoval = true)
    private List<RecipeProduct> includedProducts = new ArrayList<>();
}
