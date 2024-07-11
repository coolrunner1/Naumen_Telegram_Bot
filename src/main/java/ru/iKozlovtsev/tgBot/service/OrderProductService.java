package ru.iKozlovtsev.tgBot.service;

import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.entity.Product;

public interface OrderProductService {
    void createOrderProduct(ClientOrder clientOrder, Long productId);
}
