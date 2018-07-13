package com.example.neosoft.daggerdemo.interfaces;

import com.example.neosoft.daggerdemo.activities.MainActivity;
import com.example.neosoft.daggerdemo.module.UserHealthModule;
import com.example.neosoft.daggerdemo.module.UserModule;

import dagger.Component;

/**
 * Created by Neosoft on 12/19/2017.
 */
@Component(modules = {UserModule.class, UserHealthModule.class})
public interface UserComponent {

    void makeUserObject(MainActivity mainActivity);
}
