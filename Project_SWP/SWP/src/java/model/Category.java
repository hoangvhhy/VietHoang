/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author jaxbo
 */
public class Category {
    private int category_id;
    private String name;
    private String description;
    private int status;

    public Category() {
    }

    public Category(int category_id, String name, String description, int status) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    

    @Override
    public String toString() {
        return "Category{" + "category_id=" + category_id + ", name=" + name + ", description=" + description + ", status=" + status + '}';
    }
    
    
}
