package kz.elshop.elshopdemo.controllers;

import kz.elshop.elshopdemo.entities.*;
import kz.elshop.elshopdemo.service.ItemService;
import kz.elshop.elshopdemo.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("static/pictures")
    private String viewPathPic;

    @Value("target/classes/static/pictures")
    private String uploadPathPic;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @GetMapping(value = "/")
    public String index(Model model){
        List<Items> item = itemService.isTopPageFalse();
        List<Items> items = itemService.isTopPage();
        items.addAll(item);
        model.addAttribute("items", items);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getUserData());
        return "index";
    }

    @GetMapping(value = "/istop")
    public String inTopPage(Model model){
        List<Items> items = itemService.isTopPageFalse();
        List<Items> itemsTop = itemService.isTopPage();
        itemsTop.addAll(items);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("items", itemsTop);
        model.addAttribute("currentUser", getUserData());
        return "index";
    }

//    @GetMapping(value = "/admin")
//    public String admin(Model model){
//        List<Items> items = itemService.getAllItems();
//        List<Brands> brands = itemService.getAllBrands();
//        List<Countries> countries = itemService.getAllCountries();
//        model.addAttribute("countries", countries);
//        model.addAttribute("brands", brands);
//        model.addAttribute("items", items);
//        return "admin";
//    }

    @GetMapping(value = "/detailedSearch")
    public String detailedSearch(Model model,
                                 @RequestParam(name = "item_name", defaultValue = "")String name){
        List<Items> items = itemService.byName(name);
        List<Brands> brands = itemService.getAllBrands();
        String name2 = name;
        model.addAttribute("name2", name2);
        model.addAttribute("items", items);
        model.addAttribute("brands",brands);
        model.addAttribute("currentUser", getUserData());
        return "detailedSearch";
    }

    @GetMapping(value = "/brandSearch/{id}")
    public String brandSearch(Model model,
                              @PathVariable(name = "id")Long brandId){
        if (brandId!=null) {

            List<Brands> brands = itemService.getAllBrands();

            if (brands!=null) {
                List<Items> items = itemService.byBrandIdPriceAsc(brandId);
                System.out.println(items);
                model.addAttribute("items", items);
                model.addAttribute("brands",brands);
                model.addAttribute("brandId", brandId);
                model.addAttribute("currentUser", getUserData());
            }
        }
//        List<Items> items = itemService.byName(name);
//        model.addAttribute("items", items);

//        model.addAttribute("brands",brands);
        return "detailedSearch";
    }

    @GetMapping(value = "/search")
    public String search(Model model,
                         @RequestParam(name = "item_name", defaultValue = "")String name,
                         @RequestParam(name = "item_price_from", defaultValue = "0")double priceFrom,
                         @RequestParam(name = "item_price_to", defaultValue = "0")double priceTo,
                         @RequestParam(name = "options")String options,
                         @RequestParam(name = "brand_id", defaultValue = "")Long brandId
                         ){
        List<Items> items;
        List<Brands> brands = itemService.getAllBrands();
        if (options.equals("asc")) {

            if (!name.equals("") && priceFrom != 0 && priceTo != 0 && brandId !=0) {
//                items = itemService.byNamePriceBetweenAsc(name, priceFrom, priceTo);
                items = itemService.byBrandIdNamePriceBetweenAsc(brandId, name, priceFrom, priceTo);
                model.addAttribute("items", items);
            }
//            } else if (!name.equals("") && priceFrom != 0 && brandId !=0) {
//                items = itemService.byNamePriceAfterAsc(name, priceFrom);
//            } else if (!name.equals("") && priceTo != 0 && brandId !=0) {
//                items = itemService.byNamePriceBeforeAsc(name, priceTo);
//            } else if (!name.equals("") && brandId !=0) {
//                items = itemService.byNamePriceAsc(name);
//            } else if (priceFrom != 0 && priceTo != 0 && brandId !=0){
//                items = itemService.byPriceBetweenAsc(priceFrom, priceTo);
//            }
        }else if (options.equals("desc")){

            if (!name.equals("") && priceFrom != 0 && priceTo != 0 && brandId !=0) {
//                items = itemService.byNamePriceBetweenDesc(name, priceFrom, priceTo);
                items = itemService.byBrandIdNamePriceBetweenDesc(brandId,name, priceFrom, priceTo);
                model.addAttribute("items", items);
            }
//            } else if (!name.equals("") && priceFrom != 0 && brandId !=0) {
//                items = itemService.byNamePriceAfterDesc(name, priceFrom);
//            } else if (!name.equals("") && priceTo != 0 && brandId !=0) {
//                items = itemService.byNamePriceBeforeDesc(name, priceTo);
//            } else if (!name.equals("") && brandId !=0) {
//                items = itemService.byNamePriceDesc(name);
//            } else if (priceFrom != 0 && priceTo != 0 && brandId !=0){
//                items = itemService.byPriceBetweenDesc(priceFrom, priceTo);
//            }
        }
        String name2 = name;
        model.addAttribute("brands",brands);

        model.addAttribute("currentUser", getUserData());
        model.addAttribute("name2", name2);
        model.addAttribute("sel_brand_id", brandId);
        model.addAttribute("priceFrom", priceFrom);
        model.addAttribute("priceTo", priceTo);

        return "detailedSearch";
    }

