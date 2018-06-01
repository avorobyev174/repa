package com.javarush.task.task36.task3608.model;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.model.service.UserService;
import com.javarush.task.task36.task3608.model.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainModel implements Model{
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        modelData.setDisplayDeletedUserList(false);
        //ArrayList<User> users = new ArrayList<>(userService.getUsersBetweenLevels(1, 100));
        modelData.setUsers(getAllUsers());
    }


    public void loadDeletedUsers() {
        modelData.setDisplayDeletedUserList(true);
        ArrayList<User> users = new ArrayList<>(userService.getAllDeletedUsers());
        modelData.setUsers(users);
    }

    @Override
    public void loadUserById(long userId) {
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    @Override
    public void deleteUserById(long id) {
        User user = userService.deleteUser(id);
        ArrayList<User> users = new ArrayList<User>(getAllUsers());
        users.remove(user);
        modelData.setUsers(users);
    }

    private List<User> getAllUsers() {
        return userService.filterOnlyActiveUsers(userService.getUsersBetweenLevels(1, 100));
    }

    @Override
    public void changeUserData(String name, long id, int level) {

        User user = userService.createOrUpdateUser(name, id, level);
       /* int index = 0;
        for (User u : users) {
            if (u.getId() == user.getId()) {
                break;
            }
            index++;
        }
        users.remove(index);
        users.add(index, user);*/
        ArrayList<User> users = new ArrayList<User>(getAllUsers());
        modelData.setUsers(users);
    }
}
