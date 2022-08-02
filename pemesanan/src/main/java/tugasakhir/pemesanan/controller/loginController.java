package tugasakhir.pemesanan.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class loginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        System.out.println("Tes login");
        return "login_user";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(){
        System.out.println("Tes logout");
        return "login_user";
    }

}
