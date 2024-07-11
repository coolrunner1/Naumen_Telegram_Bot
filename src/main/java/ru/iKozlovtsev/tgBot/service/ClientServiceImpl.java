package ru.iKozlovtsev.tgBot.service;

import com.pengrad.telegrambot.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    @Override
    public void createClient(User user, String phoneNumber, String address){
        Client client = new Client();
        client.setExternalId(user.id());
        if (clientRepository.exists(Example.of(client))){
            return;
        }
        client.setFullName(user.lastName()+" "+user.firstName());
        client.setPhoneNumber(phoneNumber);
        client.setAddress(address);
        clientRepository.save(client);
    }

    @Override
    public boolean clientExists(Long id)
    {
        Client exampleClient = new Client();
        exampleClient.setExternalId(id);
        return clientRepository.exists(Example.of(exampleClient));
    }

    @Override
    public Client getClientByTelegramId(Long id)
    {
        Client exampleClient = new Client();
        exampleClient.setExternalId(id);
        return clientRepository.findOne(Example.of(exampleClient)).orElse(null);
    }
}
