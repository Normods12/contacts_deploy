package com.contacts.mapper;


import com.contacts.dto.ModifyContactDto;
import com.contacts.entity.Contact;
import org.springframework.stereotype.Component;

@Component // <-- This is what Spring Boot has been looking for!
public class ConMapperImpl implements ConMapper {

    @Override
    public void updateContactFromDto(ModifyContactDto dto, Contact contact) {
        if (dto == null) {
            return;
        }

        if (dto.getFirstName() != null) {
            contact.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            contact.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null) {
            contact.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null) {
            contact.setEmail(dto.getEmail());
        }
        if (dto.getAddress() != null) {
            contact.setAddress(dto.getAddress());
        }
        if (dto.getCompany() != null) {
            contact.setCompany(dto.getCompany());
        }
        if (dto.getNotes() != null) {
            contact.setNotes(dto.getNotes());
        }
    }
}