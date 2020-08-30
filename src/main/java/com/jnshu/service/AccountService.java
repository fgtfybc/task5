package com.jnshu.service;

import com.jnshu.pojo.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountService {
    Account login(String name);
    Account register(Account account);
    int insert(Account account);
    List<Account> selectByName(String username);
    List<Account> selectByAccount(@Param("username") String username, @Param("password") String password);

    Account selectAccount(String username);
    Account select(Account record);

    Account findById(long id);
}
