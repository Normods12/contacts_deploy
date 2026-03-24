package com.contacts.controller;

import com.contacts.dto.StatsResponseDto;
import com.contacts.dto.UsersResponseDto;
import com.contacts.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersResponseDto>> getAllUsers(){
        log.info("Received request to get all users");
        List<UsersResponseDto> usersResponseDto = adminService.getAllUsers();
        log.info("Fetched all Users");
        return new ResponseEntity<>(usersResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersResponseDto> deleteUser(@PathVariable Long id){
        log.info("Received request to delete user {}", id);
       UsersResponseDto dto =  adminService.deleteUser(id);
        log.info("Deleted user {}", id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatsResponseDto> displayStats(){
        log.info("Received request to display stats");
        StatsResponseDto dto = adminService.displayStats();
        log.info("Fetched stats");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
