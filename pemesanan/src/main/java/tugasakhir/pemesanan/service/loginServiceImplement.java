package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.UserRepository;

@Service
public class loginServiceImplement  {

    @Autowired
    UserRepository userRepository;

    public User login(String username, String password) {
        User searchUser = userRepository.getUserByUsernameAndPassword(username, password);
        System.out.println("password user adalah " + searchUser.getPassword());
        return searchUser;
    }


}
