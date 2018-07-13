package com.example.neosoft.daggerdemo.module;

import com.example.neosoft.daggerdemo.pojo.UserHealthDetails;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neosoft on 12/19/2017.
 */

@Module
public class UserHealthModule {

    @Provides
    UserHealthDetails provideUserHealthDetails() {
        return new UserHealthDetails();
    }
}
