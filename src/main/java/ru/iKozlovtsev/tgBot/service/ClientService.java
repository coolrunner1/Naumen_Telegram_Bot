package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.Client;

import java.util.List;

public interface ClientService
{
    default List<Client> searchClientsByName(String name) {
        throw new UnsupportedOperationException("Доп. задание");
    }
}
