package com.contacts.mapper;

import com.contacts.dto.ModifyContactDto;
import com.contacts.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


public interface ConMapper {

    void updateContactFromDto(ModifyContactDto dto, @MappingTarget Contact contact);

}
