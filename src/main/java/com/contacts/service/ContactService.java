package com.contacts.service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import com.contacts.dto.ContactResponse;
import com.contacts.dto.ModifyContactDto;
import com.contacts.exception.ResourceNotFoundException;
import com.contacts.repository.UserRepo;
import com.contacts.mapper.ConMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contacts.dto.ContactDTO;
import com.contacts.entity.Contact;
import com.contacts.entity.Users;
import com.contacts.repository.ContactRepo;
import com.contacts.mapper.ContactMapper;

@Service
@Slf4j
public class ContactService {
	
    @Autowired
    private ContactRepo contactRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConMapper conMapper;


    public ContactResponse save(ContactDTO dto, Principal principal) {
        Users user = userRepo.findByEmail(principal.getName());
        log.debug("Checking if user exists");
        if(user==null){
            log.warn("user not found with email {}", principal.getName());
          throw   new ResourceNotFoundException("User Not Found");
        }
        Contact contact = ContactMapper.toEntity(dto);
        contact.setUser(user);
        contactRepository.save(contact);
        log.info("Saving contact {}", contact);
        return new ContactResponse(contact.getId(),contact.getFirstName(),contact.getLastName(),contact.getPhone(),contact.getEmail(),contact.getIsFavourite());
    }

    public List<ContactResponse> getAll(Principal principal) {
        Users user = userRepo.findByEmail(principal.getName());
        log.debug("Checking if user exists");
        if(user==null){
            log.warn("user not found with email {}", principal.getName());
            throw   new ResourceNotFoundException("User Not Found");
        }
        return contactRepository.findByUser(user)
                .stream()
                .map(ContactMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ContactResponse getById(Long id) {
        log.debug("Checking if contact exists");
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));
      log.info("Getting contact {}", contact);
        return ContactMapper.toResponse(contact);
    }


    public ContactResponse update(Long id, ModifyContactDto dto) {
        log.debug("Checking if contact exists");
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));
        log.info("Updating contact details");
        conMapper.updateContactFromDto(dto,contact);
        contactRepository.save(contact);
        ContactResponse response = ContactMapper.toResponse(contact);
        return response;
    }


    public ContactResponse delete(Long id) {
        log.debug("Checking if contact exists");
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Contact not found"));
        ContactResponse response = ContactMapper.toResponse(contact);
        log.info("Deleting Contact {}", contact);
        contactRepository.delete(contact);
        return response;
    }

    public List<ContactResponse> search(String q, Principal principal) {
        log.debug("Checking if user exists");
        Users user = userRepo.findByEmail(principal.getName());
        if (user == null) {
            log.warn("User not found");
            throw new ResourceNotFoundException("User not found");
        }
        log.debug("Fetching contact lists");
        List<Contact> contacts = contactRepository.searchContactsForUser(user, q);
        log.info("Contact lists fetched {}", contacts);
        return contacts.stream()
                .map(ContactMapper::toResponse)
                .toList();
    }

    public List<ContactResponse> getFavourites(Principal  principal) {
       log.debug("Checking if user exists");
        Users user = userRepo.findByEmail(principal.getName());

        return contactRepository.findByUserAndIsFavouriteTrue(user)
                .stream()
                .map(ContactMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ContactResponse toggleFavourite(Long id) {
        log.debug("Checking if contact exists");
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Contact Not Found"));
        log.info("Toggling favourite contact {}", contact);
        contact.setIsFavourite(!contact.getIsFavourite());
        return ContactMapper.toResponse(contactRepository.save(contact));
    }
}
