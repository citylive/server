package com.citylive.server.controller;

import com.citylive.server.domain.User;
import com.citylive.server.domain.UserLocation;
import com.citylive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(@Autowired final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public User addUser(@RequestBody @Validated User user) {
        return userService.addUser(user);
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
