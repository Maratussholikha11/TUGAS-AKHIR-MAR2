package tugasakhir.pemesanan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.model.Role;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.ProductRepository;
import tugasakhir.pemesanan.repository.RoleRepository;
import tugasakhir.pemesanan.repository.UserRepository;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

//import tugasakhir.pemesanan.repository.ProductRepository;

@Controller
public class DashboardController {
	
	@Autowired
	UserController userController;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ProductRepository productRepository;

	User user = new User();
	public User user(){
	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}

	@RequestMapping (value="/", method = RequestMethod.GET)
	public String Dashboard(Model model,Principal p) {
		model.addAttribute("user",user);
		Role login = roleRepository.checkRole("CUSTOMER");
		System.out.println("GET NAMEM=========:"+user.getName());
		System.out.print("NILAI LOGIN========:"+login);
		/*if(login!=null)
			if(login.getRoles().equals("USER") || login.getRoles().equals("ADMIN")  || login.getRoles().equals("PEMILIK"))
				return "index";
		
		return "index";

			if(login.getRole().getNameRole().equals("USER") || login.getRole().getNameRole().equals("ADMIN")  || login.getRole().getNameRole().equals("PEMILIK"))
				return "index";

		String role = user.getRole().getNameRole();
		System.out.println("role is : "+ role );
		if(role.equalsIgnoreCase("ADMIN")){
			a = "index";
		}else if(role.equalsIgnoreCase("CUSTOMER")){
			a = "indexC";
		}else if(role.equalsIgnoreCase("OWNER")){
			a = "indexO";
		}*/

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("rrr : "+ user.getRole().getNameRole());
		String a = "";
		if(user != null){
			if(user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
				a = "indexC";
				System.out.println("role cus");
			}if(user.getRole().getNameRole().equalsIgnoreCase("ADMIN")){
				a = "index";
				System.out.println("role admin");
			}if(user.getRole().getNameRole().equalsIgnoreCase("OWNER")){
				a = "indexO";
				System.out.println("role owner");
			}
		}
		return a;
	}
/*
	@RequestMapping (value="/home/customer", method = RequestMethod.GET)
	public String DashboardCustomer(Model model,Principal p) {
		model.addAttribute("user",user);
		User login = userRepository.checkRole(user.getName());
		System.out.println("GET NAMEM=========:"+user.getName());
		System.out.print("NILAI LOGIN========:"+login);
		if(login!=null)
			if(login.getRole().getNameRole().equals("CUSTOMER") || login.getRole().getNameRole().equals("ADMIN")  || login.getRole().getNameRole().equals("OWNER"))
				return "index";

		return "index";
	}*/
	
	
	@RequestMapping (value="/index", method = RequestMethod.GET)
	public String indexAdmin(Model model,Principal p) {	
		model.addAttribute("user",p); 
		return "index";
	}
	
	@RequestMapping (value="/about", method = RequestMethod.GET)
	public String about(Model model,Principal p) {	
		model.addAttribute("user",p);
	return "about";
	}
	
	@RequestMapping (value="/gallery", method = RequestMethod.GET)
	public String galery(Model model,Principal p) {	
		model.addAttribute("user",p);
	return "shop";
	}

	@RequestMapping(value = "/allproduct", method = RequestMethod.GET)
	public  String getAllProducts (Model model){
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "shop";
	}
	
	@RequestMapping (value="/contact-us", method = RequestMethod.GET)
	public String contact(Model model,Principal p) {	
		model.addAttribute("user",p);
	return "contact-us";
	}
	
	
}
