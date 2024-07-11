package ru.iKozlovtsev.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.iKozlovtsev.tgBot.entity.Category;
import ru.iKozlovtsev.tgBot.repository.CategoryRepository;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoriesByParentId(Long id){
        if (id == null || id==0){
            return categoryRepository.getMainCategories();
        }
        return categoryRepository.findCategoriesByParentId(id);
    }

    @Override
    public Long getCategoryIdByName(String name){
        Category exampleCategory = new Category();
        exampleCategory.setName(name);
        Category category = categoryRepository.findCategoryByName(name);
        if (category==null)
        {
            return null;
        }
        return category.getId();
    }
}
