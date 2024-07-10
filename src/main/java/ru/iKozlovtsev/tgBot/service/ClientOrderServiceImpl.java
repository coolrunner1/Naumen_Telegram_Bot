package ru.iKozlovtsev.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.repository.ClientOrderRepository;

import java.util.List;

@Service
@Transactional
public class ClientOrderServiceImpl implements ClientOrderService
{
    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Override
    public List<ClientOrder> getClientOrders(Long id){
        return clientOrderRepository.findOrdersByClient(id);
    }
}
