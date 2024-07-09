package ru.iKozlovtsev.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long>
{
    @Query(value = "from ClientOrder ord join OrderProduct ord_prod on ord.id=ord_prod.clientOrder.id join Product prod on ord_prod.product.id=prod.id where ord.client.id=:id")
    List<ClientOrder> findOrdersByClient(Long id);
}
