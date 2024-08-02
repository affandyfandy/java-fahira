package com.lecture15.assignment3.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lecture15.assignment3.data.entity.ApiKey;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer>{
    
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM ApiKey a WHERE a.apiKey = :apiKey")
    boolean existsByApiKey(@Param("apiKey") String apiKey);


    @Query("SELECT a.username FROM ApiKey a WHERE a.apiKey = :apiKey")
    String findUsernameByApiKey(@Param("apiKey") String apiKey);

    @Query("SELECT a FROM ApiKey a WHERE a.apiKey = :apiKey")
    ApiKey findByApiKey(@Param("apiKey") String apiKey);
}
