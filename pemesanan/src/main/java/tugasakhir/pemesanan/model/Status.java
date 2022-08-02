package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "note_trx")
    private String noteTrx;


    @OneToMany(mappedBy = "status")
    @JsonIgnoreProperties({"status"})
    @JsonIgnore
    private List<Transaction> transaction = new ArrayList<>();

}
