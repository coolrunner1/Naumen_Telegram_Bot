package ru.iKozlovtsev.tgBot.rest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iKozlovtsev.tgBot.entity.Client;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.entity.Product;
import ru.iKozlovtsev.tgBot.service.ClientOrderService;
import ru.iKozlovtsev.tgBot.service.ClientService;
import ru.iKozlovtsev.tgBot.service.ProductService;

import java.util.List;

@RestController
public class ClientRestController {
    private final ClientService clientService;
    private final ProductService productService;
    private final ClientOrderService clientOrderService;

    public ClientRestController(ClientService clientService, ProductService productService, ClientOrderService clientOrderService)
    {
        this.clientService=clientService;
        this.productService=productService;
        this.clientOrderService=clientOrderService;
    }


    @GetMapping(value="/rest/clients/search")
    public List<Client> searchClientsByName(@RequestParam String name){
        return clientService.searchClientsByName(name);
    }

    @GetMapping(value = "/rest/clients/{id}/products")
    public List<Product> getClientProducts(@PathVariable Long id){
        return productService.getClientProducts(id);
    }

    @GetMapping(value = "/rest/clients/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id){
        return clientOrderService.getClientOrders(id);
    }
}
