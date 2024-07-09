package ru.iKozlovtsev.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iKozlovtsev.tgBot.entity.Client;
import ru.iKozlovtsev.tgBot.repository.ClientRepository;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService
{
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> searchClientsByName(String name)
    {
        return clientRepository.searchClientsByNameContaining(name);
    }
}
