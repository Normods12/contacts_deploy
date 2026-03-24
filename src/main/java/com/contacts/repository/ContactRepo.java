package com.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contacts.entity.Contact;
import com.contacts.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepo extends JpaRepository<Contact, Long>{

    List<Contact> findByGroupId(Long groupId);
	List<Contact> findByUserId(Long userId);
	List<Contact> findByUser(Users user);
	List<Contact> findByUserAndIsFavouriteTrue(Users user);
	List<Contact> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContaining(
		        String name, String email, String phone );
    Contact findByIdAndUser(Long contactId, Users user);

    List<Contact> findByUserIdAndIsFavouriteTrue(Long userId);
    @Query("SELECT c FROM Contact c WHERE c.user = :user AND (" +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "c.phone LIKE CONCAT('%', :q, '%'))")
    List<Contact> searchContactsForUser(@Param("user") Users user, @Param("q") String q);

    Users findByEmail(String name);
}