//    @PostMapping(value = "/additems")
//    public String addItems(@RequestParam(name = "item_name", defaultValue = "")String name,
//                           @RequestParam(name = "item_description", defaultValue = "")String description,
//                           @RequestParam(name = "item_price", defaultValue = "0")double price,
//                           @RequestParam(name = "item_stars", defaultValue = "0")int stars,
//                           @RequestParam(name = "item_small_pic_url", defaultValue = "")String smallPicUrl,
//                           @RequestParam(name = "item_large_pic_url",defaultValue = "")String largePicUrl,
//                           @RequestParam(name = "item_inTopPage", defaultValue = "False") boolean inTopPage,
//                           @RequestParam(name = "brand_id", defaultValue = "0")Long brandId,
//                           @RequestParam(name = "country_id", defaultValue = "0")Long countryId){
//        Calendar calendar = Calendar.getInstance();
//        java.util.Date currentTime = calendar.getTime();
//        long time = currentTime.getTime();
//
//        Brands brand = itemService.getBrands(brandId);
//        Countries country = itemService.getCountry(countryId);
//
//        if (brandId!=null){
//            Items item = new Items();
//            item.setName(name);
//            item.setDescription(description);
//            item.setPrice(price);
//            item.setStars(stars);
//            item.setSmallPicUrl(smallPicUrl);
//            item.setLargePicUrl(largePicUrl);
//            item.setAddedDate(new Timestamp(time));
//            item.setInTopPage(inTopPage);
//            brand.setCountry(country);
//            item.setBrand(brand);
//            itemService.addItem(item);
//        }
//
//        return "redirect:/";
//    }

    @GetMapping(value = "/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id){
        Items item = itemService.getItem(id);
        List<Brands> brands = itemService.getAllBrands();
        List<Comments> comments = itemService.findAllByItemId1(id);

        model.addAttribute("comments", comments);
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("item", item);
        List<Pictures> pictures = itemService.findAllByItemId(id);
        model.addAttribute("pictures", pictures);
        return "details";
    }


    @GetMapping(value = "/details/{id}/edit/{id}")
    public String edit(Model model, @PathVariable(name = "id") Long id){
        Items item = itemService.getItem(id);

        model.addAttribute("currentUser", getUserData());
        model.addAttribute("item", item);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        return "edit";
    }


    @GetMapping(value = "/403")
    public String accessDenied(Model model){
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getUserData());
        return "403";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getUserData());
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        Users user = userService.getUserByEmail(getUserData().getEmail());
        model.addAttribute("user",user);
        model.addAttribute("currentUser", getUserData());
        return "profile";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model){
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getUserData());
        return "registration";
    }

    @PostMapping(value = "/toregister")
    public String addItems(@RequestParam(name = "email", defaultValue = "")String email,
                           @RequestParam(name = "password", defaultValue = "")String password,
                           @RequestParam(name = "re_password", defaultValue = "0")String re_password,
                           @RequestParam(name = "full_name", defaultValue = "0")String full_name,
                           RedirectAttributes redirAttrs
    ){
        Users user = new Users();

        if (password.equals(re_password)){
            Users u = userService.getUserByEmail(email);
            if (u==null){
                user.setEmail(email);
//                user.setPassword(passwordEncoder.encode(accountDto.getPassword()));

                user.setPassword(bCryptPasswordEncoder.encode(password));
                user.setFullName(full_name);
                user.setRoles(userService.findByRole("ROLE_USER"));
                userService.addUser(user);
                redirAttrs.addFlashAttribute("success", "Registration was successfully.");
            }
            else{
            redirAttrs.addFlashAttribute("error", "User is already exists!");
            }
        }

        return "redirect:/login";
    }

    @PostMapping(value = "/updateprofile")
    @PreAuthorize("isAuthenticated()")
    public String saveUserProfile(@RequestParam(name = "id", defaultValue = "0")Long id,
                           @RequestParam(name = "full_name", defaultValue = "")String full_name,
                                  RedirectAttributes redirAttrs){

        Users user = userService.getUser(id);

        if (user!=null){

                user.setFullName(full_name);
                userService.saveUser(user);
            redirAttrs.addFlashAttribute("successProfile", "Profile was changed successfully.");
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String saveUserPassword(@RequestParam(name = "id", defaultValue = "0")Long id,
                                    @RequestParam(name = "old_password", defaultValue = "")String old_password,
                                   @RequestParam(name = "new_password", defaultValue = "")String new_password,
                                   @RequestParam(name = "re_new_password", defaultValue = "")String re_new_password,
                                   RedirectAttributes redirAttrs){

        Users user = userService.getUser(id);

        if (user!=null){
            if(new_password.equals(re_new_password)){
//                if (getUserData().getPassword().equals(old_password)){
                String passwordDb = getUserData().getPassword();
                if (bCryptPasswordEncoder.matches(old_password,passwordDb)){
                    user.setPassword(bCryptPasswordEncoder.encode(new_password));
                    userService.saveUser(user);
                    redirAttrs.addFlashAttribute("success", "Password was changed successfully.");
                    return "redirect:/profile/";
                }else{
                    return "redirect:/profile/";
                }
            }
            redirAttrs.addFlashAttribute("error", "Old password error");
        }

        return "redirect:/profile/";
    }

    @PostMapping(value = "/uploadavatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(@RequestParam(name = "user_ava")MultipartFile file){
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {

            try {

                Users currentUser = getUserData();
                String picName = DigestUtils.sha1Hex("avatar_"+currentUser.getId()+"_!Picture");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picName+".jpg");
                Files.write(path, bytes);

                currentUser.setPictureUrl(picName);
                userService.saveUser(currentUser);
                return "redirect:/profile/";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/profile/";
    }

    @PostMapping(value = "/uploadpicture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String uploadPictures(@RequestParam(name = "item_picture")MultipartFile file,
    @RequestParam(name = "item_id", defaultValue = "0")Long itemId){
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {

            try {
                Calendar calendar = Calendar.getInstance();
                java.util.Date currentTime = calendar.getTime();
                long time = currentTime.getTime();

                List<Pictures> pictures = itemService.findAllByItemId(itemId);

                Long id = 1L;
                if (pictures!= null && pictures.size() > 0){
                    id = pictures.get(pictures.size()-1).getId()+1;
                }
                String picName = DigestUtils.sha1Hex("picture_"+id+"_!Screen");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPathPic + picName+".jpg");
                Files.write(path, bytes);
                Items item = itemService.getItem(itemId);
                Pictures pic = new Pictures(null,picName,new Timestamp(time),item);
                itemService.savePictures(pic);
                return "redirect:/editItem/" + itemId;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/adminItems";
    }

    @PostMapping(value = "/minuspicture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String minusPicture(@RequestParam(name = "item_id", defaultValue = "0") Long itemId,
                                @RequestParam(name = "picture_id", defaultValue = "0") Long pictureId){
        Pictures pic = itemService.getPictures(pictureId);
        if (pic!=null){
            itemService.deletePictures(pic);
            return "redirect:/editItem/" + itemId;
        }
        return "redirect:/adminItems";

    }

    @GetMapping(value = "/viewphoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewProfilePhoto(@PathVariable(name = "url") String url) throws IOException{
        String pictureUrl = viewPath+defaultPicture;
        if(url!=null){
            pictureUrl = viewPath+url+".jpg";
        }

        InputStream in;
        try {

            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath+defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();;
        }
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/viewsmallpic/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] viewItemPic(@PathVariable(name = "url") String url) throws IOException{
        String pictureUrl = "";
        if(url!=null){
            pictureUrl = viewPathPic+url+".jpg";
        }

        InputStream in;
        try {

            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e){
            in = null;
            e.printStackTrace();;
        }
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/basket")
    public String basket(Model model, HttpSession session,@CookieValue(name = "basket_itemsId", defaultValue = "")String value){
        model.addAttribute("currentUser", getUserData());
//        Long itemId = (Long)session.getAttribute("First_session");
//        Items item = itemService.getItem(itemId);
//        model.addAttribute("item", item);
        ArrayList<Items> items = new ArrayList<>();
        if (value.contains("_")){
            String[] num = value.split("_");
            for (int i = 0; i < num.length; i++) {
                if (!num[i].equals("")){
                    if (itemService.getItem(Long.parseLong(num[i])) != null){
                        items.add(itemService.getItem(Long.parseLong(num[i])));
                    }
                }
            }
        }
        int tot = 0;
        Map<Items, Integer> counter = new LinkedHashMap<>();
        for (Items it:items){
            tot += it.getPrice();
            int newValue = counter.getOrDefault(it,0)+1;
            counter.put(it, newValue);
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("items", counter);
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("tot", tot);
        return "basket";
    }

@GetMapping(value = "/addToBasket/{item_id}")
public String addToBasket(@CookieValue(name = "basket_itemsId", defaultValue = "") String value,
                          HttpServletResponse response,
                          @PathVariable(name = "item_id") Long item_id
) {

    Items shopItem = itemService.getItem(item_id);
    if (shopItem != null) {
        value += ("_" + item_id);
        Cookie cookie = new Cookie("basket_itemsId", value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }

    return "redirect:/basket";
}

    @GetMapping(value = "/clearBasket")
    public String clearBasket(
            HttpServletResponse response
    ) {

        Cookie cookie = new Cookie("basket_itemsId", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/basket";
    }

    @GetMapping(value = "/deleteFromBasket/{item_id}")
    public String deleteFromBasket(
            @CookieValue(name = "basket_itemsId", defaultValue = "") String value,
            HttpServletResponse response,
            @PathVariable(name = "item_id") Long item_id
    ) {

        Items shopItem = itemService.getItem(item_id);

        if (shopItem != null) {

            int idx = value.indexOf(String.valueOf(item_id));
            int idx3 = value.indexOf('_' ,idx);
            if (idx3 < 0){
                idx3 = value.length();
            }

            String q = value.substring(0, idx);
            String w = value.substring(idx3);

            Cookie cookie = new Cookie("basket_itemsId", q+w);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
        }

        return "redirect:/basket";
    }

    @GetMapping(value = "/purchaseItem")
    public String purchaseItem(@RequestParam(name = "full_name") String full_name,
                               @RequestParam(name = "card_number") String card_number,
                               @RequestParam(name = "expiration") String expiration,
                               @RequestParam(name = "cvv") int cvv,
                               @CookieValue(name = "basket_itemsId", defaultValue = "") String value,
                               HttpServletResponse response
    ) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

        ArrayList<Items> items = new ArrayList<>();

        if (value.contains("_")) {
            String[] num = value.split("_");

            for (int i = 0; i < num.length; i++) {
                if (!num[i].equals("")) {
                    if (itemService.getItem(Long.parseLong(num[i])) != null) {
                        items.add(itemService.getItem(Long.parseLong(num[i])));
                    }
                }
            }
        }


        for (Items shopItem: items) {
            Purchase purchase = new Purchase(null, full_name, card_number, expiration, cvv,new Timestamp(time) ,shopItem);
            itemService.savePurchase(purchase);
        }

        Cookie cookie = new Cookie("basket_itemsId", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);


        return "redirect:/";
    }

    @PostMapping(value = "/addComment")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@RequestParam(name = "text", defaultValue = "")String text,
                             @RequestParam(name = "itemId", defaultValue = "")Long itemId,
                             RedirectAttributes redirAttrs) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

        Users currentUser = getUserData();
        Items item = itemService.getItem(itemId);
        Comments comment = new Comments();
        comment.setComment(text);
        comment.setAuthor(currentUser);
        comment.setAddedDate(new Timestamp(time));
        comment.setItem(item);

        itemService.addComment(comment);
        redirAttrs.addFlashAttribute("successProfile", "Comment was added successfully.");

        return "redirect:/details/"+itemId;
    }

    @PostMapping(value = "/deletecom")
    @PreAuthorize("isAuthenticated()")
    public String deleteCom(@RequestParam(name = "id", defaultValue = "") Long id,
                            @RequestParam(name = "itemId", defaultValue = "")Long itemId){
        Comments comments = itemService.getComment(id);
        if (comments!=null) {
            itemService.deleteComment(comments);
        }
        return "redirect:/details/"+itemId;
    }

    @PostMapping(value = "/editcomment")
    @PreAuthorize("isAuthenticated()")
    public String saveCountry(@RequestParam(name = "id", defaultValue = "0")Long id,
                              @RequestParam(name = "itemId", defaultValue = "")Long itemId,
                              @RequestParam(name = "comment", defaultValue = "")String comment){
        Comments comments = itemService.getComment(id);
        Countries countries = itemService.getCountry(id);
        if (comments != null){
            comments.setComment(comment);
            itemService.saveComment(comments);
        }
        return "redirect:/details/"+itemId;
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
