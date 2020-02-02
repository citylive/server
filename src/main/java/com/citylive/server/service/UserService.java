package com.citylive.server.service;

import com.citylive.server.MTree.Planar.MTree2D;
import com.citylive.server.MTree.common.Data;
import com.citylive.server.dao.UserLocationRepository;
import com.citylive.server.dao.UserRepository;
import com.citylive.server.dao.UserRoleRepository;
import com.citylive.server.domain.User;
import com.citylive.server.domain.UserLocation;
import com.citylive.server.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private MTree2D mtree;

    @Transactional
    public User addUser(User user) {
        user.setEnabled(true);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User updatedUser = userRepository.save(user);
        userRoleRepository.save(UserRole.builder().userName(updatedUser.getUserName()).role("ROLE_USER").build());
        return updatedUser;
    }

    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    public void updatePassword(User user) {
        userRepository.updateUserPassword(user.getUserName(), user.getPassword());
    }

    public void updateDeviceId(String userName, String deviceId) {
        userRepository.updateUserDeviceId(userName, deviceId);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findById(userName);
    }

    public void delete(String userName) {
        //TODO Set enabled = false, instead of delete
        userRoleRepository.deleteById(userName);
        userRepository.deleteById(userName);
    }

    public Optional<UserLocation> getUserLocation(final String userName) {
        return userLocationRepository.findById(userName);
    }

    public UserLocation updateUserLocation(UserLocation userLocation) {
        Optional<UserLocation> userLocationOptional = userLocationRepository.findById(userLocation.getUserName());

        UserLocation location = null;
        if(userLocationOptional.isPresent()) {
            userLocationRepository.updateUserLocation(userLocation.getUserName(), userLocation.getLatitude(), userLocation.getLongitude());
            location = userLocationRepository.findById(userLocation.getUserName()).get();
        } else {
            location = userLocationRepository.save(userLocation.toBuilder().locationType("CURRENT").build());
        }
        mtree.add(new Data(userLocation.getUserName(),location.getLongitude(),location.getLatitude()));
        return location;

    }

    public Map<String, String> getDeviceId(String userName) {
        String deviceId = userRepository.getDeviceIdForUserId(userName);
        Map<String, String> map = new HashMap<>();
        map.put("deviceId",deviceId);
        return map;
    }
}
