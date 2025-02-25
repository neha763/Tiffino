package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Menu;
import com.quantum.tiffino.Exception.MenuNotFoundException;
import com.quantum.tiffino.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin("*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // Create a new menu
    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        Menu savedMenu = menuService.saveMenu(menu);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    // Get a menu by its ID
    @GetMapping("/{menuId}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long menuId) {
        Optional<Menu> menu = menuService.getMenuById(menuId);
        return menu.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    // Get all menus
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    // Update an existing menu
    @PutMapping("/{menuId}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long menuId, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(menuId, menu);
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } catch (MenuNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a menu by ID
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        try {
            menuService.deleteMenu(menuId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MenuNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get menus by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Menu>> getMenusByCategory(@PathVariable("category") String category) {
        List<Menu> menus = menuService.getMenusByCategory(category);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    // Get active menus based on the current date
    @GetMapping("/active")
    public ResponseEntity<List<Menu>> getActiveMenus() {
        List<Menu> activeMenus = menuService.getActiveMenus();
        return new ResponseEntity<>(activeMenus, HttpStatus.OK);
    }

    // Upload and store image URL in the database
    @PutMapping("/{menuId}/uploadImage")
    public ResponseEntity<String> uploadMenuImage(@PathVariable Long menuId, @RequestParam("file") MultipartFile file) {
        try {
            Optional<Menu> menuOptional = menuService.getMenuById(menuId);
            if (!menuOptional.isPresent()) {
                return new ResponseEntity<>("Menu not found", HttpStatus.NOT_FOUND);
            }

            Menu menu = menuOptional.get();

            // Define the storage location (Modify path as needed)
            String uploadDir = "uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();  // Create the folder if it doesn't exist
            }

            // Save file locally with a unique name
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            // Store the image URL in the database
            String imageUrl = "http://localhost:8080/uploads/" + fileName;  // Modify base URL if needed
            menu.setImageUrl(imageUrl);
            menuService.saveMenu(menu);

            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
