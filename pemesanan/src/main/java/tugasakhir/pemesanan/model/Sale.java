package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sale;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"sale"})
//    @JoinColumn(name = "id_Order")
//    private Ordering ordering;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"sale"})
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    private Integer transactionId;

    @Column(name = "lunas")
    private String lunas;

    @Column(name = "total_pay")
    private Integer totalPay;

    @Column(name = "transaction_date")
    private String transactionDate;
}
