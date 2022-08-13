package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"transaction"})
    @JoinColumn(name = "id_Order")
    private Ordering ordering;


    private Integer OrderId;

    //
    @OneToMany(mappedBy = "transaction",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"transaction"})
    @JsonIgnore
    private List<Sale> sale = new ArrayList<>();



    public List<Sale> getSale() {
        return sale;
    }

    public void setSale(List<Sale> sale) {
        for( Sale data : sale){
            data.setTransaction(this);
        }
        this.sale = sale;
    }

    @Lob
    @Column(name = "receipt", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @Column(name = "receipt_name")
    String receiptName;

    @Column(name = "lunas")
    private String lunas;

    @Column(name = "total_pay")
    private Integer totalPay;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "note")
    private String note;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"OrderingUserT"})
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"transaction"})
    @JoinColumn(name = "id_status")
    private Status status;
}
