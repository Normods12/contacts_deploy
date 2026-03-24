package com.contacts.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.contacts.dto.ContactResponse;
import com.contacts.dto.CreateGroupRequest;
import com.contacts.dto.GroupResponse;
import com.contacts.dto.MoveContactRequest;
import com.contacts.dto.UpdatedGroupRequest;
import com.contacts.service.GroupService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public GroupResponse createGroup(@RequestBody CreateGroupRequest request) {
        log.info("Received request to create group: {}", request.getName());
        return groupService.createGroup(request);
    }

    @GetMapping
    public List<GroupResponse> getAllGroups() {
        log.info("Received request to get all groups");
        return groupService.getAllGroups();
    }

    @PutMapping("/{id}")
    public GroupResponse updateGroup(
            @PathVariable Long id,
            @RequestBody UpdatedGroupRequest request) {
        log.info("Received request to update group ID: {}", id);
        return groupService.updateGroup(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Long id) {
        log.info("Received request to delete group ID: {}", id);
        return groupService.deleteGroup(id);
    }

    @GetMapping("/{id}/contacts")
    public List<ContactResponse> getContactsByGroup(@PathVariable Long id) {
        log.info("Received request to get contacts for group ID: {}", id);
        return groupService.getContactsByGroup(id);
    }

    @PatchMapping("/contacts/{id}/move-group")
    public ContactResponse moveContact(
            @PathVariable Long id,
            @RequestBody MoveContactRequest request) {
        log.info("Received request to move contact ID: {} to group ID: {}", id, request.getGroupId());
        return groupService.moveContactToGroup(id, request);
    }

    @PostMapping("/groups/{groupId}/contacts/{contactId}")
    public ResponseEntity<String> addContactToGroup(
            @PathVariable Long groupId,
            @PathVariable Long contactId,
            Principal principal) {

        log.info("Received Request to add Contact ID: {} to Group ID: {}", contactId, groupId);
        groupService.addContactToGroup(groupId, contactId, principal);

        return new ResponseEntity<>("Contact added to group successfully", HttpStatus.OK);
    }

}