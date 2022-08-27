package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ordering")
@Setter @Getter
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Order")
    private Integer id_Order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    @JsonIgnoreProperties({"Ordering"})
    private Product product;

    private Integer productId;


    //
    @ManyToOne //(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"OrderingUser"})
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "dp")
    private Integer dp;

    @Column(name = "final_cost")
    private Integer finalCost;


    @Column(name = "Order_date")
    private String OrderDate;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "Order_details")
    private String OrderDetails;

    @Column(name = "percentage")
    private Integer percentage;

    @Column(name = "status_payment")
    private String statusPayment;

    @Column(name = "status_order")
    private String statusOrder;

    @Lob
    @Column(name = "receipt_dp", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @Column(name = "receipt_dp_name")
    String receiptDpName;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @OneToMany(mappedBy = "ordering")
    @JsonIgnoreProperties({"ordering"})
    @JsonIgnore
    private List<Transaction> transaction = new ArrayList<>();
}
