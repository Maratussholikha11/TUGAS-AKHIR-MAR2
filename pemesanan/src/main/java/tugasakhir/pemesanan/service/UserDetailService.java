package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tugasakhir.pemesanan.model.Role;
import tugasakhir.pemesanan.model.User;
import tugasakhir.pemesanan.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {


//    private User user;

    /*@Autowired
    private User user;*/


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                String.format("User with email '%s' not found", s))
                );
    }

    public List<User>getAll(){
        return userRepository.findAll();
    }

    public User save (User user){
        boolean userExist = userRepository.findByUsername(user.getUsername()).isPresent();
        if (userExist){
            throw new RuntimeException(String.format("user with username '%s' is present", user.getUsername()));
        }

        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userRepository.save(user);

    }

 /*   @Autowired
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for(Role r : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getNameRole()));
        }
        return authorities;
  */  }
/*
    public boolean hasRole(String roleName) {
        return this.user.hasRole(roleName);
    }*/


