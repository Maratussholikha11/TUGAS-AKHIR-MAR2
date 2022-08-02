package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Setter
@Getter
@NoArgsConstructor
public class Role {

    private Integer idRole;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    public Integer getIdRole() {
        return idRole;
    }

    @Column(name = "name_role")
    private String nameRole;

    //before
    @JsonIgnoreProperties({"user"})
    private List<User> user;


    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"role"})
    @JsonIgnore
    public List<User> getUser() {
        return user;
    }
//before


}