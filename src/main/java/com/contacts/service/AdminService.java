package com.contacts.service;

import com.contacts.dto.StatsResponseDto;
import com.contacts.dto.UsersResponseDto;
import com.contacts.entity.Users;
import com.contacts.exception.ResourceNotFoundException;
import com.contacts.repository.ContactRepo;
import com.contacts.repository.GroupRepository;
import com.contacts.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    GroupRepository groupRepo;

    public List<UsersResponseDto> getAllUsers() {
        log.info("Fetching users from database...");
        List<Users> users = userRepo.findAll();
        log.info("Fetching users from database complete.");
        List<UsersResponseDto> usersList = new ArrayList<>();
        for (Users user : users) {
            UsersResponseDto usersDto = new UsersResponseDto();
            usersDto.setId(user.getId());
            usersDto.setFirstName(user.getFirstName());
            usersDto.setEmail(user.getEmail());
            usersList.add(usersDto);

        }
        return usersList;
    }

    @Transactional
    public UsersResponseDto deleteUser(long id) {
        log.debug("Checking if user Exists");
        Users user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepo.delete(user);
        return new UsersResponseDto(user.getId(),user.getFirstName(),user.getEmail());
    }

    public StatsResponseDto displayStats() {
        log.info("Fetching stats from database...");
        StatsResponseDto statsDto = new StatsResponseDto();
        log.info("Fetching User Count");
        statsDto.setTotalUsers(userRepo.count());
        log.info("Fetching Contact Count");
        statsDto.setTotalContacts(contactRepo.count());
           log.info("Fetching Group Count");
           statsDto.setGroupCount(groupRepo.count());

           return statsDto;
    }





}
