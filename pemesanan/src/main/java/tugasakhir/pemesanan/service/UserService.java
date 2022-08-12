package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        if(user.getIdUser()!=null){
            User current = userRepository.findById(user.getIdUser()).get();
            current.setName(user.getName());
            current.setAddress(user.getAddress());
            current.setEmail(user.getEmail());
            current.setNoTelepon(user.getNoTelepon());
            current.setPassword(user.getPassword());
            current.setUsername(user.getUsername());
            user = current;
        }
        return userRepository.save(user);
    }
    public User findOne(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return null;
        }
        return user.get();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<User> findByNameOrEmail(String name){
        return userRepository.findByNameContainsOrEmailContains(name, name);
    }

    public List<User> getCustomer(){
        return userRepository.allCustomer();
    }

    public List<User> getAdmin(){
        return userRepository.allAdmin();
    }


}
