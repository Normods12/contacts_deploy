package com.contacts.repository;


import com.contacts.entity.Contact;
import com.contacts.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByGroupId(Long groupId);

}