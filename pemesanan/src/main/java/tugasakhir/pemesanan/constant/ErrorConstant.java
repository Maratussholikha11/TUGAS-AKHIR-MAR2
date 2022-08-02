package tugasakhir.pemesanan.constant;

import java.util.HashMap;
import java.util.Map;

public class ErrorConstant {

    public static final String BEARER = "Bearer";
    public static final String OPEN = "Open";
    public static final String ONPROGRESS = "On Progress";
    public static final String FINISH = "Finish";



//    ------------

    private static Map<String, String> errorMessage = new HashMap<String, String>();
    private static Map<String, String> pesanError = new HashMap<String, String>();

    public static final String ERROR = "01";

    public static final String SUCCESS="00";
    public static final String PENDING="01";
    public static final String FAILED="02";

    public static final String SUCCESS_BILLER="000";
    public static final String PENDING_BILLER="002";
    public static final String FAILED_BILLER="201";

    public static final String INCOMPLETE_PARAMTER="05";
    public static final String INVALID_FORMAT="06";

    static {
        pesanError.put(ERROR, "Something wrong!!");
        pesanError.put(PENDING, "Anda sudah punya tabungan");
        pesanError.put(FAILED, "Permintaan tidak dapat diproses");
        pesanError.put(SUCCESS_BILLER, "Alamat anda tidak ditemukan");

    }

    public static String getMessage(String code) {
        String message = errorMessage.get(code);
        if (message == null) {
            message = "Internal Server Error : " + code;
        }
        return message;
    }

    public static String getPesan(String code) {
        String message = pesanError.get(code);
        if (message == null) {
            message = "Internal Server Error : " + code;
        }
        return message;
    }

    }
