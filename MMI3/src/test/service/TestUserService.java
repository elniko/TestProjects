package service;

import entity.ProfileEntity;
import exceptions.BadProfileException;
import exceptions.EntityNotExistsException;
import exceptions.UserNotExistsException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import service.interfaces.UserService;
import spring.SpringConfig;

import java.util.List;

/**
 * Created by Nick on 24/02/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestUserService {


    public static final String USER = "superadmin";
    public static final String TESTPROFILE = "TestData";

    static int profileId = 0;

    @Autowired
    UserService userService;


    @Test(expected = UserNotExistsException.class)
    public void test001AddProfileBadUser() throws UserNotExistsException {
        userService.addProfile("Bad_User", TESTPROFILE);
    }

    @Test
    public void test002AddProfile() throws UserNotExistsException {
        profileId = userService.addProfile(USER, TESTPROFILE);
        Assert.isInstanceOf(Integer.class, profileId);
    }

    @Test(expected = BadProfileException.class)
    public void test003GetProfileBadUser() throws BadProfileException {
        userService.getProfile("Bad_User", profileId);
    }

    @Test(expected = BadProfileException.class)
    public void test004GetProfileBadId() throws BadProfileException {
        userService.getProfile(USER, 0);
    }

    @Test
    public void test005GetProfile() throws BadProfileException {
         ProfileEntity profile = userService.getProfile(USER, profileId);
         Assert.notNull(profile);
         Assert.isInstanceOf(ProfileEntity.class, profile);
    }

    @Test
    public void test006GetProfile() {
        ProfileEntity profile = userService.getProfile(profileId);
        Assert.notNull(profile);
        Assert.isInstanceOf(ProfileEntity.class, profile);
    }

    @Test
    public void test007EditUserProfile() throws BadProfileException {
        String test = "New Test Data";
        userService.editUserProfile(USER, profileId , test);
        ProfileEntity profile = userService.getProfile(profileId);
        Assert.isTrue(profile.getParams().equals(test));
    }

    @Test
    public void test008EditUserProfile() throws BadProfileException, UserNotExistsException {
        String test = "New Test Data 2";
        ProfileEntity profile = userService.getProfile(profileId);
        profile.setParams(test);
        userService.editUserProfile(USER, profile);
        profile = userService.getProfile(profileId);
        Assert.isTrue(profile.getParams().equals(test));
    }

    @Test(expected =  BadProfileException.class)
    public void test009GetActiveProfile() throws BadProfileException, UserNotExistsException {
        userService.getActiveProfile(USER);
    }

    @Test(expected =  UserNotExistsException.class)
    public void test010GetActiveProfile() throws BadProfileException, UserNotExistsException {
        userService.getActiveProfile("BAD_USER");
    }

     @Test(expected = BadProfileException.class)
    public void test011SetActiveProfile() throws UserNotExistsException, BadProfileException {
        userService.setActiveUserProfile(USER, 0);
    }

    @Test
    public void test012SetActiveProfile() throws UserNotExistsException, BadProfileException {
        userService.setActiveUserProfile(USER, profileId);
        ProfileEntity profile = userService.getProfile(profileId);
        Assert.isTrue(profile.isActive());
    }

    @Test
    public void test013GetActiveProfile() throws BadProfileException, UserNotExistsException {
        ProfileEntity activeProfile = userService.getActiveProfile(USER);
        Assert.isTrue(activeProfile.isActive());
    }

    @Test
    public void test014GetProfiles() throws UserNotExistsException {
        List<ProfileEntity> profiles = userService.getProfiles(USER);
        Assert.notNull(profiles);
        Assert.isTrue(profiles.size() > 0);
    }

    @Test
    public void test015RemoveProfile() throws BadProfileException, EntityNotExistsException {
        boolean res =  userService.removeProfile(USER, profileId);
        Assert.notNull(res);
        Assert.isTrue(res);
    }


}
