package tugasakhir.pemesanan.dto;


import tugasakhir.pemesanan.constant.ErrorConstant;

public class ErrorMessage {
	public String code;
	public String text;
	public String message;
//	public String type;
	
	
	public ErrorMessage(String code, String type) {
		super();
		this.code = code;
//		this.type = type;
		this.text = ErrorConstant.getMessage(code);
		this.message = ErrorConstant.getPesan(code);
	}
	
	public ErrorMessage(String code, String type, String message, String error) {
		super();
		this.code = code;
//		this.type = type;
		this.text = message;
		this.message = error;
	}
	
	
	public ErrorMessage() {
		super();
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
