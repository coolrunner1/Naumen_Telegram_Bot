package ru.iKozlovtsev.tgBot.service;

import com.pengrad.telegrambot.model.User;
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
    /**
     * Adds a new client to the database
     * @param user information about user gathered from telegram api
     * @param phoneNumber client's number
     * @param address client's address
     */
    void createClient(User user, String phoneNumber, String address);
    /**
     * Checks if client is registered
     * @param id telegram id of the client
     */
    boolean clientExists(Long id);
    /**
     * Gets client by telegram id
     * @param id telegram id of the client
     */
    Client getClientByTelegramId(Long id);
}
