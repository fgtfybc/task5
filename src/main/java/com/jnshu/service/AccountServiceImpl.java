package com.jnshu.service;

import com.jnshu.dao.AccoutMapper;
import com.jnshu.pojo.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccoutMapper accoutMapper;

    @Override
    public Account login(String username) {
        return accoutMapper.login(username);
    }

    @Override
    public Account register(Account account) {
        return accoutMapper.register(account);
    }

    @Override
    public int insert(Account account) {
        return accoutMapper.insert(account);
    }

    @Override
    public List<Account> selectByName(String username) {
        return accoutMapper.selectByName(username);
    }

    @Override
    public List<Account> selectByAccount(@Param("username") String username, @Param("password") String password) {
        return accoutMapper.selectByAccount(username,password);
    }

    @Override
    public Account selectAccount(String username) {
        return accoutMapper.selectAccount(username);
    }

    @Override
    public Account select(Account record) {
        return accoutMapper.select(record);
    }

    @Override
    public Account findById(long id) {
        return accoutMapper.findById(id);
    }

    /*@Override
    public Account selectAccount(String username) {
        return accoutMapper.selectAccount(username);
    }

    @Override
    public Account select(Account account) {
        return accoutMapper.select(account);
    }*/
}
