package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.Client;

import java.util.List;

/**
 * Сервис для работы с сущностью Client (клиент) телеграмм-бота
 */
public interface ClientService
{
    /**
     * Найти всех клиентов по подстроке имени
     * @param name подстрока имени клиента
     */
    default List<Client> searchClientsByName(String name) {
        throw new UnsupportedOperationException("Доп. задание");
    }
}
