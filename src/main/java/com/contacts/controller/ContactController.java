package com.contacts.controller;

import java.security.Principal;
import java.util.List;

import com.contacts.dto.ContactResponse;
import com.contacts.dto.ModifyContactDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contacts.dto.ContactDTO;
import com.contacts.service.ContactService;
import com.contacts.service.UserService;

@RestController
@RequestMapping("/api")
@Slf4j
public class ContactController {
	
	 @Autowired
	    private ContactService contactService;

	    @Autowired
	    private UserService userService;

	    // CREATE
	    @PostMapping("/contacts")
	    public ResponseEntity<ContactResponse>  create(@RequestBody ContactDTO dto, Principal principal) {
	        log.info("Received Request to Create Contact");
            ContactResponse response = contactService.save(dto,principal);
            log.info("Contact created successfully: ",response);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    // GET ALL
	    @GetMapping("/contacts")
	    public ResponseEntity<List<ContactResponse>> getAll(Principal principal) {
	       log.info("Received Request to Get All Contacts");
           List<ContactResponse> responses = contactService.getAll(principal);
           log.info("Fetched Contacts Successfully : ",responses);
	        return new ResponseEntity<>(responses, HttpStatus.OK);
	    }

	    // GET BY ID
	    @GetMapping("/contacts/{id}")
	    public ResponseEntity<ContactResponse> getById(@PathVariable Long id) {
	        log.info("Received Request to Get Contact of Id: ",id);
            ContactResponse response = contactService.getById(id);
            log.info("Fetched Contacts Successfully : ",response);
            return new ResponseEntity<>(response,HttpStatus.OK);

	    }

	    // UPDATE
	    @PutMapping("/contacts/{id}/edit")
	    public ResponseEntity<ContactResponse> update(@PathVariable Long id, @RequestBody ModifyContactDto dto,Principal principal) {
	        log.info("Received Request to Update Contact: ",id);
            ContactResponse response = contactService.update(id,dto);
            log.info("Updated Contact: ",response);
            return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    // DELETE
	    @DeleteMapping("/contacts/{id}")
	    public ResponseEntity<ContactResponse> delete(@PathVariable Long id) {

            log.info("Received Request to Delete Contact: ",id);
            ContactResponse dto = contactService.delete(id);
            log.info("Deleted Contact: ",dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	    }

	    // SEARCH

            @GetMapping("/search")
            public ResponseEntity<List<ContactResponse>> search(@RequestParam String q, Principal principal) {
                log.info("Received Request to Search Contacts");
                List<ContactResponse> responses = contactService.search(q,principal);
                log.info("Fetched Contacts Successfully : ",responses);
                return new ResponseEntity<>(responses, HttpStatus.OK);
            }

	    // FAVOURITES
	    @GetMapping("/contacts/favourites")
	    public ResponseEntity<List<ContactResponse>> favourites(Principal principal) {
	        log.info("Received request to fetch Favourite contact ");
            List<ContactResponse> responses = contactService.getFavourites(principal);
            log.info("Fetched Favourite Contacts: ",responses);
	        return new ResponseEntity<>(responses,HttpStatus.OK);
	    }

	    // TOGGLE FAVOURITE
	    @PatchMapping("contacts/{id}/favourite")
	    public ResponseEntity<ContactResponse> toggle(@PathVariable Long id) {
	        log.info("Received Request to toggle Favourite Contact : ",id);
            ContactResponse response = contactService.toggleFavourite(id);
            log.info("Toggled Favourite Contact: ",response);
            return new ResponseEntity<>(response, HttpStatus.OK);
	    }
}
