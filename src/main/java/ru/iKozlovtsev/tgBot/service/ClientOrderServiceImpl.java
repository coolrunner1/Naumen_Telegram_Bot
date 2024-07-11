package ru.iKozlovtsev.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.iKozlovtsev.tgBot.entity.Client;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.repository.ClientOrderRepository;
import ru.iKozlovtsev.tgBot.repository.ClientRepository;

import java.util.List;

@Service
@Transactional
public class ClientOrderServiceImpl implements ClientOrderService
{
    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClientOrder> getClientOrders(Long id){
        return clientOrderRepository.findOrdersByClient(id);
    }

    @Override
    public ClientOrder getOpenedClientOrderByTelegramId(Long id)
    {
        Client exampleClient = new Client();
        exampleClient.setExternalId(id);
        ClientOrder exampleOrder = new ClientOrder();
        exampleOrder.setClient(exampleClient);
        exampleOrder.setStatus(1);
        return clientOrderRepository.findOne(Example.of(exampleOrder)).orElse(null);
    }

    @Override
    public void createClientOrder(Long id)
    {
        Client exampleClient = new Client();
        exampleClient.setExternalId(id);
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(clientRepository.findOne(Example.of(exampleClient)).orElse(null));
        clientOrder.setStatus(1);
        clientOrder.setTotal(1D);
        clientOrderRepository.save(clientOrder);
    }

    @Override
    public void closeOrder(Long id)
    {
        ClientOrder clientOrder = getOpenedClientOrderByTelegramId(id);
        clientOrder.setStatus(2);
        clientOrderRepository.save(clientOrder);
    }
}
