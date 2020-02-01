package com.citylive.server.otp;

import com.citylive.server.dao.UserRepository;
import com.citylive.server.domain.User;
import com.citylive.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class OtpController {
    @Autowired
    private OtpService otpService;
    @Autowired
    private MyEmailService myEmailService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/generateOtp")
    public Map<String, String> generateOtp(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        Optional<User> byUserName = userService.findByUserName(user.getUserName());
        final List<User> userByEmail = userRepository.getUserByEmail(user.getEmail());
        if (!byUserName.isPresent() && user.getEmail() != null && userByEmail != null && userByEmail.isEmpty()) {
            int otp = otpService.generateOTP(user.getUserName());
            log.info("{} OTP : " + otp, user.getUserName());
            //Generate The Template to send OTP
            EmailTemplate template = new EmailTemplate();
            Map<String, String> replacements = new HashMap<>();
            replacements.put("user", user.getUserName());
            replacements.put("otpnum", String.valueOf(otp));
            String message = template.getTemplate(replacements);
            myEmailService.sendOtpMessage(user.getEmail(), "Welcome to CityLive", message);
            map.put("Status", "OTP_SENT");
        } else if (byUserName.isPresent()) {
            map.put("Status", "USER_EXISTS");
        } else {
            map.put("Status", "EMAIL_EXISTS");
        }
        return map;
    }

}