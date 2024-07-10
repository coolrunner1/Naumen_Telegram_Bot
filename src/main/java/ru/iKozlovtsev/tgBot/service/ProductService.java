package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.Product;

import java.util.List;

/**
 * Сервис для работы с сущностью Product (продукт) телеграмм-бота
 */
public interface ProductService
{
    /**
     * Получить список товаров в категории
     * @param id идентификатор категории
     */
    List<Product> getProductsByCategoryId(Long id);

    /**
     * Получить список всех товаров во всех заказах клиента
     * @param id идентификатор клиента
     */
    List<Product> getClientProducts(Long id);

    /**
     * Получить указанное кол-во самых популярных (наибольшее
     * количество штук в заказах) товаров среди клиентов
     * @param limit максимальное кол-во товаров
     */
    List<Product> getTopPopularProducts(Integer limit);

    /**
     * Найти все продукты по подстроке названия
     * @param name подстрока названия продукта
     */
    default List<Product> searchProductsByName(String name) {
        throw new UnsupportedOperationException("Доп. задание");
    }
}
