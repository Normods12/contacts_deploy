package com.contacts.service;

import com.contacts.exception.ResourceNotFoundException;
import com.contacts.exception.UnauthorizedResourceAccessException;
import com.contacts.repository.ContactRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.contacts.dto.ContactResponse;
import com.contacts.dto.CreateGroupRequest;
import com.contacts.dto.GroupResponse;
import com.contacts.dto.MoveContactRequest;
import com.contacts.dto.UpdatedGroupRequest;
import com.contacts.entity.Contact;
import com.contacts.entity.Group;
import com.contacts.entity.Users;
import com.contacts.repository.ContactRepository;
import com.contacts.repository.GroupRepository;
import com.contacts.repository.UserRepo;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;

    private final UserRepo userRepository;
    private final ContactRepo contactRepository;

    private Users getCurrentUser() {
        log.debug("Extracting email from Security Context");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        log.debug("Fetching user details for email: {}", email);
        Users user = userRepository.findByEmail(email);

        if (user == null) {
            log.warn("User not found in database for email: {}", email);
            throw new RuntimeException("User not found");
        }

        log.debug("User successfully authenticated: {}", user.getId());
        return user;
    }

    public GroupResponse createGroup(CreateGroupRequest request) {
        log.info("Creating new group for request: {}", request.getName());
        Users user = getCurrentUser();

        Group group = new Group();
        group.setName(request.getName());
        group.setColorTag(request.getColorTag());
        group.setUser(user);

        log.debug("Saving new group to database");
        Group saved = groupRepository.save(group);

        log.info("Group created successfully with ID: {}", saved.getId());
        return mapToGroupResponse(saved);
    }

    public List<GroupResponse> getAllGroups() {
        log.info("Fetching all groups for current user");
        Users user = getCurrentUser();

        log.debug("Querying database for groups associated with user ID: {}", user.getId());
        List<Group> groups = groupRepository.findByUserId(Long.valueOf(user.getId()));

        log.info("Found {} groups for user ID: {}", groups.size(), user.getId());
        return groups.stream()
                .map(this::mapToGroupResponse)
                .toList();
    }

    public GroupResponse updateGroup(Long id, UpdatedGroupRequest request) {
        log.info("Updating group ID: {}", id);
        Users user = getCurrentUser();

        log.debug("Fetching group ID: {} from database", id);
        Group group = groupRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Group not found"));

        if (group.getUser() == null ||
                group.getUser().getId() != user.getId()) {
            log.warn("Unauthorized update attempt on group ID: {} by user ID: {}", id, user.getId());
            throw new UnauthorizedResourceAccessException("Unauthorized Access");
        }

        group.setName(request.getName());
        group.setColorTag(request.getColorTag());

        log.debug("Saving updated group ID: {} to database", id);
        Group updated = groupRepository.save(group);

        log.info("Group ID: {} updated successfully", updated.getId());
        return mapToGroupResponse(updated);
    }

    public String deleteGroup(Long id) {
        log.info("Deleting group ID: {}", id);
        Users user = getCurrentUser();

        log.debug("Fetching group ID: {} from database", id);
        Group group = groupRepository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Group not found"));

        if (group.getUser() == null ||
                group.getUser().getId() != user.getId()) {
            log.warn("Unauthorized delete attempt on group ID: {} by user ID: {}", id, user.getId());
            throw new UnauthorizedResourceAccessException("Unauthorized Access");
        }

        if (group.getContacts() != null) {
            log.debug("Removing group reference from {} contacts", group.getContacts().size());
            group.getContacts().forEach(contact -> contact.setGroup(null));
        }

        log.debug("Deleting group ID: {} from database", id);
        groupRepository.delete(group);

        log.info("Group ID: {} deleted successfully", id);
        return "Group deleted successfully";
    }

    public List<ContactResponse> getContactsByGroup(Long groupId) {
        log.info("Fetching contacts for group ID: {}", groupId);
        Users user = getCurrentUser();

        log.debug("Fetching group ID: {} from database", groupId);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()->  new ResourceNotFoundException("Group not found"));

        if (group.getUser() == null ||
                group.getUser().getId() != user.getId()) {
            log.warn("Unauthorized access attempt to contacts in group ID: {} by user ID: {}", groupId, user.getId());
            throw new UnauthorizedResourceAccessException("Unauthorized Access");
        }

        log.debug("Querying database for contacts in group ID: {}", groupId);
        List<Contact> contacts = contactRepository.findByGroupId(groupId);

        log.info("Found {} contacts in group ID: {}", contacts.size(), groupId);
        return contacts.stream()
                .map(this::mapToContactResponse)
                .toList();
    }

    public ContactResponse moveContactToGroup(Long contactId, MoveContactRequest request) {
        log.info("Moving contact ID: {} to group ID: {}", contactId, request.getGroupId());
        Users user = getCurrentUser();

        log.debug("Fetching contact ID: {} from database", contactId);
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));

        if (contact.getUser() == null ||
                contact.getUser().getId() != user.getId()) {
            log.warn("Unauthorized attempt to move contact ID: {} by user ID: {}", contactId, user.getId());
            throw new UnauthorizedResourceAccessException("Unauthorized Access");
        }

        log.debug("Fetching target group ID: {} from database", request.getGroupId());
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(()->  new ResourceNotFoundException("Group not found"));

        if (group.getUser() == null ||
                group.getUser().getId() != user.getId()) {
            log.warn("Unauthorized attempt to access target group ID: {} by user ID: {}", request.getGroupId(), user.getId());
            throw new UnauthorizedResourceAccessException("Unauthorized Access");
        }

        log.debug("Updating group reference for contact ID: {}", contactId);
        contact.setGroup(group);

        log.debug("Saving updated contact ID: {} to database", contactId);
        Contact updated = contactRepository.save(contact);

        log.info("Contact ID: {} successfully moved to group ID: {}", updated.getId(), group.getId());
        return mapToContactResponse(updated);
    }

    private GroupResponse mapToGroupResponse(Group group) {
        log.debug("Mapping Group entity to GroupResponse DTO for group ID: {}", group.getId());
        GroupResponse res = new GroupResponse();
        res.setId(group.getId());
        res.setName(group.getName());
        res.setColorTag(group.getColorTag());
        res.setCreatedDate(group.getCreatedDate());
        return res;
    }

    private ContactResponse mapToContactResponse(Contact c) {
        log.debug("Mapping Contact entity to ContactResponse DTO for contact ID: {}", c.getId());
        ContactResponse res = new ContactResponse();
        res.setId(c.getId());
        res.setFirstName(c.getFirstName());
        res.setLastName(c.getLastName());
        res.setPhone(c.getPhone());
        res.setEmail(c.getEmail());
        res.setIsFavourite(c.getIsFavourite());
        return res;
    }


    public void addContactToGroup(Long groupId, Long contactId, Principal principal) {
        Users user = userRepository.findByEmail(principal.getName());

        Group group = groupRepository.findByIdAndUser(groupId, user);

        Contact contact = contactRepository.findByIdAndUser(contactId, user);


        contact.setGroup(group);
        contactRepository.save(contact);
    }
}