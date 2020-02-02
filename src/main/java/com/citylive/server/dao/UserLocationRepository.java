package com.citylive.server.dao;

import com.citylive.server.domain.UserLocation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserLocationRepository extends CrudRepository<UserLocation, String> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "Update CityLive.User_Location Set latitude=:latitude, longitude=:longitude where UserName=:userName", nativeQuery = true)
    void updateUserLocation(@Param("userName") String userName, @Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
