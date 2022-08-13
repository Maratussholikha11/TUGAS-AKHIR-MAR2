package tugasakhir.pemesanan.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugasakhir.pemesanan.constant.ErrorConstant;
import tugasakhir.pemesanan.dto.BusinessException;
import tugasakhir.pemesanan.dto.ResponseData;
import tugasakhir.pemesanan.dto.SearchData;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.product;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.productRepository;
import tugasakhir.pemesanan.service.productService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class productController {

    @Autowired
    private productService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/create")
    @ResponseBody
    public ResponseEntity<ResponseData<product>> register(@RequestBody product product){
        ResponseData<product> response = new ResponseData<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("role : " + user.getRole().getNameRole());
        product product1  = modelMapper.map(product, product.class);
        response.setPayload(productService.save(product1));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @ResponseBody
    public String updateproduct(@RequestBody product product){
        productService.save(product);
        return "product Updated";
    }

    @GetMapping(value = "/findById/{a}")
    @ResponseBody
    public Object getproductById(@PathVariable("a") Integer a) throws BusinessException {
        try{
            return productService.findById(a);
        }catch (Exception e){
            throw new BusinessException(ErrorConstant.ERROR);
        }

    }

    @GetMapping(value = "/namelike")
    public List<product> getproductByNameLike(@RequestBody SearchData searchData){
        return productService.findByproductNameLike(searchData.getSearchKey());
    }


    @GetMapping("/page1")
    @ResponseBody
    public String Halo (){
        return "hello 1";
    }

    @RequestMapping(value = "/allproduct", method = RequestMethod.GET)
    public  String getAllproducts (Model model){
        List<product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "shop";
    }

    @RequestMapping (value="/gallery", method = RequestMethod.GET)
    public String galery(Model model, Principal p) {
        model.addAttribute("user",p);
        return "shop";
    }
}
