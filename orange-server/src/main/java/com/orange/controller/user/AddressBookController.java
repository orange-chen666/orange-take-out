package com.orange.controller.user;

import com.orange.context.BaseContext;
import com.orange.entity.AddressBook;
import com.orange.result.Result;
import com.orange.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @PostMapping()
    public Result add(@RequestBody AddressBook addressBook) {
        addressBookService.add(addressBook);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        return  Result.success(addressBookService.list());
    }
    @DeleteMapping
    public Result deleteById(Long id) {
        addressBookService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }
    @PutMapping
    public Result update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return Result.success();
    }
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success();
    }
    @GetMapping("default")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.getDefault(addressBook);
        if (list != null && list.size() == 1) {
            return Result.success(list.getFirst());
        }
        return Result.error("没有查询到默认地址");
    }
}
