package com.orange.service;

import com.orange.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void add(AddressBook addressBook);

    List<AddressBook> list();

    void deleteById(Long id);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    AddressBook getDefalut();

    List<AddressBook> getDefault(AddressBook addressBook);
}
