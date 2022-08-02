package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordering")
public class Ordering {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Integer id_order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    @JsonIgnoreProperties({"ordering"})
    private Product product;

    private Integer productId;


    //
    @ManyToOne //(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"orderingUser"})
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "dp")
    private Integer dp;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "order_details")
    private String orderDetails;

    @Column(name = "percentage")
    private Integer percentage;

    @Column(name = "status_payment")
    private String statusPayment;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @OneToMany(mappedBy = "ordering")
    @JsonIgnoreProperties({"ordering"})
    @JsonIgnore
    private List<Transaction> transaction = new ArrayList<>();
}
