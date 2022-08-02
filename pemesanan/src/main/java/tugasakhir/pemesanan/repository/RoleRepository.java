package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findByNameRole (String roleName);

    @Query("select a from Role a where a.nameRole= :username")
    Role checkRole(String username);
}
