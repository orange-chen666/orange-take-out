package com.orange.service.impl;

import com.orange.context.BaseContext;
import com.orange.entity.AddressBook;
import com.orange.mapper.AddressBookMapper;
import com.orange.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     * @param addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);
    }
    @Override
    public List<AddressBook> list() {
        return addressBookMapper.listByUserId(BaseContext.getCurrentId());
    }

    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        return addressBook;
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    @Override
    public void setDefault(AddressBook addressBook) {
        //将原来的地址都弄成0否
        addressBookMapper.setDefault(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);

    }

    @Override
    public AddressBook getDefalut() {
        return addressBookMapper.list();
    }

    @Override
    public List<AddressBook> getDefault(AddressBook addressBook) {
        return addressBookMapper.getDefault(addressBook);
    }
}
