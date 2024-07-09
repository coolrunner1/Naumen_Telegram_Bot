package ru.iKozlovtsev.tgBot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.service.ClientOrderService;

import java.util.List;

@RestController
public class ClientOrderRestController {
    private final ClientOrderService clientOrderService;

    public ClientOrderRestController(ClientOrderService clientOrderService)
    {
        this.clientOrderService=clientOrderService;
    }

    @GetMapping(value = "/rest/clients/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id){
        return clientOrderService.getClientOrders(id);
    }
}
