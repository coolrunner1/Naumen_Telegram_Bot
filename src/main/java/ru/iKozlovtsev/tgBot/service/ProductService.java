package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.Product;

import java.util.List;

public interface ProductService
{
    List<Product> getProductsByCategoryId(Long id);
    List<Product> getClientProducts(Long id);
    List<Product> getTopPopularProducts(Integer limit);
    default List<Product> searchProductsByName(String name) {
        throw new UnsupportedOperationException("Доп. задание");
    }
}
