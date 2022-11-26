package tugasakhir.pemesanan.controller;

import org.hibernate.criterion.Order;
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
import tugasakhir.pemesanan.model.*;
import tugasakhir.pemesanan.repository.ImageRepository;
import tugasakhir.pemesanan.repository.OrderingRepository;
import tugasakhir.pemesanan.repository.TransactionRepository;
import tugasakhir.pemesanan.service.TransactionService;
import tugasakhir.pemesanan.util.ImageUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageController imageController;

    @Autowired
    private OrderingRepository orderingRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SaleController saleController;

    @Value("${ReceiptDir}")
    private String uploadFolder;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/form")
    public String registration(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "forms/transactionforms";
    }

    @GetMapping("/form/{id_Order}")
    public String registration(@PathVariable("id_Order") Integer idOrder, Model model) {
        Ordering Order = orderingRepository.getById(idOrder);
        System.out.println("id Order will pay : " + idOrder);
        Transaction trx = new Transaction();
        trx.setOrdering(Order);
        trx.setOrderId(Order.getId_Order());
        model.addAttribute("transaction", trx);
        return "forms/transactionforms";
    }


    @PostMapping("/create")
    public String register(@RequestParam("OrderId") Integer idOrder,
                            @RequestParam("totalPay") Integer totalPay,  @RequestParam("note") String note,
                           Model model, HttpServletRequest request, final @RequestParam("image") MultipartFile file) {
        System.out.println("masukk sini");
        try {
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                System.out.println("Sorry! Filename contains invalid path sequence " + fileName);
            }
            /*String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            log.info("Name: " + names[0]+" "+filePath);
            log.info("description: " + descriptions[0]);
            log.info("price: " + price);*/
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
            Transaction transaction = new Transaction();

            //transaction.setTotalPay(totalPay);
            transaction.setNote(note);
            transaction.setReceiptName(fileName);
            transaction.setImage(imageData);
            ResponseData<Transaction> response = new ResponseData<>();
            Ordering ord = orderingRepository.getById(idOrder);
            if(totalPay == ord.getFinalCost()){
                transaction.setLunas("lunas");
            }
            ord.setStatusPayment("Pending");
            orderingRepository.save(ord);
            transaction.setOrdering(ord);
            transaction.setOrderId(ord.getId_Order());
            int totalPayment = transaction.getOrdering().getDp() + transaction.getOrdering().getFinalCost();
            System.out.println("dp : " + transaction.getOrdering().getDp()+ " + " + "final cost : " + transaction.getOrdering().getFinalCost() + " = " + totalPayment);
            transaction.setTotalPay(totalPayment);
            String LD_PATTERN = "yyyy-MM-dd";
            DateTimeFormatter LD_FORMATTER = DateTimeFormatter.ofPattern(LD_PATTERN);
            String dateString = LD_FORMATTER.format(LocalDate.now());
            transaction.setTransactionDate(dateString);
            response.setPayload(transactionService.save(transaction));
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            System.out.println("Receipt Saved With File - " + fileName);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")) {
                return "redirect:/transaction/mytransaction";
            } else {
                return "redirect:/transaction/show";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error bestiee";
        }

    }

    @PostMapping("/update")
    public String update(@RequestParam("id_transaction") Integer id,@RequestParam("status") String status ,Transaction transaction){
        Transaction trx = transactionRepository.getById(id);
//        trx.setTransactionDate(transaction.getTransactionDate());
        Ordering ord = orderingRepository.getById(transaction.getOrderId());
        trx.setImage(transaction.getImage());
        if (status.equalsIgnoreCase("1")){
            trx.setLunas("Lunas");
            ord.setStatusPayment("Pay off");
        }else if(status.equalsIgnoreCase("3")){
            trx.setLunas("Rejected");
            ord.setStatusPayment("Rejected");
        }
//        trx.setTotalPay(transaction.getTotalPay());
        trx.setNote(transaction.getNote());
//        trx.setTotalPay(transaction.getTotalPay());
        trx.setOrderId(transaction.getOrderId());
        System.out.println("status updated : " + transaction.getStatus().getIdStatus());
        trx.setStatus(transaction.getStatus());
        orderingRepository.save(ord);
        trx.setOrdering(ord);
        trx.setUser(ord.getUser());
        System.out.println("user Order : " + ord.getUser().getIdUser() + " " + ord.getUser().getName());
        transactionRepository.save(trx);
        if(trx.getStatus().getIdStatus().equals(1)){
            Sale sale = new Sale();
            sale.setTransaction(trx);
            sale.setTransactionId(transaction.getId_transaction());
            sale.setTransactionDate(trx.getTransactionDate());
            sale.setTotalPay(trx.getTotalPay());
            System.out.println("save sale after approve payment");
            System.out.println("trx no : "+ trx.getId_transaction());
            System.out.println("trx date : "+ trx.getTransactionDate());
            System.out.println("total : "+ trx.getTotalPay());
            System.out.println("no Order : "+ trx.getOrderId());
            System.out.println("id user : " + trx.getUser().getIdUser());
            System.out.println("name : "+ trx.getUser().getName());
            saleController.save(sale);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
            return  "redirect:/transaction/mytransaction";
        }else{
            return "redirect:/transaction/show";
        }
    }

    //findOrderingByUsername
    @GetMapping("/mytransaction")
    public String Order(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> transactions =  transactionService.findOrderingByUser(user.getIdUser());
        for (Transaction t : transactions){
            System.out.println("statuss trx :" + t.getStatus().getName());
        }
        model.addAttribute("transaction", transactions);
        return "tables/showtransactionC";
    }

    @GetMapping("/update/{id_transaction}")
    public String transaction(@PathVariable("id_transaction") Integer id, Model model){
        try{
            Transaction transaction = transactionRepository.getById(id);
            model.addAttribute("transaction", transaction);
            return  "forms/transactionformsU";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    //update transaction approve
    @GetMapping("/update/a/{id_transaction}")
    public String approve(@PathVariable("id_transaction") Integer id, Model model){
        try{
            Transaction transaction = transactionRepository.getById(id);
            transactionService.approve(transaction);
            model.addAttribute("transaction", transaction);
            return "redirect:/transaction/show";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    //update transaction reject
    @GetMapping("/update/r/{id_transaction}")
    public String reject(@PathVariable("id_transaction") Integer id, Model model){
        try{
            Transaction transaction = transactionRepository.getById(id);
            transactionService.reject(transaction);
            model.addAttribute("transaction", transaction);
            return "redirect:/transaction/show";
        }catch (Exception e){
            return "redirect:/";
        }

    }


    @GetMapping("/receipt/{id}")
    void showImage(@PathVariable("id") Integer id, HttpServletResponse response, Transaction transaction)
            throws ServletException, IOException {
        transaction = transactionRepository.getById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(transaction.getImage());
        response.getOutputStream().close();
    }

    @GetMapping("/show")
    public  String getAllTransaction(Model map){
        List<Transaction> transaction = transactionRepository.findAll();
        map.addAttribute("transaction", transaction);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().getNameRole().equalsIgnoreCase("CUSTOMER")){
            return "redirect:/transaction/mytransaction";
        }else{
            return "tables/showtransactionA";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        transactionRepository.deleteById(id);
        return "redirect:/transaction/show";
    }

}
