package ru.iKozlovtsev.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iKozlovtsev.tgBot.entity.Category;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long>
{
    Category findCategoryByName(String name);

    @Query("from Category cat where cat.parent.id = :id")
    List<Category> findCategoriesByParentId(Long id);

    @Query("from Category cat where cat.parent is null")
    List<Category> getMainCategories();
}
