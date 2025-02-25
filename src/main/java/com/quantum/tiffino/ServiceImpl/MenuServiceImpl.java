package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Category;
import com.quantum.tiffino.Entity.Menu;
import com.quantum.tiffino.Exception.MenuNotFoundException;
import com.quantum.tiffino.Repository.MenuRepository;
import com.quantum.tiffino.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Optional<Menu> getMenuById(Long menuId) {
        return menuRepository.findById(menuId);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu updateMenu(Long menuId, Menu menu) throws MenuNotFoundException {
        Optional<Menu> existingMenu = menuRepository.findById(menuId);
        if (existingMenu.isPresent()) {
            Menu updatedMenu = existingMenu.get();
            updatedMenu.setItemName(menu.getItemName());
            updatedMenu.setStartDate(menu.getStartDate());
            updatedMenu.setEndDate(menu.getEndDate());
            updatedMenu.setCuisineType(menu.getCuisineType());
            updatedMenu.setRegion(menu.getRegion());
            updatedMenu.setCategory(menu.getCategory());
            updatedMenu.setRestaurant(menu.getRestaurant());
            updatedMenu.setImageUrl(menu.getImageUrl());
            return menuRepository.save(updatedMenu);
        } else {
            throw new MenuNotFoundException("Menu not found for ID: " + menuId);
        }
    }

    @Override
    public void deleteMenu(Long menuId) throws MenuNotFoundException {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            menuRepository.delete(menu.get());
        } else {
            throw new MenuNotFoundException("Menu not found for ID: " + menuId);
        }
    }

    @Override
    public List<Menu> getMenusByCategory(String category) {
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        return menuRepository.findByCategory(categoryEnum);
    }

    @Override
    public List<Menu> getActiveMenus() {
        LocalDate currentDate = LocalDate.now();
        return menuRepository.findActiveMenus(currentDate);
    }


    @Override
    public Menu uploadMenuImage(Long menuId, String imageUrl) throws MenuNotFoundException {
        Optional<Menu> existingMenu = menuRepository.findById(menuId);
        if (existingMenu.isPresent()) {
            Menu menu = existingMenu.get();
            menu.setImageUrl(imageUrl);
            return menuRepository.save(menu);
        } else {
            throw new MenuNotFoundException("Menu not found for ID: " + menuId);
        }
    }
}
