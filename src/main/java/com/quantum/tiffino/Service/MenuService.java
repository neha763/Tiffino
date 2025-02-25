package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Menu;
import com.quantum.tiffino.Exception.MenuNotFoundException;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    Menu saveMenu(Menu menu);
    Optional<Menu> getMenuById(Long menuId);
    List<Menu> getAllMenus();
    Menu updateMenu(Long menuId, Menu menu) throws MenuNotFoundException;
    void deleteMenu(Long menuId) throws MenuNotFoundException;

    List<Menu> getMenusByCategory(String category);
    List<Menu> getActiveMenus();

    // Upload image URL instead of storing image as byte[]
    Menu uploadMenuImage(Long menuId, String imageUrl) throws MenuNotFoundException;
}
