package tugasakhir.pemesanan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class GenericResponse {
	public boolean failure;
	public ErrorMessage status;
	public String message;
	public GenericResponse() {
		super();
	}
	
	public GenericResponse(boolean failure, ErrorMessage status) {
		super();
		this.failure = failure;
		this.status = status;
	}

}
