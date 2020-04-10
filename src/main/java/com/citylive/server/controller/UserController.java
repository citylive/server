package com.citylive.server.controller;

import com.citylive.server.domain.User;
import com.citylive.server.domain.UserLocation;
import com.citylive.server.otp.OtpService;
import com.citylive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<User> addUser(@RequestParam Integer otp, @RequestBody @Validated User user) {
        if (!otpService.isValidOTP(otp, user.getUserName())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } else {
            User addedUser = userService.addUser(user);
            return ResponseEntity.ok(addedUser);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update")
    public void updatePassword(@RequestBody @Validated User user) {
        userService.updatePassword(user);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update/deviceId")
    public void updateDeviceId(final String userName, final String deviceId) {
        userService.updateDeviceId(userName, deviceId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/deviceId")
    public Map<String, String> getDeviceId(final String userName) {
        return userService.getDeviceId(userName);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/find")
    public Optional<User> findByUserName(String userName) {
        return userService.findByUserName(userName);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public void delete(String userName) {
        userService.delete(userName);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public Iterable<User> getAllUser() {
        return userService.getAllUser();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/location")
    public UserLocation getUserLocation(final String userName) {
        Optional<UserLocation> userLocation = userService.getUserLocation(userName);
        return userLocation.isPresent() ? userLocation.get() : UserLocation.builder().userName(userName).build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/location")
    public UserLocation updateUserLocation(@RequestBody @Validated UserLocation userLocation) {
        return userService.updateUserLocation(userLocation);
    }
}
