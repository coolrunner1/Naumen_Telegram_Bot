package ru.iKozlovtsev.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.iKozlovtsev.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product findByName(String name);

    @Query("from Product prod " +
            "join OrderProduct ord_prod on prod.id=ord_prod.product.id " +
            "join ClientOrder ord on ord_prod.clientOrder.id=ord.id " +
            "where ord.client.id=:id")
    List<Product> findClientProducts(Long id);

    @Query("from Product prod " +
            "join OrderProduct ord_prod on prod.id=ord_prod.product.id " +
            "group by prod.id " +
            "order by sum(ord_prod.countProduct) " +
            "desc limit :limit")
    List<Product> findTopPopularProducts(Integer limit);

    @Query("from Product prod where prod.name ilike %:name%")
    List<Product> searchProductsByNameContaining(String name);
 }