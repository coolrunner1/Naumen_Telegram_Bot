package ru.iKozlovtsev.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.entity.OrderProduct;
import ru.iKozlovtsev.tgBot.entity.Product;
import ru.iKozlovtsev.tgBot.repository.OrderProductRepository;
import ru.iKozlovtsev.tgBot.repository.ProductRepository;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService{
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void createOrderProduct(ClientOrder clientOrder, Long productId){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setClientOrder(clientOrder);
        orderProduct.setCountProduct(1);
        Product exampleProduct = new Product();
        exampleProduct.setId(productId);
        orderProduct.setProduct(productRepository.findOne(Example.of(exampleProduct)).orElse(null));
        orderProduct.setCountProduct(1);
        orderProductRepository.save(orderProduct);
    }
}
