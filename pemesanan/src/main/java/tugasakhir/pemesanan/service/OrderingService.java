package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tugasakhir.pemesanan.model.Ordering;
//import tugasakhir.pemesanan.model.product;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.OrderingRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderingService {


    @Autowired
    private OrderingRepository orderingRepository;

    @Autowired
    ProductService productService;

    @Autowired
    EmailService emailService;

    public Ordering save(Ordering ordering){
//       if(ordering.getId_Order()!=null){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("user adalah :" + user.getName());
            Ordering current = new Ordering();
            current.setProduct(ordering.getProduct());
//            current.setSale(ordering.getSale());
            if (ordering.getPercentage() == null){
                ordering.setPercentage(0);
            }
            //set payment status belum di insert disini
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateTime = dateFormatter.format(new Date());
            System.out.println("currentDateTime : " + currentDateTime);

            String splitDate = currentDateTime;
            String[] splited = splitDate.split("-");
            int thn = Integer.valueOf(splited[0]);
            int bln = Integer.valueOf(splited[1]);
            int hari = Integer.valueOf(splited[2]);

            String dl = "";
            int day = 0;
            if (ordering.getQuantity()<=100){
                day = 15;
            }else if(ordering.getQuantity()>100 && ordering.getQuantity()<=200){
                day = 30;
            }else if (ordering.getQuantity()>200 && ordering.getQuantity()<=300){
                day = 45;
            }else if (ordering.getQuantity()>300 && ordering.getQuantity()<=400){
                day = 60;
            }else{
                System.out.println("Error quantity");
            }

       /* int thn = 2022;
        int bln = 7;
        int hari = 15;*/

        int b = 0;
        int h = 0;
        int gnp = 30;
        int gnj = 31;
        int feb = 28;

        hari += day;
        for (int a=1; a<4; a++){
            if(bln == 1 || bln == 3 || bln == 1 || bln == 5 || bln == 7 || bln == 8 || bln == 10 || bln == 11){
                if(hari > gnj){
                    b = hari / 31;
                    bln +=b;
                    h = hari % 31;
                    hari =h;
                    if(bln>12){
                        thn+=1;
                        bln=1;
                    }
                }else{

                }

            }else if(bln == 4 || bln == 6 || bln == 9 || bln == 7 || bln == 11){
                if(hari > gnp){
                    b = hari / 30;
                    bln +=b;
                    h = hari % 30;
                    hari =h;
                    if(bln>12){
                        thn+=1;
                        bln=1;
                    }
                }
            }else if(bln == 02){
                if(hari > gnp){
                    b = hari / 28;
                    bln +=b;
                    h = hari % 28;
                    hari =h;
                    if(bln>12){
                        thn+=1;
                        bln=1;
                    }
                }
            }

        }
        if(bln<10){
            System.out.println("after : " + thn + "-0"+ bln + "-" + hari);
        }else if(bln>=10){
            System.out.println("day : " + day);
            System.out.println("after : " + thn + "-"+ bln + "-" + hari);
        }
            System.out.println("dlnyaadalah = " +  thn + "-0"+ bln + "-" + hari);
            String deadline ="";
            if(bln<10){
                System.out.println("1120000 : " + thn + "-0"+ bln + "-" + hari);
                deadline = thn + "-0"+ bln + "-" + hari;
            }else if(bln>=10){
                System.out.println("day : " + day);
                System.out.println("deadinenya kapan ya  : " + thn + "-"+ bln + "-" + hari);
                deadline = thn + "-"+ bln + "-" + hari;
            }
            System.out.println("deadline : " + deadline);
            current.setDp(ordering.getDp());
            current.setDeadline(deadline);
            current.setOrderDate(currentDateTime);
            current.setOrderDetails(ordering.getOrderDetails());
            current.setQuantity(ordering.getQuantity());
            current.setStatusOrder("Menunggu");
            current.setPercentage(ordering.getPercentage());
            current.setImage(ordering.getImage());
            current.setReceiptDpName(ordering.getReceiptDpName());
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
            current.setTotalCost(totalCost);
            int finalCost = totalCost - current.getDp();
            current.setFinalCost(finalCost);
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
//        current.setUser(user);
        Product p = productService.findById(ordering.getProductId());
        current.setProduct(p);

//        System.out.println("Order by \n user name : " + user.getName());
        System.out.println("id product : " + ordering.getProductId());
        System.out.println("quantity : " + ordering.getQuantity());
        System.out.println("price : " + p.getPrice());
        int total = ordering.getQuantity() * p.getPrice();
        System.out.println("total : " + total);
        /*int totalCost = 0;
        for (int a=0; a<ordering.getQuantity(); a++){
            totalCost += ordering.getQuantity() * p.getPrice();
        }
        current.setTotalCost(totalCost);
        int finalCost = totalCost - current.getDp();
        current.setFinalCost(finalCost);*/
        ordering = current;
//     }
        return orderingRepository.save(ordering);
    }

    public Ordering terima(Ordering ordering){
        Ordering ord = orderingRepository.getById(ordering.getId_Order());
        ord.setStatusOrder("Diterima");
        /*String[] email = new String[1];
        email[0] = ord.getUser().getEmail();
        String text = "Hi " + ord.getUser().getName() + ",\n"+
                "   Terimakasih telah melakukan pemesanan melalui Persada Konveksi\n" +
                "pesanan anda telah kami terima dan akan segera kami proses." +
                "pesanan akan selesai pada tanggal "+ ord.getDeadline() +
                "\nPersada Konveksi\n";
        emailService.sendEmail(email[0],"Penerimaan Pesanan",text);*/
        return orderingRepository.save(ord);
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
