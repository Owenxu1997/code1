package com.tjj.bysjerp.bus.service;

import com.tjj.bysjerp.bus.domain.Apilist;
import com.tjj.bysjerp.bus.mapper.ApilistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApilistService {

    @Autowired
    ApilistMapper apilistMapper;

    public List<Apilist> getAllApi() {
        return apilistMapper.getAllApi();
    };

    public List<Apilist> getAllApiReviewed() {
        return apilistMapper.getAllApiReviewed();
    };

    public int addApi(Apilist api) {
        return apilistMapper.addApi(api);
    }

    public List<Apilist> getTest() {
        return apilistMapper.getTest();
    }

    public List<Apilist> getReview() {
        return apilistMapper.getReview();
    }

    public List<Apilist> getPersonalApi(int userId) {
        return apilistMapper.getPersonalApi(userId);
    }

    public List<Apilist> getPersonalReview(int userId) {
        return apilistMapper.getPersonalReview(userId);
    }

    public List<Apilist> getPersonalReviewed(int userId) {
        return apilistMapper.getPersonalReviewed(userId);
    }

    public List<Apilist> getPersonalTest(int userId) {
        return apilistMapper.getPersonalTest(userId);
    }

    public List<Apilist> getPersonalTested(int userId) {
        return apilistMapper.getPersonalTested(userId);
    }

    public Apilist getApiById(int id) {
        return apilistMapper.getApiById(id);
    }

    public int deleteById(int id) {
        return apilistMapper.deleteById(id);
    }

    public int reviewPassed(int id) {
        return apilistMapper.reviewPassed(id);
    }

    public int testPassed(int id) {
        return apilistMapper.testPassed(id);
    }

}


