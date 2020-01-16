package com.altimetrix.altimetrixjpacrudexample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altimetrix.altimetrixjpacrudexample.exception.ResourceNotFoundException;
import com.altimetrix.altimetrixjpacrudexample.model.User;
import com.altimetrix.altimetrixjpacrudexample.repository.UserRepository;
 
@Service
public class UserService {
     
    @Autowired
    UserRepository repository;
     
    public List<User> getAllUsers()
    {
        List<User> result = (List<User>) repository.findAll();
         
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<User>();
        }
    }
     
    public User getUserById(Long id) throws ResourceNotFoundException 
    {
        Optional<User> user = repository.findById(id);
         
        if(user.isPresent()) {
        	return user.get();
        } else {
            throw new ResourceNotFoundException("No user record exist for given id");
        }
    }
     
    public User createOrUpdateUser(User userEntity) 
    {
    	if(userEntity.getId()== null) 
        {
        	userEntity = repository.save(userEntity);
            return userEntity;
        } 
        else
        {
            Optional<User> user1 = repository.findById(userEntity.getId());
             
            if(user1.isPresent()) 
            {
                User newEntity = user1.get();
                newEntity.setEmailId(userEntity.getEmailId());
                newEntity.setFirstName(userEntity.getFirstName());
                newEntity.setLastName(userEntity.getLastName());
                newEntity.setPhoneNumber(userEntity.getPhoneNumber());
                newEntity.setStatus(userEntity.getStatus());
                newEntity = repository.save(newEntity);
                 
                return newEntity;
            } else {
            	userEntity = repository.save(userEntity);
                 
                return userEntity;
            }
        }
    } 
     
    public void deleteUserById(Long id) throws ResourceNotFoundException 
    {
        Optional<User> user = repository.findById(id);
         
        if(user.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No user record exist for given id");
        }
    } 
}