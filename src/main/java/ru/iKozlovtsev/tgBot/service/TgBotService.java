package ru.iKozlovtsev.tgBot.service;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.pengrad.telegrambot.TelegramBot;
import ru.iKozlovtsev.tgBot.entity.ClientOrder;
import ru.iKozlovtsev.tgBot.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TgBotService
{
    private final TelegramBot bot = new TelegramBot("6483798891:AAELbdPew0EpUoMOlibq8uYQUFt-O-j0Uxs");
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ClientService clientService;
    private final ClientOrderService clientOrderService;
    private final OrderProductService orderProductService;

    //Constants
    static final int REGISTRATION=1;
    static final int ADDRESS=2;
    static final int PHONE=3;
    static final int MENU=4;

    //Booleans
    private Boolean addressFilled = false;
    private Boolean phoneNumberFilled = false;
    private Boolean orderIsEmpty = true;

    //Strings
    private String address;
    private String phoneNumber;

    //Ids
    private Long categoryId=null;

    int currentScreen=REGISTRATION;

    public ReplyKeyboardMarkup backMarkup()
    {
        ReplyKeyboardMarkup markup = new
                ReplyKeyboardMarkup("Назад");
        markup.resizeKeyboard(true);
        return markup;
    }

    public TgBotService(ProductService productService, CategoryService categoryService, ClientService clientService, ClientOrderService clientOrderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.clientService = clientService;
        this.clientOrderService = clientOrderService;
        this.orderProductService = orderProductService;
    }

    public ClientOrder getOpenedOrder(Long id)
    {
        ClientOrder clientOrder = clientOrderService.getOpenedClientOrderByTelegramId(id);
        if (clientOrder==null)
        {
            clientOrderService.createClientOrder(id);
            return getOpenedOrder(id);
        }
        return clientOrder;
    }

    public void erroneousInput(Update update)
    {
        bot.execute(new SendMessage(update.message().chat().id(),
                "Неверный ввод!").replyMarkup(backMarkup()));
    }

    public class RegistrationMessages
    {
        Update update;

        public RegistrationMessages(Update update)
        {
            this.update=update;
        }

        private ReplyKeyboardMarkup registrationMarkup()
        {
            ReplyKeyboardMarkup markup;
            markup = new ReplyKeyboardMarkup("Адрес", "Телефон");
            markup.resizeKeyboard(true);
            if (phoneNumberFilled && addressFilled)
            {
                markup.addRow(new KeyboardButton("Отправить данные"));
            }
            return markup;
        }

        public void addressMessage()
        {
            bot.execute(new SendMessage(update.message().chat().id(),
                    "Введите свой адрес:").replyMarkup(backMarkup()));
        }

        public void phoneMessage()
        {
            bot.execute(new SendMessage(update.message().chat().id(),
                    "Введите свой номер телефона:").replyMarkup(backMarkup()));
        }

        public void registrationMessage()
        {
            bot.execute(new SendMessage(update.message().chat().id(),
                    "Здравствуйте, "+update.message().from().firstName()+"!\n" +
                            "Добро пожаловать в телеграм бот ресторана быстрого питания \"Снежинка\"!\n" +
                            "Для того, чтобы пользоваться данным ботом, необходимо пройти регистрацию.").replyMarkup(registrationMarkup()));
        }
    }
    /**
     * Class for user registration
     */
    public class Registration{

        private final Update update;
        private final String userMessage;
        private final RegistrationMessages registrationMessages;

        public Registration(Update update)
        {
            this.update=update;
            this.userMessage=update.message().text();
            registrationMessages=new RegistrationMessages(update);
        }

        /**
         * This method checks if string is convertible to Long
         * @param input string
         * @return boolean
         */
        public boolean isLong(String input) {
            try
            {
                Long.parseLong( input );
                return true;
            }
            catch( Exception e )
            {
                return false;
            }
        }

        public int addressForm()
        {
            switch (userMessage)
            {
                case "Назад" ->
                {
                    registrationMessages.registrationMessage();
                    return REGISTRATION;
                }
                default ->
                {
                    address=userMessage;
                    addressFilled=true;
                    bot.execute(new SendMessage(update.message().chat().id(),
                            "Успешно принят адресс!"));
                    registrationMessages.registrationMessage();
                    return REGISTRATION;
                }
            }
        }

        private int phoneForm(){
            switch (userMessage)
            {
                case "Назад" ->
                {
                    registrationMessages.registrationMessage();
                    return REGISTRATION;
                }
                default ->
                {
                    if (!(userMessage.length()==12 && userMessage.charAt(0)=='+' && isLong(userMessage))){
                        erroneousInput(update);
                        registrationMessages.phoneMessage();
                        return PHONE;
                    }
                    phoneNumber=userMessage;
                    phoneNumberFilled=true;
                    bot.execute(new SendMessage(update.message().chat().id(),
                            "Успешно принят номер!"));
                    registrationMessages.registrationMessage();
                    return REGISTRATION;
                }
            }

        }

        public int registrationForm()
        {
            registrationMessages.registrationMessage();
            switch (userMessage){
                case "/start" ->
                {

                }
                case "Адрес" ->
                {
                    registrationMessages.addressMessage();
                    return ADDRESS;
                }
                case "Телефон" ->
                {
                    registrationMessages.phoneMessage();
                    return PHONE;
                }
                case "Отправить данные" ->
                {
                    if (!phoneNumberFilled || !addressFilled){
                        erroneousInput(update);
                        return REGISTRATION;
                    }
                    clientService.createClient(update.message().from(), phoneNumber, address);
                    return MENU;
                }
                case "Назад" ->
                {
                    return REGISTRATION;
                }
                default ->
                {
                    erroneousInput(update);
                }
            }
            return REGISTRATION;
        }
    }

    public class MainMenuMessages
    {
        private final Update update;

        MainMenuMessages(Update update){
            this.update=update;
        }

        private ReplyKeyboardMarkup itemMenuMarkup(Long id)
        {
            List<KeyboardButton> categories = categoryService.getCategoriesByParentId(id)
                    .stream()
                    .map(category -> new KeyboardButton(category.getName()))
                    .collect(Collectors.toList());
            ReplyKeyboardMarkup markup = new
                    ReplyKeyboardMarkup(categories.toArray(KeyboardButton[]::new));
            markup.resizeKeyboard(true);
            markup.addRow(new KeyboardButton("Оформить заказ"));
            if (id!=null)
            {
                markup.addRow(new KeyboardButton("В основное меню"));
            }
            return markup;
        }

        private InlineKeyboardMarkup listOfMenuItemsMarkup(Long id)

        {
            List<Product> products = productService.getProductsByCategoryId(id);

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

            for(Product product: products){
                InlineKeyboardButton button = new
                        InlineKeyboardButton(String.format("Товар %s. Цена %.2f руб.",
                        product.getName(), product.getPrice()))
                        .callbackData(String.format("product:%d", product.getId()));
                markup.addRow(button);
            }
            return markup;
        }


        public void itemMenuMessage(Long categoryId)
        {
            if (update.callbackQuery()==null)
            {
                bot.execute(new SendMessage(update.message().chat().id(),
                        "Добро пожаловать в ресторан быстрого питания \"Снежинка\"").replyMarkup(itemMenuMarkup(categoryId)));
                if (categoryId!=null)
                {
                    bot.execute(new SendMessage(update.message().chat().id(),
                            "Меню ресторана \"Снежинка\"").replyMarkup(itemMenuMarkup(categoryId)).replyMarkup(listOfMenuItemsMarkup(categoryId)));
                }
            }
        }
    }

    public class MainMenu
    {
        private final Update update;
        private final MainMenuMessages mainManuMessages;
        Long categoryId = null;

        MainMenu(Update update)
        {
            this.update=update;
            mainManuMessages = new MainMenuMessages(update);
        }

        public Long itemMenu(Long categoryId)
        {
            if (update.callbackQuery()!=null)
            {
                ClientOrder clientOrder = getOpenedOrder(update.callbackQuery().from().id());
                Long chatId = update.callbackQuery().from().id();
                String data = update.callbackQuery().data();

                System.out.println(data);

                if (data.contains("product:"))
                {
                    System.out.println(data.substring(data.lastIndexOf(":") + 1));
                    orderProductService.createOrderProduct(clientOrder, Long.parseLong(data.substring(data.lastIndexOf(":") + 1)));
                    orderIsEmpty=false;
                }
            }
            else
            {
                //mainManuMessages.itemMenuMessage(categoryId);
                String userMessage = update.message().text();
                switch (userMessage)
                {
                    case "/start" ->
                    {
                        bot.execute(new SendMessage(update.message().chat().id(),
                                "Рады вас снова видеть в телеграм боте ресторана \"Снежинка\""));
                        mainManuMessages.itemMenuMessage(categoryId);
                    }
                    case "Назад" ->
                    {
                        mainManuMessages.itemMenuMessage(null);
                        return null;
                    }

                    case "В основное меню" ->
                    {
                        mainManuMessages.itemMenuMessage(null);
                        return null;
                    }

                    case "Оформить заказ" ->
                    {
                        if(orderIsEmpty)
                        {
                            bot.execute(new SendMessage(update.message().chat().id(),
                                    "Заказ пустой!").replyMarkup(backMarkup()));
                        }
                        else
                        {
                            clientOrderService.closeOrder(update.message().from().id());
                            bot.execute(new SendMessage(update.message().chat().id(),
                                    "Заказ был создан успешно!").replyMarkup(backMarkup()));
                        }
                        return null;
                    }

                    default ->
                    {
                        categoryId = categoryService.getCategoryIdByName(userMessage);

                        if (categoryId==null)
                        {
                            erroneousInput(update);
                            return null;
                        }

                        mainManuMessages.itemMenuMessage(categoryId);
                        return categoryId;
                    }
                }

            }
            mainManuMessages.itemMenuMessage(categoryId);
            return null;
        }


    }


    @PostConstruct
    private void start()
    {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void processUpdate(Update update)
    {
        if (update.callbackQuery()==null)
        {
            if (!clientService.clientExists(update.message().from().id()))
            {
                Registration registration = new Registration(update);
                switch (currentScreen){
                    case REGISTRATION -> {
                        currentScreen=registration.registrationForm();
                    }
                    case ADDRESS -> {
                        currentScreen=registration.addressForm();
                    }
                    case PHONE -> {
                        currentScreen=registration.phoneForm();
                    }
                }
                return;
            }
        }

        MainMenu mainMenu = new MainMenu(update);
        categoryId=mainMenu.itemMenu(categoryId);
    }
}
