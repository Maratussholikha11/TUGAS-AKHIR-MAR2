package tugasakhir.pemesanan;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PemesananApplication {

	public static void main(String[] args) {
		SpringApplication.run(PemesananApplication.class, args);


		//74%2
		//System.out.println("modulus 75%30: " + 75%30);

		//System.out.println("modulus 75%31: " + 75%31);

	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
