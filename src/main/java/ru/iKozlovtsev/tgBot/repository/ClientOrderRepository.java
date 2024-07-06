package ru.iKozlovtsev.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long>
{
}
