package co.edu.udec.infrastructure.controllers;

import co.edu.udec.application.services.UserApplicationService;
import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.Email;
import co.edu.udec.domain.model.valueObjects.FullName;

import java.util.List;

public class UserController {

    private final UserApplicationService userService;

    public UserController(UserApplicationService userService) {
        this.userService = userService;
    }

    public String createUser(String firstName, String middleName, String lastName, String secondSurname, String email, String password
    ) {
        try {
            FullName fullName = new FullName(firstName, middleName, lastName, secondSurname);
            Email emailVo = new Email(email);

            var user = userService.registerUser(firstName, middleName, lastName, secondSurname, emailVo.getValue(), password);

            return "Usuario creado con ID: " + user.getId();
        } catch (Exception e) {
            return "Error creando usuario: " + e.getMessage();
        }
    }

    public Object[][] listUsers() {
        List<User> users = (List<User>) userService.listUsers();

        Object[][] data = new Object[users.size()][3];

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            data[i][0] = u.getId();
            data[i][1] = u.getFullName().getFirstName() + " " + u.getFullName().getLastName();
            data[i][2] = u.getEmail().getValue();
        }

        return data;
    }

}