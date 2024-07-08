package ru.iKozlovtsev.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iKozlovtsev.tgBot.entity.Client;

@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")

public interface ClientRepository extends JpaRepository<Client, Long>
{
}
