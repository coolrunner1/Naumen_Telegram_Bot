package ru.iKozlovtsev.tgBot.entity;

import jakarta.persistence.*;
import ru.iKozlovtsev.tgBot.entity.Category;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    //@JoinColumn
    private Category category;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 400)
    private String description;

    @Column(nullable = false, columnDefinition="Decimal(15,2)")
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
