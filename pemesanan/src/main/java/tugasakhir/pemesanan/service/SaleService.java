package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Sale;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.SaleRepository;

import java.util.Date;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleService saleService;

    @Autowired
    private OrderingService orderingService;

    public Sale save(Sale sale){
//       if(sale.getId_sale()!=null){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("user adalah :" + user.getName());
        Sale current = new Sale();
//        System.out.println("total cost Ordering : " + sale.getOrdering().getTotalCost() );
//        Ordering ord = orderingService.findById(sale.getOrdering().getId_Order());
//        System.out.println("total cost Ordering : " + ord.getTotalCost());
//        current.setTotalPay(ord.getTotalCost());
        current.setLunas(sale.getLunas());
        current.getTransactionDate();
        current.setTransaction(sale.getTransaction());
//        current.setOrdering(sale.getOrdering());
        sale = current;
//     }

        return saleRepository.save(sale);
    }

    // find All sale
    public List<Sale> listAll() {
        return saleRepository.findAll();
    }
    // count laba
    // count total cost per Order

    public List<Sale> listSale() {
        return saleRepository.findAll();
    }

}
