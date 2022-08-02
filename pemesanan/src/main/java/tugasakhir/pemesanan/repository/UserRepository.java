package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.User;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String name);

    @Query("select u from User u where u.name like :nama")
    public User findUserByName(@PathParam("nama") String nama);

    @Query(value = "SELECT u FROM User u WHERE u.username= :username and  u.password= :password")
    public User getUserByUsernameAndPassword(String username, String password);

    List<User> findByNameContainsOrEmailContains(String name, String email);


    @Query(value = "SELECT u FROM User u WHERE u.role.idRole = 8")
    List<User> allCustomer();

    @Query(value = "SELECT u FROM User u WHERE u.role.idRole = 7")
    List<User> allAdmin();


}
