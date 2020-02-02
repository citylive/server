package com.citylive.server.dao;

import com.citylive.server.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "Update CityLive.User Set Password=:password where UserName=:userName", nativeQuery = true)
    void updateUserPassword(@Param("userName") String userName, @Param("password") String password);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "Update CityLive.User Set deviceId=:deviceId where UserName=:userName", nativeQuery = true)
    void updateUserDeviceId(@Param("userName") String userName, @Param("deviceId") String deviceId);

    @Query(value = "Select deviceId From CityLive.User where UserName=:userName", nativeQuery = true)
    String getDeviceIdForUserId(@Param("userName") String userName);

}
