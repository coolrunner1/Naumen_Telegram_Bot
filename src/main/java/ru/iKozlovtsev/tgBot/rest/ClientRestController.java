package ru.iKozlovtsev.tgBot.rest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iKozlovtsev.tgBot.entity.Client;
import ru.iKozlovtsev.tgBot.service.ClientService;

import java.util.List;

@RestController
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService)
    {
        this.clientService=clientService;
    }

    @GetMapping(value="/rest/clients/search")
    public List<Client> searchClientsByName(@RequestParam String name){
        return clientService.searchClientsByName(name);
    }
}
