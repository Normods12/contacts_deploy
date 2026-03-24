package com.contacts.mapper;

import com.contacts.dto.ContactDTO;
import com.contacts.dto.ContactResponse;
import com.contacts.dto.ModifyContactDto;
import com.contacts.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public class ContactMapper {

    public static ContactDTO toDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();


        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        dto.setAddress(contact.getAddress());
        dto.setCompany(contact.getCompany());
        dto.setNotes(contact.getNotes());
        dto.setIsFavourite(contact.getIsFavourite());

        return dto;
    }

    public static Contact toEntity(ContactDTO dto) {
        Contact contact = new Contact();

        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        contact.setAddress(dto.getAddress());
        contact.setCompany(dto.getCompany());
        contact.setNotes(dto.getNotes());
        contact.setIsFavourite(dto.getIsFavourite());

        return contact;
    }

    public static ContactResponse toResponse(Contact contact) {
        ContactResponse dto = new ContactResponse();

        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        dto.setIsFavourite(contact.getIsFavourite());

        return dto;
    }


    @Mapper(componentModel = "spring",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface ContactMappers {
        void updateContactFromDto(ModifyContactDto dto, @MappingTarget Contact contact);
    }

}
