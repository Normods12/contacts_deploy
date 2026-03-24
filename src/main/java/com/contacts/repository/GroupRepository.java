package com.contacts.repository;



import com.contacts.entity.Group;
import com.contacts.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUserId(Long userId);

     Group findByIdAndUser(Long groupId, Users user);
}
