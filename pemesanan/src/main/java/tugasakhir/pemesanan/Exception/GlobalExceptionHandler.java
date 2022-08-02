package tugasakhir.pemesanan.Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tugasakhir.pemesanan.constant.ErrorConstant;
import tugasakhir.pemesanan.dto.BusinessException;
import tugasakhir.pemesanan.dto.ErrorMessage;
import tugasakhir.pemesanan.dto.GenericResponse;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<GenericResponse> generalException(BusinessException ex, HttpServletRequest request) {
        LOGGER.error("Error service ", ex);
        ex.printStackTrace();
        return ResponseEntity.ok(new GenericResponse(true,
                new ErrorMessage(ex.getCode(), request.getRequestURI(), ErrorConstant.getMessage(ex.getCode()), ErrorConstant.getPesan(ex.getCode()))));
    }

   /* @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericResponse> handleBadService(Exception ex, HttpServletRequest request) {
        LOGGER.error("Error service ", ex);
        ex.printStackTrace();
        return ResponseEntity
                .ok(new GenericResponse(true, new ErrorMessage("5D", request.getRequestURI(), " Service Down", "Terjadi kesalahan")));
    }*/

    /*@ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<GenericResponse> handleInvalidToken(final UsernameNotFoundException ex) {
        LOGGER.error("error : "+ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.ok(new GenericResponse(true, new ErrorMessage("68", null, "Invalid Token", "Token Tidak Valid")));
    }
*/
   /* @ExceptionHandler({CustomException.class})
    public ResponseEntity<GenericResponse> generalException(CustomException ex, HttpServletRequest request) {
        ex.printStackTrace();
        return ResponseEntity.ok(new GenericResponse(true,new ErrorMessage(ex.getCode(), request.getRequestURI(), ex.getMessage(), ex.getMessage())));
    }*/
}
