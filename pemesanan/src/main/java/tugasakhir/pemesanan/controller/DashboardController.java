package tugasakhir.pemesanan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tugasakhir.pemesanan.model.*;
import tugasakhir.pemesanan.repository.*;
import tugasakhir.pemesanan.service.OrderingService;
import tugasakhir.pemesanan.service.ProductService;
import tugasakhir.pemesanan.service.SaleService;
import tugasakhir.pemesanan.service.UserService;

import java.security.Principal;
import java.util.List;

//import tugasakhir.pemesanan.repository.productRepository;

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

	@Autowired
	private OrderingService orderingService;

	@Autowired
	private OrderingRepository orderingRepository;


	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private SaleService saleService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;


	User user = new User();
	public User user(){
	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}



	@RequestMapping (value="/", method = RequestMethod.GET)
	public String Dashboard(Model model,Principal p) {
		List<User> allCustomer =  userService.getCustomer();
		List<Ordering> ordering = orderingService.findAllOrdering();
		List<Sale> sale = saleService.listAll();
		List<Product> products = productService.getAllActiveproducts();
		String p1 = products.get(0).getProductName();
		String p2 = products.get(1).getProductName();
		String p3 = products.get(2).getProductName();
		String p4 = products.get(3).getProductName();
		String p5 = products.get(4).getProductName();
		String p6 = products.get(5).getProductName();
		String p7 = products.get(6).getProductName();
		String p8 = products.get(7).getProductName();
		String p9 = products.get(8).getProductName();


		/*String p10 = products.get(10).getProductName();
		String p11 = products.get(11).getProductName();
		String p12 = products.get(12).getProductName();
		String p13 = products.get(13).getProductName();
		String p14 = products.get(14).getProductName();
		String p15 = products.get(15).getProductName();*/

		Integer h1 = products.get(0).getPrice();
		Integer h2 = products.get(1).getPrice();
		Integer h3 = products.get(2).getPrice();
		Integer h4 = products.get(3).getPrice();
		Integer h5 = products.get(4).getPrice();
		Integer h6 = products.get(5).getPrice();
		Integer h7 = products.get(6).getPrice();
		Integer h8 = products.get(7).getPrice();
		Integer h9 = products.get(8).getPrice();

		model.addAttribute("p1", p1);
		model.addAttribute("p2", p2);
		model.addAttribute("p3", p3);
		model.addAttribute("h1", h1);
		model.addAttribute("h2", h2);
		model.addAttribute("h3", h3);
		List<Ordering> ord = orderingService.findByPercentage(0,50);
		String o1 = ord.get(0).getUser().getName();
		String o2 = ord.get(1).getUser().getName();
		String o3 = ord.get(2).getUser().getName();
		String o4 = ord.get(3).getUser().getName();
		/*String o5 = ord.get(4).getUser().getName();*/
		Integer c1 = ord.get(0).getPercentage();
		Integer c2 = ord.get(1).getPercentage();
		Integer c3 = ord.get(2).getPercentage();
		Integer c4 = ord.get(3).getPercentage();
		/*Integer c5 = ord.get(4).getPercentage();*/
		model.addAttribute("o1", o1);
		model.addAttribute("o2", o2);
		model.addAttribute("o3", o3);
		model.addAttribute("o4", o4);
		/*model.addAttribute("o5", o5);*/
		model.addAttribute("c1", c1);
		model.addAttribute("c2", c2);
		model.addAttribute("c3", c3);
		model.addAttribute("c4", c4);
		/*model.addAttribute("c5", c5);*/
		Integer sizeCustomer = allCustomer.size();
		Integer sizeOrdering = ordering.size();
		Integer sizeSale = sale.size();
		model.addAttribute("customer", sizeCustomer);
		model.addAttribute("Ordering", sizeOrdering);
		model.addAttribute("sale", sizeSale);

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
		model.addAttribute("user",user);
		String userRole = user.getRole().getNameRole().toLowerCase();
		String userName = user.getName().toUpperCase();
		String userEmail = user.getEmail();
		model.addAttribute("userRole", userRole);
		model.addAttribute("userName", userName);
		model.addAttribute("userEmail", userEmail);
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
	public  String getAllproducts (Model model){
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "shop";
	}

	@RequestMapping (value="/contact-us", method = RequestMethod.GET)
	public String contact(Model model,Principal p) {
		model.addAttribute("user",p);
	return "contact-us";
	}

	@RequestMapping(value = "/persadakonveksi", method = RequestMethod.GET)
	public String Persada(Model model){
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
//			return  "tables/showproductC";
//		}else{
//			return "tables/showproduct";
//		}
		System.out.println("masuk ..");

		return "tables/showproductunlogin2";
	}
	
}
