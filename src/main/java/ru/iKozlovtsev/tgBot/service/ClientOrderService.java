package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.ClientOrder;

import java.util.List;

/**
 * Сервис для работы с сущностью ClientOrder (заказ) телеграмм-бота
 */
public interface ClientOrderService
{
    /**
     * Получить список заказов клиента
     * @param id идентификатор клиента
     */
    List<ClientOrder> getClientOrders(Long id);
}
