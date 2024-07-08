package ru.iKozlovtsev.tgBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iKozlovtsev.tgBot.entity.*;
import ru.iKozlovtsev.tgBot.repository.*;

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
        classicRolls.setParent(categoryRepository.findCategoryByName("Роллы"));
        categoryRepository.save(classicRolls);

        Category bakedRolls = new Category();
        bakedRolls.setName("Запечённые роллы");
        bakedRolls.setParent(categoryRepository.findCategoryByName("Роллы"));
        categoryRepository.save(bakedRolls);

        Category sweetRolls = new Category();
        sweetRolls.setName("Сладкие роллы");
        sweetRolls.setParent(categoryRepository.findCategoryByName("Роллы"));
        categoryRepository.save(sweetRolls);

        Category sets = new Category();
        sets.setName("Наборы");
        sets.setParent(categoryRepository.findCategoryByName("Роллы"));
        categoryRepository.save(sets);

        Category classicBurgers = new Category();
        classicBurgers.setName("Классические бургеры");
        classicBurgers.setParent(categoryRepository.findCategoryByName("Бургеры"));
        categoryRepository.save(classicBurgers);

        Category spicyBurgers = new Category();
        spicyBurgers.setName("Острые бургеры");
        spicyBurgers.setParent(categoryRepository.findCategoryByName("Бургеры"));
        categoryRepository.save(spicyBurgers);

        Category carbonatedDrinks = new Category();
        carbonatedDrinks.setName("Газированные напитки");
        carbonatedDrinks.setParent(categoryRepository.findCategoryByName("Напитки"));
        categoryRepository.save(carbonatedDrinks);

        Category energyDrinks = new Category();
        energyDrinks.setName("Энергетические напитки");
        energyDrinks.setParent(categoryRepository.findCategoryByName("Напитки"));
        categoryRepository.save(energyDrinks);

        Category juices = new Category();
        juices.setName("Соки");
        juices.setParent(categoryRepository.findCategoryByName("Напитки"));
        categoryRepository.save(juices);

        Category otherDrinks = new Category();
        otherDrinks.setName("Другие");
        otherDrinks.setParent(categoryRepository.findCategoryByName("Напитки"));
        categoryRepository.save(otherDrinks);
    }

    @Test
    void createProducts(){
        Product pineapplePizza =  new Product();
        pineapplePizza.setName("Pineapple Pizza");
        pineapplePizza.setCategory(categoryRepository.findCategoryByName("Пицца"));
        pineapplePizza.setDescription("Pineapple pizza is topped with pineapple, tomato sauce, mozzarella cheese, and either ham or bacon");
        pineapplePizza.setPrice(79445.34);
        productRepository.save(pineapplePizza);

        Product  grapeJuice = new Product();
        grapeJuice.setName("Grape Juice");
        grapeJuice.setDescription("Juice made of grapes");
        grapeJuice.setPrice(5849234.324324);
        grapeJuice.setCategory(categoryRepository.findCategoryByName("Соки"));
        productRepository.save(grapeJuice);

        Product orangeJuice = new Product();
        orangeJuice.setName("Orange Juice");
        orangeJuice.setDescription("Juice made of oranges");
        orangeJuice.setCategory(categoryRepository.findCategoryByName("Соки"));
        orangeJuice.setPrice(39428.324324);
        productRepository.save(orangeJuice);
    }

    @Test
    void createOrders(){
        ClientOrder order1 = new ClientOrder();
        order1.setClient(clientRepository.findById(1L).orElse( null));
        order1.setStatus(1);
        order1.setTotal(2423.234);
        clientOrderRepository.save(order1);

        ClientOrder order2 = new ClientOrder();
        order2.setClient(clientRepository.findById(2L).orElse( null));
        order2.setStatus(1);
        order2.setTotal(2423.234);
        clientOrderRepository.save(order2);
    }

    @Test
    void createOrderProductRelations(){
        OrderProduct op1 = new OrderProduct();
        op1.setProduct(productRepository.findByName("Grape Juice"));
        op1.setClientOrder(clientOrderRepository.findById(1L).orElse( null));
    }
}
