package kz.elshop.elshopdemo.controllers;


import kz.elshop.elshopdemo.entities.*;
import kz.elshop.elshopdemo.service.ItemService;
import kz.elshop.elshopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(Model model){
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("currentUser", getUserData());
        return "admin";
    }

    @GetMapping(value = "/adminBrands")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminBrands(Model model){
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getUserData());
        return "adminBrands";
    }

    @GetMapping(value = "/adminRoles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminRoles(Model model){
        List<Roles> roles = userService.getAllRole();
        model.addAttribute("roles", roles);
        model.addAttribute("currentUser", getUserData());
        return "adminRoles";
    }
    @GetMapping(value = "/adminComments")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String adminComments(Model model){
        List<Comments> comments = itemService.getAllComment();
        model.addAttribute("comments", comments);
        model.addAttribute("currentUser", getUserData());
        return "adminComments";
    }

    @GetMapping(value = "/adminItems")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String adminItems(Model model){
        List<Items> items = itemService.getAllItems();
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("brands", brands);
        model.addAttribute("items", items);
        model.addAttribute("currentUser", getUserData());
        return "adminItems";
    }

    @GetMapping(value = "/adminPurchases")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String adminItemsPurchases(Model model){
        List<Purchase> purchases = itemService.allPurchases();
        model.addAttribute("purchases", purchases);
        model.addAttribute("currentUser", getUserData());
        return "adminPurchases";
    }

    @GetMapping(value = "/adminUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminUsers(Model model){
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        List<Roles> roles = userService.getAllRole();
        model.addAttribute("roles",roles);
        model.addAttribute("currentUser", getUserData());
        return "adminUsers";
    }

    @GetMapping(value = "/managerTools")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String managerTools(Model model){
        model.addAttribute("currentUser", getUserData());
        return "managerTools";
    }

    @GetMapping(value = "/adminCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminCategory(Model model){
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser", getUserData());
        return "adminCategory";
    }

    @PostMapping(value = "/addcountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCountry(@RequestParam(name = "country_name", defaultValue = "")String name,
                           @RequestParam(name = "country_code", defaultValue = "")String code)
                           {

        Countries country = new Countries();

        country.setName(name);
        country.setCode(code);

        itemService.addCountry(country);

        return "redirect:/admin";
    }

    @GetMapping(value = "/editCountry/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCountry(Model model, @PathVariable(name = "id") Long id){
        Countries country = itemService.getCountry(id);
        model.addAttribute("country",country);
        model.addAttribute("currentUser", getUserData());
        return "editCountry";
    }

    @PostMapping(value = "/savecountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCountry(@RequestParam(name = "id", defaultValue = "0")Long id,
                              @RequestParam(name = "country_name", defaultValue = "")String name,
                              @RequestParam(name = "country_code", defaultValue = "")String code){
        Countries countries = itemService.getCountry(id);
        if (countries != null){
            countries.setName(name);
            countries.setCode(code);
            itemService.saveCountries(countries);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/deletecountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCountry(@RequestParam(name = "id", defaultValue = "0") Long id){
        Countries countries = itemService.getCountry(id);
        if (countries!=null) {
            itemService.deleteCountry(countries);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/addbrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBrand(@RequestParam(name = "brands_name", defaultValue = "")String name,
                           @RequestParam(name = "country_id", defaultValue = "")Long countryId)
    {

        Countries country = itemService.getCountry(countryId);
        Brands brand = new Brands();

        brand.setName(name);
        brand.setCountry(country);

        itemService.addBrands(brand);

        return "redirect:/adminBrands";
    }

    @GetMapping(value = "/editBrand/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBrand(Model model, @PathVariable(name = "id") Long id){
        Brands brand = itemService.getBrands(id);
        List<Countries> countries = itemService.getAllCountries();

        model.addAttribute("countries",countries);
        model.addAttribute("brand",brand);
        model.addAttribute("currentUser", getUserData());
        return "editBrands";
    }

    @GetMapping(value = "/editRole/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editRole(Model model, @PathVariable(name = "id") Long id){
        Roles role = userService.getRole(id);

        model.addAttribute("role",role);
        model.addAttribute("currentUser", getUserData());
        return "editRole";
    }

    @PostMapping(value = "/addrole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRole(@RequestParam(name = "role", defaultValue = "")String roleName
                           )
    {
        Roles role = new Roles();

        role.setRole(roleName);


        userService.addRole(role);

        return "redirect:/adminRoles";
    }

    @PostMapping(value = "/savebrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveBrand(@RequestParam(name = "id", defaultValue = "0")Long id,
                              @RequestParam(name = "brand_name", defaultValue = "")String name,
                              @RequestParam(name = "country_id", defaultValue = "")Long cid){
        Brands brands = itemService.getBrands(id);
        if (brands != null){
            Countries countries = itemService.getCountry(cid);
            if (countries!=null) {
                brands.setName(name);
                brands.setCountry(countries);
                itemService.saveBrands(brands);
            }
        }
        return "redirect:/adminBrands";
    }

    @PostMapping(value = "/saverole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveRole(@RequestParam(name = "id", defaultValue = "0")Long id,
                            @RequestParam(name = "role", defaultValue = "")String roleName){
        Roles role = userService.getRole(id);
        if (role != null){

                role.setRole(roleName);
                userService.saveRole(role);
        }
        return "redirect:/adminRoles";
    }

    @PostMapping(value = "/savecomment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String saveComment(@RequestParam(name = "id", defaultValue = "0")Long id,
                           @RequestParam(name = "comment", defaultValue = "")String comment){
        Comments comments = itemService.getComment(id);
        if (comments != null){

            comments.setComment(comment);
            itemService.saveComment(comments);
        }
        return "redirect:/adminComments";
    }

    @PostMapping(value = "/deletecomment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String deleteComment(@RequestParam(name = "id", defaultValue = "0") Long id){
        Comments comments = itemService.getComment(id);
        if (comments!=null) {
            itemService.deleteComment(comments);
        }
        return "redirect:/adminComments";
    }

    @PostMapping(value = "/deleterole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteRole(@RequestParam(name = "id", defaultValue = "0") Long id){
        Roles role = userService.getRole(id);
        if (role!=null) {
            userService.deleteRole(role);
        }
        return "redirect:/adminRoles";
    }

    @PostMapping(value = "/deletebrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBrand(@RequestParam(name = "id", defaultValue = "0") Long id){
        Brands brands = itemService.getBrands(id);
        if (brands!=null) {
            itemService.deleteBrands(brands);
        }
        return "redirect:/adminBrands";
    }

    @PostMapping(value = "/additems")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String addItems(@RequestParam(name = "item_name", defaultValue = "")String name,
                           @RequestParam(name = "item_description", defaultValue = "")String description,
                           @RequestParam(name = "item_price", defaultValue = "0")double price,
                           @RequestParam(name = "item_stars", defaultValue = "0")int stars,
                           @RequestParam(name = "item_small_pic_url", defaultValue = "")String smallPicUrl,
                           @RequestParam(name = "item_large_pic_url",defaultValue = "")String largePicUrl,
                           @RequestParam(name = "item_inTopPage", defaultValue = "False") boolean inTopPage,
                           @RequestParam(name = "brand_id", defaultValue = "0")Long brandId
                           ){
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

        Brands brand = itemService.getBrands(brandId);

        if (brandId!=null){
            Items item = new Items();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setStars(stars);
            item.setSmallPicUrl(smallPicUrl);
            item.setLargePicUrl(largePicUrl);
            item.setAddedDate(new Timestamp(time));
            item.setInTopPage(inTopPage);
            item.setBrand(brand);
            itemService.addItem(item);
        }

        return "redirect:/adminItems";
    }

    @GetMapping(value = "/editItem/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String editItems(Model model, @PathVariable(name = "id") Long id){

        Items item = itemService.getItem(id);
        model.addAttribute("item",item);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories",categories);
        List<Pictures> pictures = itemService.findAllByItemId(id);
        model.addAttribute("pictures",pictures);
        model.addAttribute("currentUser", getUserData());
        return "editItem";
    }

    @GetMapping(value = "/editComment/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String editComment(Model model, @PathVariable(name = "id") Long id){
        Comments comments = itemService.getComment(id);
        model.addAttribute("comments",comments);
        model.addAttribute("currentUser", getUserData());
        return "editComment";
    }


    @PostMapping(value = "/saveitems")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String saveItems(@RequestParam(name = "id", defaultValue = "0")Long id,
                            @RequestParam(name = "brand_id", defaultValue = "0")Long brandId,
                            @RequestParam(name = "item_name", defaultValue = "")String name,
                            @RequestParam(name = "item_description", defaultValue = "")String description,
                            @RequestParam(name = "item_price", defaultValue = "0")double price,
                            @RequestParam(name = "item_stars", defaultValue = "0")int stars,
                            @RequestParam(name = "item_small_pic_url", defaultValue = "")String smallPicUrl,
                            @RequestParam(name = "item_large_pic_url",defaultValue = "")String largePicUrl,
                            @RequestParam(name = "item_inTopPage", defaultValue = "False") boolean inTopPage){
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

        Items item = itemService.getItem(id);

        if (item!=null){
            Brands brand = itemService.getBrands(brandId);
            if (brand!=null) {
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
                item.setStars(stars);
                item.setSmallPicUrl(smallPicUrl);
                item.setLargePicUrl(largePicUrl);
                item.setInTopPage(inTopPage);
                item.setBrand(brand);
                itemService.saveItem(item);
            }
        }

        return "redirect:/adminItems";
    }

    @PostMapping(value = "/deleteitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String deleteItem(@RequestParam(name = "id", defaultValue = "0") Long id){
        Items items = itemService.getItem(id);
        if (items!=null) {
            itemService.deleteItem(items);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/assigncategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String assignCategory(@RequestParam(name = "item_id", defaultValue = "0") Long itemId,
                                 @RequestParam(name = "category_id", defaultValue = "0") Long categoryId){
        Categories cat = itemService.getCategories(categoryId);
        if (cat!=null){
            Items item = itemService.getItem(itemId);
            if (item!=null){
                List<Categories> categories = item.getCategories();
                if (categories==null){
                    categories = new ArrayList<>();
                }
                categories.add(cat);

                itemService.saveItem(item);
                return "redirect:/editItem/" + itemId;
            }
        }
        return "redirect:/adminItems";

    }

    @PostMapping(value = "/minuscategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String minusCategory(@RequestParam(name = "item_id", defaultValue = "0") Long itemId,
                                 @RequestParam(name = "category_id", defaultValue = "0") Long categoryId){
        Categories cat = itemService.getCategories(categoryId);
        if (cat!=null){
            Items item = itemService.getItem(itemId);
            if (item!=null){
                List<Categories> categories = item.getCategories();
                if (categories!=null){
//                    categories = new ArrayList<>();
                    categories.remove(cat);
                }
//                categories.add(cat);

                itemService.saveItem(item);
                return "redirect:/editItem/" + itemId;
            }
        }
        return "redirect:/adminItems";

    }

    @PostMapping(value = "/savecategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCategory(@RequestParam(name = "id", defaultValue = "0")Long id,
                              @RequestParam(name = "category_name", defaultValue = "")String name,
                              @RequestParam(name = "category_url", defaultValue = "")String url){
        Categories categories = itemService.getCategories(id);
        if (categories != null){
            categories.setName(name);
            categories.setLogoURL(url);
            itemService.saveCategories(categories);
        }
        return "redirect:/adminCategory";
    }

    @PostMapping(value = "/addcategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCategory(@RequestParam(name = "category_name", defaultValue = "")String name,
                             @RequestParam(name = "category_url", defaultValue = "")String logo)
    {

        Categories categories = new Categories();

        categories.setName(name);
        categories.setLogoURL(logo);

        itemService.addCategories(categories);

        return "redirect:/adminCategory";
    }

    @GetMapping(value = "/editCategory/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategory(Model model, @PathVariable(name = "id") Long id){
        Categories category = itemService.getCategories(id);
        model.addAttribute("category",category);
        model.addAttribute("currentUser", getUserData());
        return "editCategory";
    }

    @PostMapping(value = "/deletecategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategory(@RequestParam(name = "id", defaultValue = "0") Long id){
        Categories categories = itemService.getCategories(id);
        if (categories!=null) {
            itemService.deleteCategories(categories);
        }
        return "redirect:/adminCategory";
    }

    @GetMapping(value = "/editUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUsers(Model model, @PathVariable(name = "id") Long id){

        Users user = userService.getUser(id);
        model.addAttribute("user",user);
        List<Roles> roles = userService.getAllRole();
        model.addAttribute("roles",roles);
        model.addAttribute("currentUser", getUserData());
        return "editUser";
    }

    @PostMapping(value = "/assignrole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String assignRole(@RequestParam(name = "user_id", defaultValue = "0") Long userId,
                                 @RequestParam(name = "role_id", defaultValue = "0") Long roleId){
        Roles role = userService.getRole(roleId);
        if (role!=null){
            Users user = userService.getUser(userId);
            if (user!=null){
                List<Roles> roles = user.getRoles();
                if (roles==null){
                    roles = new ArrayList<>();
                }
                roles.add(role);

                userService.saveUser(user);
                return "redirect:/editUser/" + userId;
            }
        }
        return "redirect:/adminUsers";

    }

    @PostMapping(value = "/minusrole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String minusRole(@RequestParam(name = "user_id", defaultValue = "0") Long userId,
                                @RequestParam(name = "role_id", defaultValue = "0") Long roleId){
        Roles role = userService.getRole(roleId);
        if (role!=null){
            Users user = userService.getUser(userId);
            if (user!=null){
                List<Roles> roles = user.getRoles();
                if (roles!=null){
//                    roles = new ArrayList<>();
                    roles.remove(role);
                }
//                categories.add(cat);

                userService.saveUser(user);
                return "redirect:/editUser/" + userId;
            }
        }
        return "redirect:/adminUsers";

    }

    @PostMapping(value = "/saveuser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveUserProfile(@RequestParam(name = "id", defaultValue = "0")Long id,
                                  @RequestParam(name = "email", defaultValue = "")String email,
                                  @RequestParam(name = "full_name", defaultValue = "")String full_name){

        Users user = userService.getUser(id);

        if (user!=null){
            user.setEmail(email);
            user.setFullName(full_name);
            userService.saveUser(user);
        }

        return "redirect:/adminUsers";
    }

    @PostMapping(value = "/deleteuser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@RequestParam(name = "id", defaultValue = "0") Long id){
        Users users = userService.getUser(id);
        if (users!=null) {
            userService.deleteUser(users);
        }
        return "redirect:/adminUsers";
    }

    @PostMapping(value = "/addusers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUsers(@RequestParam(name = "email", defaultValue = "")String email,
                           @RequestParam(name = "password", defaultValue = "")String password,
                           @RequestParam(name = "full_name", defaultValue = "0")String full_name,
                           @RequestParam(name = "role", defaultValue = "0")String role
    ){
        Users user = new Users();

            Users u = userService.getUserByEmail(email);
            if (u==null){
                user.setEmail(email);
//                user.setPassword(passwordEncoder.encode(accountDto.getPassword()));

                user.setPassword(bCryptPasswordEncoder.encode(password));
                user.setFullName(full_name);
                user.setRoles(userService.findByRole(role));
                userService.addUser(user);
            }

        return "redirect:/adminUsers";
    }

    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }
}
