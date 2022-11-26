package tugasakhir.pemesanan.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugasakhir.pemesanan.dto.ResponseData;
import tugasakhir.pemesanan.model.Role;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.RoleRepository;
import tugasakhir.pemesanan.repository.UserRepository;
import tugasakhir.pemesanan.service.UserDetailService;
import tugasakhir.pemesanan.service.UserService;

import java.util.List;
import java.util.Optional;


//@RestController
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;


//    @Autowired
//    EmailService emailService;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user",new User());
        return  "forms/customerformsA";
    }

    @PostMapping("/register")
    public String register(User user){
        try {
            System.out.println("userr 2 ");
            ResponseData<User> response = new ResponseData<>();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user1  = modelMapper.map(user, User.class);
//        user1.setPassword(user.getPassword());
//        String[] email = new String[1];
//        email[0] = user1.getEmail();
//        String text = "Welcome to Leave Management System\n" +
//                "here is your username and password to login in your account Leave management system\n" +
//                "username : " + user.getUsername() +
//                "\npassword : " + user.getPassword() +
//                "\nor you can login by click the link below\n" +
//                "ini link\n" +
//                "thank you!!!\n";
////        emailService.sendEmail(email[0],"Acces Login User",text);
//        response.setPayload(userDetailService.save(user1));
//        return ResponseEntity.ok(response);
            Role role = new Role();
            if(user.getRole()==null) {
                role = roleRepository.findByNameRole("CUSTOMER");
                user.setRole(role);
            }
            System.out.println("role : " + user.getRole().getNameRole());
            userDetailService.save(user);
            return "redirect:/";
        }catch (Exception e){
            return "register";
        }

    }

    @GetMapping("/create/customer")
    public String newCustomer(Model model){
        model.addAttribute("user",new User());
        return  "forms/customerformsA";
    }

    @GetMapping("/create/user")
    public String newAdmin(Model model){
        model.addAttribute("user",new User());
        return  "forms/adminformsO";
    }

    @PostMapping("/newadmin")
    public String createAdmin(User user){
        System.out.println("userr 2 ");
        ResponseData<User> response = new ResponseData<>();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user1  = modelMapper.map(user, User.class);
//        user1.setPassword(user.getPassword());
//        String[] email = new String[1];
//        email[0] = user1.getEmail();
//        String text = "Welcome to Leave Management System\n" +
//                "here is your username and password to login in your account Leave management system\n" +
//                "username : " + user.getUsername() +
//                "\npassword : " + user.getPassword() +
//                "\nor you can login by click the link below\n" +
//                "ini link\n" +
//                "thank you!!!\n";
////        emailService.sendEmail(email[0],"Acces Login User",text);
//        response.setPayload(userDetailService.save(user1));
//        return ResponseEntity.ok(response);
       /* Role role = new Role();
        if(user.getRole()==null) {
            role = roleRepository.findByNameRole("ADMIN");
            user.setRole(role);
        }*/
       /* Role role = roleRepository.findByNameRole("ADMIN");
        user.setRole(role);*/
        System.out.println("role : " + user.getRole().getNameRole());
        Integer role = user.getRole().getIdRole();
        String a = "";
        userDetailService.save(user);
        if(role == 7){
            System.out.println("create admin berhasil");
            return  "redirect:/user/admin";
        }else if(role == 8){
            System.out.println("create customer berhasil");
            return "redirect:/user/customer";
        }else if (role == 9){
            System.out.println("create owner berhasil");
            return "indexO";
        }
        return a;
    }

    @GetMapping("/updateadmin/{id}")
    public String updateproduct(@PathVariable("id") Integer id, Model model){
        try {
            User user = userRepository.getById(id);
            model.addAttribute("user", user);
            return  "forms/adminformsOU";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    @PostMapping("/updateadmin")
    public String updateRegister(@RequestParam("idUser") Integer id, User user){
        User u = userRepository.getById(id);
        u.setName(user.getName());
        u.setAddress(user.getAddress());
        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        u.setNoTelepon(user.getNoTelepon());
        userRepository.save(u);
        return "redirect:/user/admin";
    }

    @PostMapping("/updatecustomer")
    public String updateCustomer(@RequestParam("idUser") Integer id, User user){
        User u = userRepository.getById(id);
        u.setName(user.getName());
        u.setAddress(user.getAddress());
        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        u.setNoTelepon(user.getNoTelepon());
        userRepository.save(u);
        return "redirect:/user/customer";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
        return "showcustomer";
    }


    // not yet
    @GetMapping("/getAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    //get customer
    @GetMapping("/customer")
    public String getAllCustomer(Model model){
        List<User> allCustomer =  userService.getCustomer();
        model.addAttribute("customer", allCustomer);
        return "tables/showcustomer";
    }

    @GetMapping("/updatecustomer/{id}")
    public String updateCustomer(@PathVariable("id") Integer id, Model model){
        try {
            User user = userRepository.getById(id);
            model.addAttribute("user", user);
            return  "forms/customerformsAU";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    @GetMapping("/admin")
    public String getAllAdmin(Model model){
        List<User> getAllAdmin =  userService.getAdmin();
        model.addAttribute("admin", getAllAdmin);
        return "tables/showadmin";
    }


    @GetMapping("/{id}")
    public User findOne (@PathVariable ("id") Integer id){
        Optional<User> p = userRepository.findById(id);
        if (!p.isPresent()){
            return  null;
        }
        return userService.findOne(id);
    }

    @GetMapping("/bynameoremail/{name}")
    public List<User> findByNameOrEmail(@PathVariable ("name") String name){
        return userRepository.findByNameContainsOrEmailContains(name, name);
    }

    // not yet
    @PutMapping("/changepassword")
    public String change(@RequestBody String pass){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String encodePassword = bCryptPasswordEncoder.encode(pass);
        user.setPassword(encodePassword);
        user.setPassword(pass);
        userDetailService.save(user);
        return "pass";
    }





}
