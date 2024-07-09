package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.ClientOrder;

import java.util.List;

public interface ClientOrderService
{
    List<ClientOrder> getClientOrders(Long id);
}
