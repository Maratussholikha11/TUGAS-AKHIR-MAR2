package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_user")
@Setter
@Getter
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    private Integer idUser;
    private Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, unique = true)
    public Integer getIdUser() {
        return idUser;
    }



    /*@OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();


    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getNameRole().equals(roleName)) {
                return true;
            }
        }

        return false;
    }*/

    //    @Transient
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_role")
    @JsonIgnoreProperties({"user"})
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;

    }

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "no_telepon")
    private String noTelepon;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //
    //targetEntity=Student.class, mappedBy="college", fetch=FetchType.EAGER
    /*@Access(AccessType.PROPERTY)
    @OneToMany(targetEntity= Ordering.class, mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties({"user"})
    @JsonIgnore
    private List<Ordering> orderingUser = new ArrayList<>();
*/


   /* public List<Ordering> getOrdering() {
        return orderingUser;
    }

    public void setOrdering(List<Ordering> ordering) {
        for( Ordering data : Ordering){
            data.setUser(this);
        }
        this.OrderingUser = Ordering;
    }*/



    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> result = new ArrayList<SimpleGrantedAuthority>();
        result.add(new SimpleGrantedAuthority("ROLE_" + getRole().getNameRole()));
        return result;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

}
