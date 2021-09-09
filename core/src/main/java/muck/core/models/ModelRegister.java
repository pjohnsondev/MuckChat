package muck.core.models;

import muck.core.models.AbstractModel.Model;
import muck.core.models.models.User;

import java.sql.SQLException;

/**
 * Register your models here so that a table is created for them when the program is built.
 */
public class ModelRegister {
    private Model[] models = {
        new User(),
    };

    public void makemigrations() {
        for (Model model : models) {
            try {
                model.createTable();
            } catch (SQLException exception) {
                System.out.println(exception.getSQLState());
            }
        }
    }
}
