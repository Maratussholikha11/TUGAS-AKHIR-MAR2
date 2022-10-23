package tugasakhir.pemesanan.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tugasakhir.pemesanan.dto.SearchData;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.ProductRepository;
import tugasakhir.pemesanan.service.ProductService;


@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private ProductService productService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public Map<String, Product> products = new HashMap<>();

	@GetMapping(value = {"/home"})
	public String addproductPage() {
		return "indexUploadImg";
	}

	@GetMapping("/product")
	public String registration(Model model){
		model.addAttribute("product",new Product());
		return  "forms/productforms";
	}

	@PostMapping("/product/create")
	public String createEmployee(@RequestParam("productName") String name,
								 @RequestParam("price") Integer price, @RequestParam("description") String description, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) {
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				System.out.println("Sorry! Filename contains invalid path sequence " + fileName);
			}
			String[] names = name.split(",");
			String[] descriptions = description.split(",");
			Date createDate = new Date();
			log.info("Name: " + names[0]+" "+filePath);
			log.info("description: " + descriptions[0]);
			log.info("price: " + price);
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
				System.out.println("file uploadeddd");
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			Product product = new Product();
			product.setProductName(names[0]);
			product.setImage(imageData);
			product.setPrice(price);
			product.setDescription(descriptions[0]);
			product.setFileName(fileName);
			productService.saveproduct(product);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			System.out.println("product Saved With File - " + fileName);
			return "redirect:/product/show";
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return "error";
		}
	}

	@PostMapping("/product/form")
	public String update(@RequestParam("id_product") Integer id,@RequestParam("productName") String name,
								 @RequestParam("price") Integer price, @RequestParam("description") String description, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) {
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				System.out.println("Sorry! Filename contains invalid path sequence " + fileName);
			}
			String[] names = name.split(",");
			String[] descriptions = description.split(",");
			Date createDate = new Date();
			log.info("Name: " + names[0]+" "+filePath);
			log.info("description: " + descriptions[0]);
			log.info("price: " + price);
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
				System.out.println("file uploadeddd");
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			Product product = productRepository.getById(id);
			product.setProductName(names[0]);
			product.setImage(imageData);
			product.setPrice(price);
			product.setDescription(descriptions[0]);
			product.setFileName(fileName);
			productService.saveproduct(product);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			System.out.println("product Saved With File - " + fileName);
			return "redirect:/product/show";
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return "error";
		}
	}


	@GetMapping("/product/display/{id}")
	byte[] showImage(@PathVariable("id") String id, HttpServletResponse response, Product product)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		Integer ids = Integer.valueOf(id);
		product = productService.getproductById(ids);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(product.getImage());
		response.getOutputStream().close();
		return product.getImage();
	}

	/*@GetMapping("/product/productdetails")
	String showproductDetails(@RequestParam("id") Integer id, product product, Model model) {
		try {
			log.info("Id :: " + id);
			if (id != 0) {
			product = productService.getproductById(id);
			
				log.info("products :: " + product);
				if (product.isPresent()) {
					model.addAttribute("id", product.get().getId_product());
					model.addAttribute("description", product.get().getDescription());
					model.addAttribute("name", product.get().getproductName());
					model.addAttribute("price", product.get().getPrice());
					return "productdetails";
				}
				return "redirect:/home";
			}
		return "redirect:/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}	
	}
*/
	@GetMapping("/product/show")
	String show(Model map) {
		List<Product> products = productRepository.findAll();
		map.addAttribute("products", products);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
			return  "tables/showproductC";
		}else{
			return "tables/showproduct";
		}
	}



	@PostMapping("/product/search")
	String search(SearchData searchData, Model model){
		List<Product> products = productService.getproduct(searchData);
		SearchData searchKey = new SearchData();
		model.addAttribute("search", searchKey);
		model.addAttribute("products", products);
		return "showproduct";
	}

        //1
	@GetMapping("/product/update/{id}")
	public String updateproduct(@PathVariable("id") String id, Model model){
		try {
			Integer idp = Integer.valueOf(id);
			Product product = productRepository.getById(idp);
			model.addAttribute("product", product);
			return  "forms/productformsU";
		}catch (Exception e){
			return "redirect:/";
		}

	}

	@GetMapping("/product/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		productRepository.deleteById(id);
		return "redirect:/product/show";
	}

	@GetMapping("/product/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model){
		Product product = productRepository.getById(id);
		model.addAttribute("product", product);
		return "tables/showdetailproductunlogin";
	}



}	
