package tugasakhir.pemesanan.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugasakhir.pemesanan.dto.ResponseData;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.OrderingRepository;
import tugasakhir.pemesanan.service.OrderingService;
import tugasakhir.pemesanan.service.ProductService;
//import tugasakhir.pemesanan.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/ordering")
public class OrderingController {

    @Autowired
    private OrderingService orderingService;

    @Autowired
    private OrderingRepository orderingRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/order")
    public String registration(Model model){
        model.addAttribute("order",new Ordering());
        return  "forms/orderforms";
    }

    @GetMapping("/order/{id_product}")
    public String registration(@PathVariable("id_product") Integer idProduct,Model model){
        System.out.println("id product for order come in : " + idProduct);
        Product product = productService.findById(idProduct);
        Ordering order = new Ordering();
        order.setProductId(product.getId_product());
        System.out.println("id product will order : " + product.getId_product());
        model.addAttribute("order",order);
        return  "createorder";
    }


    @PostMapping("/createorder")
    public String register(Ordering ordering){
        ResponseData<Ordering> response = new ResponseData<>();
        response.setPayload(orderingService.save(ordering));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
            return  "redirect:/ordering/myorder";
        }else{
            return "redirect:/ordering/show";
        }
    }

    @PostMapping("/updateorder")
    public String update(@RequestParam("id_order") Integer id_order, Ordering ordering){
        ResponseData<Ordering> response = new ResponseData<>();
        System.out.println("productId :" + ordering.getProductId());
        Ordering ord = orderingRepository.getById(id_order);
        ord.setOrderDate(ordering.getOrderDate());
        ord.setDeadline(ordering.getDeadline());
        ord.setOrderDetails(ordering.getOrderDetails());
        ord.setDp(ordering.getDp());
        ord.setPercentage(ordering.getPercentage());
        ord.setQuantity(ordering.getQuantity());
        ord.setTotalCost(ord.getTotalCost());
        ord.setUser(ordering.getUser());
        response.setPayload(orderingService.update(ord));
        return "redirect:/ordering/show";
    }

    //1
    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") String id, Model model){
        try {
            Integer idp = Integer.valueOf(id);
            Ordering ordering = orderingRepository.getById(idp);
            model.addAttribute("order", ordering);
            return  "forms/orderformsU";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    @GetMapping("/detail/{id}")
    public String detailOrder(@PathVariable("id") String id, Model model){
        try {
            Integer idp = Integer.valueOf(id);
            Ordering ordering = orderingRepository.getById(idp);
            model.addAttribute("order", ordering);
            return  "tables/showdetailorder";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        orderingRepository.deleteById(id);
        return "redirect:/ordering/show";
    }

    //findOrderingByUsername
    @GetMapping("/myorder")
    public String order(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ordering> ordering =  orderingService.findOrderingByUser(user.getIdUser());
        model.addAttribute("order", ordering);
        return "showOrderC";
    }

    //findAllOrdering
    @GetMapping("/show")
    public  String getAllOrder(Model map){
        List<Ordering> ordering = orderingRepository.findAll();
        map.addAttribute("order", ordering);
        return "tables/showorderA";
    }


    //find by username
    //BELUM JADI
    @GetMapping("/findorderbyuser/{username}")
    @ResponseBody
    public  List<Ordering> getOrderByUser(@PathVariable("username") String username ){
        System.out.println("username : " +  username );
        return orderingService.findByProductUsernameLike(username);
    }

    @GetMapping("/findbypercentage/{a}/{b}")
    @ResponseBody
    public Object getOrderByPercentage(@PathVariable("a") Integer a, @PathVariable("b") Integer b ){
       if(b > a){
           return "last input is too small";
       }
        return orderingService.findByPercentage(a, b);
    }



}
