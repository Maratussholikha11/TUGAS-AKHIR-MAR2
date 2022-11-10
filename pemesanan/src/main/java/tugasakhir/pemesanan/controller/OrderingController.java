package tugasakhir.pemesanan.controller;
import org.modelmapper.ModelMapper;
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
import tugasakhir.pemesanan.dto.ResponseData;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.model.Transaction;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.OrderingRepository;
import tugasakhir.pemesanan.service.OrderingService;
import tugasakhir.pemesanan.service.ProductService;
//import tugasakhir.pemesanan.service.productService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Value("${ReceiptDpDir}")
    private String uploadFolder;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/order")
    public String registration(Model model){
        model.addAttribute("order",new Ordering());
        return  "forms/orderforms";
    }

    @GetMapping("/order/{id_product}")
    public String registration(@PathVariable("id_product") Integer idproduct,Model model){
        System.out.println("id product for Order come in : " + idproduct);
        Product product = productService.findById(idproduct);
        Ordering order  = new Ordering();
        order.setProductId(product.getId_product());
        order.setProduct(product);
        System.out.println("id product will Order : " + product.getId_product());
        model.addAttribute("order",order);
        return  "forms/orderforms";
    }


    /*@PostMapping("/createorder")
    public String register(Ordering ordering){
        ResponseData<Ordering> response = new ResponseData<>();
        if (ordering.getPercentage() == null){
            ordering.setPercentage(0);
        }
        response.setPayload(orderingService.save(ordering));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
            return  "redirect:/ordering/myorder";
        }else{
            return "redirect:/ordering/show";
        }
    }*/

    @PostMapping("/createorder")
    public String register(@RequestParam("productId") Integer productId,
                           @RequestParam("quantity") String quantity, @RequestParam("dp") Integer dp,  @RequestParam("OrderDetails") String orderDetails,
                           @RequestParam("jeniskain") String jeniskain, @RequestParam("warnakain") String warnakain,  @RequestParam("jmlS") String jmlS,
                           @RequestParam("jmlM") String jmlM, @RequestParam("jmlL") String jmlL,  @RequestParam("jmlXL") String jmlXL,  @RequestParam("jmlXXL") String jmlXXL,
                           Model model, HttpServletRequest request, final @RequestParam("image") MultipartFile file){


        try {
            ResponseData<Ordering> response = new ResponseData<>();

            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                System.out.println("Sorry! Filename contains invalid path sequence " + fileName);
            }
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
            Ordering ordering1 = new Ordering();
            Product p = productService.getproductById(productId);

            if(jmlS.isEmpty()){
                jmlS = "0";
            }if(jmlL.isEmpty()){
                jmlL = "0";
            }if(jmlXL.isEmpty()){
                jmlXL ="0";
            }if(jmlXXL.isEmpty()){
                jmlXXL ="0";
            }if(jmlM.isEmpty()){
                jmlM = "0";
            }

            Integer S = Integer.valueOf(jmlS);
            Integer M = Integer.valueOf(jmlM);
            Integer L = Integer.valueOf(jmlL);
            Integer XL = Integer.valueOf(jmlXL);
            Integer XXL = Integer.valueOf(jmlXXL);



            String detail = "Jenis kain : " + jeniskain + "\n Warna kain : " + warnakain + "\n Jml S : " + String.valueOf(S) +
                    " Jml M : " + String.valueOf(M) + "\n jml L : " +  String.valueOf(L) + "\n JmXL  : " +  String.valueOf(XL)+ "\n JmXXL  : " +  String.valueOf(XXL) +
                    " Catatan : " + orderDetails;

            System.out.println("detailll : " + detail);
            ordering1.setOrderDetails(detail);
            /*ordering1.setOrderDate(ordering.getOrderDate());
            ordering1.setDeadline(ordering.getDeadline());
            ordering1.setPercentage(ordering.getPercentage());*/
            ordering1.setQuantity(Integer.valueOf(quantity));
            ordering1.setDp(dp);
            ordering1.setStatusOrder("Menunggu");
            ordering1.setReceiptDpName(fileName);
//            ordering1.setStatusPayment(ordering.getStatusPayment());
            ordering1.setImage(imageData);
            ordering1.setProduct(p);
            ordering1.setProductId(p.getId_product());

            String LD_PATTERN = "yyyy-MM-dd";
            DateTimeFormatter LD_FORMATTER = DateTimeFormatter.ofPattern(LD_PATTERN);
            String dateString = LD_FORMATTER.format(LocalDate.now());
            /*transaction.setTransactionDate(dateString);*/
            response.setPayload(orderingService.save(ordering1));
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            System.out.println("Receipt Saved With File - " + fileName);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
                return  "redirect:/ordering/myorder";
            }else{
                return "redirect:/ordering/show";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error bestiee";
        }
    }

    @PostMapping("/updateorder")
    public String update(@RequestParam("id_Order") Integer id_order, Ordering ordering){
        ResponseData<Ordering> response = new ResponseData<>();
        System.out.println("productId :" + ordering.getProductId());
        Ordering ord = orderingRepository.getById(id_order);
        /*ord.setOrderDate(ordering.getOrderDate());
        ord.setDeadline(ordering.getDeadline());
        ord.setOrderDetails(ordering.getOrderDetails());
        ord.setDp(ordering.getDp());*/
        System.out.println("persen 1 => " + ordering.getPercentage());
        ord.setPercentage(ordering.getPercentage());
        System.out.println("persen 2 => " + ord.getPercentage());
        /*ord.setQuantity(ordering.getQuantity());
        ord.setTotalCost(ord.getTotalCost());
        ord.setUser(ordering.getUser());*/
        response.setPayload(orderingService.update(ord));
        return "redirect:/ordering/show";
    }

    //1
    @GetMapping("/update/{id}")
    public String updateproduct(@PathVariable("id") String id, Model model){
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
    public String detailorder(@PathVariable("id") String id, Model model){
        try {
            Integer idp = Integer.valueOf(id);
            Ordering ordering = orderingRepository.getById(idp);
            model.addAttribute("order", ordering);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
                return  "tables/showdetailorderC";
            }else{
                return  "tables/showdetailorder";
            }

        }catch (Exception e){
            return "redirect:/";
        }

    }

    @GetMapping("/save/{id}")
    public String save(@PathVariable("id") Integer id){
        Ordering ord = orderingRepository.getById(id);
        orderingService.terima(ord);
        return "redirect:/ordering/show";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        orderingRepository.deleteById(id);
        return "redirect:/ordering/show";
    }

    //findOrderingByUsername
    @GetMapping("/myorder")
    public String Order(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ordering> ordering =  orderingService.findOrderingByUser(user.getIdUser());
        model.addAttribute("order", ordering);
        return "tables/showorderC";
    }

    //findAllOrdering
    @GetMapping("/show")
    public  String getAllOrder(Model map){
        List<Ordering> ordering = orderingRepository.findAll();
        map.addAttribute("order", ordering);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
            return "redirect:/ordering/myorder";
        }else{
            return "tables/showorderA";
        }
    }


    //find by username
    //BELUM JADI
    @GetMapping("/findOrderbyuser/{username}")
    @ResponseBody
    public  List<Ordering> getOrderByUser(@PathVariable("username") String username ){
        System.out.println("username : " +  username );
        return orderingService.findByproductUsernameLike(username);
    }

    @GetMapping("/findbypercentage/{a}/{b}")
    @ResponseBody
    public Object getOrderByPercentage(@PathVariable("a") Integer a, @PathVariable("b") Integer b ){
       if(b > a){
           return "last input is too small";
       }
        return orderingService.findByPercentage(a, b);
    }

    @RequestMapping (value="/tabelukuran", method = RequestMethod.GET)
    public String contact(Model model, Principal p) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "forms/tableukuran";
    }

    @RequestMapping (value="/tabelwarna", method = RequestMethod.GET)
    public String color(Model model, Principal p) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "forms/tabelwarna";
    }



}
