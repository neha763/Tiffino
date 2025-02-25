package com.quantum.tiffino.Entity;



import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the region (e.g., "Punjab", "Maharashtra", "South India")
    private String name;

    // List of menus available in this region
    @OneToMany(mappedBy = "region")
    private List<Menu> menus;  // Menu items available in the region

    // Default constructor
    public Region() {}

    // Constructor with parameters for easy initialization
    public Region(String name) {
        this.name = name;
    }

    // Getters and Setters
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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    // toString method for easy object representation
    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
