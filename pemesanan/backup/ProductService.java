package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tugasakhir.pemesanan.model.product;
import tugasakhir.pemesanan.repository.productRepository;

import java.util.List;

@Service
public class productService {

    @Autowired
    private ProductRepository productRepository;

    public product save(product product){
        if(product.getId_product()!=null){
            product current = productRepository.findById(product.getId_product()).get();
            current.setOrderings(product.getOrderings());
            current.setPhoto(product.getPhoto());
            current.setPrice(product.getPrice());
            current.setproductName(product.getproductName());
            current.setPhoto(product.getPhoto());
            product = current;
        }
        return productRepository.save(product);
    }

    public product findById(Integer id){
        return productRepository.getById(id);
    }


    // find product by name
    public List<product> findproductByName(String a){
        return productRepository.findproductByproductNameLike(a);
    }

    //findAll
    public Iterable<product> findAll(){
        return productRepository.findAll();
    }

    // find product by name
    public List<product> findByproductNameLike(String name){
        return productRepository.findproductByproductNameLike("%"+ name +"%");
    }


    // delete product

    // upload image
}
