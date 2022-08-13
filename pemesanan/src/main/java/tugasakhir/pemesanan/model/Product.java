package tugasakhir.pemesanan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
@Setter
@Getter
@NoArgsConstructor
@Data
public class Product {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_product;

	@OneToMany(mappedBy = "product")
	@JsonIgnoreProperties({"product"})
	@JsonIgnore
	private List<Ordering> orderings = new ArrayList<>();

	@NotNull(message = "product name is required.")
	@Column(name = "product_name")
	private String productName;

	@NotNull(message = "product description is required.")
	//@Size(min = 10, max = 1000, message = "product description must be between 10 and 1000 characters.")
	@Column(name = "description", nullable = false)
	private String description;	
	
	//@Size(min = 2, max = 10)
	@Column(name = "price",nullable = false, precision = 10, scale = 2)
    private Integer price;
	
	@Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

	@Column(name = "file_name")
	String fileName;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


   
}

