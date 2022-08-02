package tugasakhir.pemesanan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product){
        if(product.getId_product()!=null){
            Product current = productRepository.findById(product.getId_product()).get();
            current.setOrderings(product.getOrderings());
            current.setPhoto(product.getPhoto());
            current.setPrice(product.getPrice());
            current.setProductName(product.getProductName());
            current.setPhoto(product.getPhoto());
            product = current;
        }
        return productRepository.save(product);
    }

    public Product findById(Integer id){
        return productRepository.getById(id);
    }


    // find product by name
    public List<Product> findProductByName(String a){
        return productRepository.findProductByProductNameLike(a);
    }

    //findAll
    public Iterable<Product> findAll(){
        return productRepository.findAll();
    }

    // find product by name
    public List<Product> findByProductNameLike(String name){
        return productRepository.findProductByProductNameLike("%"+ name +"%");
    }


    // delete product

    // upload image
}
