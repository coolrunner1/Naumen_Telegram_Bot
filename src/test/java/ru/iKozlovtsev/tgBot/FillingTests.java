package ru.iKozlovtsev.tgBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iKozlovtsev.tgBot.entity.*;
import ru.iKozlovtsev.tgBot.repository.*;

import javax.management.Query;

@SpringBootTest
class FillingTests
{
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    void createTwoClients(){
        Client client1 = new Client();
        client1.setAddress("address1");
        client1.setExternalId(1L);
        client1.setFullName("fullName1");
        client1.setPhoneNumber("+94385400000000");
        clientRepository.save(client1);
        Client client2 = new Client();
        client2.setAddress("address2S");
        client2.setExternalId(2L);
        client2.setFullName("fullName2");
        client2.setPhoneNumber("+94385");
        clientRepository.save(client2);
    }

    @Test
    void createMainCategories() {
        Category pizza = new Category();
        pizza.setName("Пицца");
        categoryRepository.save(pizza);
        Category rolls = new Category();
        rolls.setName("Роллы");
        categoryRepository.save(rolls);
        Category burgers = new Category();
        burgers.setName("Бургеры");
        categoryRepository.save(burgers);
        Category drinks = new Category();
        drinks.setName("Напитки");
        categoryRepository.save(drinks);
    }

    @Test
    void createSubcategories() {
        Category classicRolls = new Category();
        classicRolls.setName("Классические Роллы");
        classicRolls.setParent(getCategoryByName("Роллы"));
        categoryRepository.save(classicRolls);
        Category bakedRolls = new Category();
        bakedRolls.setName("Запечённые роллы");
        bakedRolls.setParent(getCategoryByName("Роллы"));
        categoryRepository.save(bakedRolls);
        Category sweetRolls = new Category();
        sweetRolls.setName("Сладкие роллы");
        sweetRolls.setParent(getCategoryByName("Роллы"));
        categoryRepository.save(sweetRolls);
        Category sets = new Category();
        sets.setName("Наборы");
        sets.setParent(getCategoryByName("Роллы"));
        categoryRepository.save(sets);
        Category classicBurgers = new Category();
        classicBurgers.setName("Классические бургеры");
        classicBurgers.setParent(getCategoryByName("Бургеры"));
        categoryRepository.save(classicBurgers);
        Category spicyBurgers = new Category();
        spicyBurgers.setName("Острые бургеры");
        spicyBurgers.setParent(getCategoryByName("Бургеры"));
        categoryRepository.save(spicyBurgers);
        Category carbonatedDrinks = new Category();
        carbonatedDrinks.setName("Газированные напитки");
        carbonatedDrinks.setParent(getCategoryByName("Напитки"));
        categoryRepository.save(carbonatedDrinks);
        Category energyDrinks = new Category();
        energyDrinks.setName("Энергетические напитки");
        energyDrinks.setParent(getCategoryByName("Напитки"));
        categoryRepository.save(energyDrinks);
        Category juices = new Category();
        juices.setName("Соки");
        juices.setParent(getCategoryByName("Напитки"));
        categoryRepository.save(juices);
        Category otherDrinks = new Category();
        otherDrinks.setName("Другие");
        otherDrinks.setParent(getCategoryByName("Напитки"));
        categoryRepository.save(otherDrinks);
    }

    Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name);
    }

    @Test
    void createProducts(){
        Product pineapplePizza =  new Product();
        pineapplePizza.setName("Pineapple Pizza");
        pineapplePizza.setCategory(getCategoryByName("Пицца"));
        pineapplePizza.setDescription("Pineapple pizza is topped with pineapple, tomato sauce, mozzarella cheese, and either ham or bacon");
        pineapplePizza.setPrice(79445.34);
        productRepository.save(pineapplePizza);
        Product  grapeJuice = new Product();
        grapeJuice.setName("Grape Juice");
        grapeJuice.setDescription("Juice made of grapes");
        grapeJuice.setPrice(5849234.324324);
        grapeJuice.setCategory(getCategoryByName("Соки"));
        productRepository.save(grapeJuice);
        Product  orangeJuice = new Product();
        orangeJuice.setName("Orange Juice");
        orangeJuice.setDescription("Juice made of oranges");
        orangeJuice.setCategory(getCategoryByName("Соки"));
        orangeJuice.setPrice(39428.324324);
        productRepository.save(orangeJuice);
    }

    @Test
    void createOrders(){
        ClientOrder order1 = new ClientOrder();
        order1.setClient(getClientByID(1L));
        order1.setStatus(1);
        order1.setTotal(2423.234);
        clientOrderRepository.save(order1);
        ClientOrder order2 = new ClientOrder();
        order2.setClient(getClientByID(2L));
        order2.setStatus(1);
        order2.setTotal(2423.234);
        clientOrderRepository.save(order2);
    }

    Client getClientByID(Long ID){
        return clientRepository.findById(ID).orElse( null);
    }

    @Test
    void createOrderProductRelations(){
        OrderProduct op1 = new OrderProduct();
        op1.setProduct(productRepository.findByName("Grape Juice"));
        op1.setClientOrder(getOrderByID(1L));
    }

    ClientOrder getOrderByID(Long ID){
        return clientOrderRepository.findById(ID).orElse( null);
    }
}
