package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tugasakhir.pemesanan.model.Ordering;
//import tugasakhir.pemesanan.model.product;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.OrderingRepository;

import java.util.List;

@Service
public class OrderingService {


    @Autowired
    private OrderingRepository orderingRepository;

    @Autowired
    ProductService productService;

    public Ordering save(Ordering ordering){
//       if(ordering.getId_Order()!=null){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("user adalah :" + user.getName());
            Ordering current = new Ordering();
            current.setProduct(ordering.getProduct());
//            current.setSale(ordering.getSale());
            current.setDp(ordering.getDp());
            current.setDeadline(ordering.getDeadline());
            current.setOrderDate(ordering.getOrderDate());
            current.setOrderDetails(ordering.getOrderDetails());
            current.setQuantity(ordering.getQuantity());
            current.setPercentage(ordering.getPercentage());
            current.setUser(user);
            Product p = productService.findById(ordering.getProductId());
            current.setProduct(p);
            current.setProductId(p.getId_product());
            System.out.println("Order by \n user name : " + user.getName());
            System.out.println("id product : " + ordering.getProductId());
            System.out.println("quantity : " + ordering.getQuantity());
            System.out.println("price : " + p.getPrice());
            Integer totalCost = p.getPrice() * ordering.getQuantity();
            System.out.println("total cost : " + totalCost);
            current.setTotalCost(totalCost);
            ordering = current;
//     }
        return orderingRepository.save(ordering);
    }

    public Ordering update(Ordering ordering){
//       if(ordering.getId_Order()!=null){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("user adalah :" + user.getName());
        Ordering current = orderingRepository.getById(ordering.getId_Order());
        current.setProduct(ordering.getProduct());
//            current.setSale(ordering.getSale());
        current.setDp(ordering.getDp());
        current.setDeadline(ordering.getDeadline());
        current.setOrderDate(ordering.getOrderDate());
        current.setOrderDetails(ordering.getOrderDetails());
        current.setQuantity(ordering.getQuantity());
        current.setPercentage(ordering.getPercentage());
        current.setUser(user);
        Product p = productService.findById(ordering.getProductId());
        current.setProduct(p);

        System.out.println("Order by \n user name : " + user.getName());
        System.out.println("id product : " + ordering.getProductId());
        System.out.println("quantity : " + ordering.getQuantity());
        System.out.println("price : " + p.getPrice());
        int total = ordering.getQuantity() * p.getPrice();
        System.out.println("total : " + total);
        int totalCost = 0;
        for (int a=0; a<ordering.getQuantity(); a++){
            totalCost += ordering.getQuantity() * p.getPrice();
        }
        current.setTotalCost(totalCost);
        ordering = current;
//     }
        return orderingRepository.save(ordering);
    }

    public Ordering findById(Integer id){
        return orderingRepository.getById(id);
    }

    // select Order by user
    public List<Ordering> findOrderingByUser(Integer idUser){
        return orderingRepository.findOrderingByUser(idUser);
    }

    // find all Ordering
    public List<Ordering> findAllOrdering(){
        return orderingRepository.findAll();
    }


    // BELUM JADI
    // fing Order by username and name
    public List<Ordering>findByproductUsernameLike(String username){
        System.out.println("service");
        System.out.println("print repo : " + orderingRepository.findOrderingByUsername(username));
        return orderingRepository.findOrderingByUsername(username);
    }

    // find Order by percentage
    public List<Ordering> findByPercentage(Integer a, Integer b){
        return orderingRepository.findByPercentageBetween(a, b);
    }

    // delete Order
}
