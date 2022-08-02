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
        return  "register";
    }

    @PostMapping("/register")
    public String register(User user){
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
    }

    @GetMapping("/create/newadmin")
    public String newAdmin(Model model){
        model.addAttribute("user",new User());
        return  "registeradmin";
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
        System.out.println("role : " + user.getRole().getNameRole());
        userDetailService.save(user);
        return "redirect:/user/admin";
    }

    @PutMapping("/update")
    @ResponseBody
    public String updateRegister(@RequestBody User user){
        userDetailService.save(user);
        return "Updated";
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
        return "showcustomer";
    }

    @GetMapping("/admin")
    public String getAllAdmin(Model model){
        List<User> getAllAdmin =  userService.getAdmin();
        model.addAttribute("admin", getAllAdmin);
        return "showadmin";
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
