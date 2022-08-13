package tugasakhir.pemesanan.controller;

import com.lowagie.text.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugasakhir.pemesanan.dto.ResponseData;
//import tugasakhir.pemesanan.model.product;
import tugasakhir.pemesanan.exporter.PdfSaleExporter;
import tugasakhir.pemesanan.model.*;
import tugasakhir.pemesanan.repository.SaleRepository;
import tugasakhir.pemesanan.repository.TransactionRepository;
import tugasakhir.pemesanan.service.SaleService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/sold")
    public String save(Sale sale){
        ResponseData<Sale> response = new ResponseData<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("role : " + user.getRole());
        Transaction trx = transactionRepository.getById(sale.getTransactionId());
        sale.setTransaction(trx);
        sale.setTransactionId(sale.getTransactionId());
        saleRepository.save(sale);
        return "redirect:/sale/show";
    }


    @GetMapping("/salesdata")
    public String registration(Model model){
        model.addAttribute("sale",new Sale());
        return  "salesdata";
    }

    @GetMapping("/show")
    public  String getAllOrder(Model map){
        List<Sale> sale = saleRepository.findAll();
        map.addAttribute("sale", sale);
        return "tables/showsaleA";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id_sale") Integer id,Sale sale){
        ResponseData<Sale> response = new ResponseData<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("role : " + user.getRole());
        Sale s = saleRepository.getById(id);
        s.setTransaction(sale.getTransaction());
        s.setLunas(sale.getLunas());
        s.setTotalPay(sale.getTotalPay());
        s.setTransactionDate(sale.getTransactionDate());
        Transaction trx = transactionRepository.getById(sale.getTransactionId());
        s.setTransaction(trx);
        saleRepository.save(sale);
        return "redirect:/sale/show";
    }

    @GetMapping("/update/{id}")
    public String updateSale(@PathVariable("id") String id, Model model){
        try {
            Integer idp = Integer.valueOf(id);
            Sale sale = saleRepository.getById(idp);
            model.addAttribute("sale", sale);
            return  "updatesalesdata";
        }catch (Exception e){
            return "redirect:/";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        saleRepository.deleteById(id);
        return "redirect:/sale/show";
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Sale_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Sale> listSales = saleService.listAll();

        PdfSaleExporter exporter = new PdfSaleExporter(listSales);
        exporter.export(response);

    }
}
