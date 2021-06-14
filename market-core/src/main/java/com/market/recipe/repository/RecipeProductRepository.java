package com.market.recipe.repository;

import com.market.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeProductRepository extends JpaRepository<Recipe, Long> {
}
