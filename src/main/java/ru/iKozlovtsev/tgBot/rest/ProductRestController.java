package ru.iKozlovtsev.tgBot.rest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iKozlovtsev.tgBot.entity.Product;
import ru.iKozlovtsev.tgBot.service.ProductService;

import java.util.List;

@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService)
    {
        this.productService=productService;
    }

    @GetMapping(value="/rest/products/search", params="categoryId")
    public List<Product> getProductsByCategoryId(@RequestParam Long categoryId){
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping(value = "/rest/products/popular")
    public List<Product> getTopPopularProducts(@RequestParam Integer limit) {
        return productService.getTopPopularProducts(limit);
    }

    @GetMapping(value="/rest/products/search", params="name")
    public List<Product> searchProductsByName(@RequestParam String name){
        return productService.searchProductsByName(name);
    }
}
