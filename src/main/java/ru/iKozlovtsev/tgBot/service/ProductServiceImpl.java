package ru.iKozlovtsev.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.iKozlovtsev.tgBot.entity.*;
import ru.iKozlovtsev.tgBot.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProductsByCategoryId(Long id)
    {
        Category exampleCategory = new Category();
        exampleCategory.setId(id);
        Product exampleProduct = new Product();
        exampleProduct.setCategory(exampleCategory);
        return productRepository.findAll(Example.of(exampleProduct));
    }

    @Override
    public List<Product> getClientProducts(Long id)
    {
        return productRepository.findClientProducts(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return productRepository.findTopPopularProducts(limit);
    }

    @Override
    public List<Product> searchProductsByName(String name){
        return productRepository.searchProductsByNameContaining(name);
    }

}
