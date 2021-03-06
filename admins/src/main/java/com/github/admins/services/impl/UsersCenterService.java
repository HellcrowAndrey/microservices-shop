package com.github.admins.services.impl;

import com.github.admins.dto.ForgotPassDto;
import com.github.admins.dto.LockedDto;
import com.github.admins.dto.UserRegDto;
import com.github.admins.services.IUsersCenterService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UsersCenterService implements IUsersCenterService {

    @Override
    public void createAdmins(UserRegDto payload) {

    }

    @Override
    public void createManager(UserRegDto payload) {

    }

    @Override
    public void updateIsLocked(LockedDto payload) {

    }

    @Override
    public void staffForgotPass(@Valid ForgotPassDto payload) {

    }

}
