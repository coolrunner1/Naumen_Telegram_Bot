package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.Category;

import java.util.List;

/**
 * Сервис для работы с сущностью Category (категория) телеграмм-бота
 */
public interface CategoryService {
    /**
     * Получить список категорий по идентификатору категории-родителя
     * @param id идентификатор категории-родителя
     */
    List<Category> getCategoriesByParentId(Long id);

    Long getCategoryIdByName(String name);
}
