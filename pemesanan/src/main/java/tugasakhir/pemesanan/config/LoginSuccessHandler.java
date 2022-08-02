/*
package tugasakhir.pemesanan.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tugasakhir.pemesanan.service.UserDetailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        UserDetailService userDetails = (UserDetailService) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if (userDetails.hasRole("ADMIN")) {
            redirectURL = "index";
        } else if (userDetails.hasRole("CUSTOMER")) {
            redirectURL = "index";
        } else if (userDetails.hasRole("OWNER")) {
            redirectURL = "index";
        }

        response.sendRedirect(redirectURL);

    }
}
*/
