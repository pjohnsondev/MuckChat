package muck.server.models;
import java.sql.SQLException;

import muck.server.models.AbstractModel.Model;
import muck.server.models.models.*;

/**
 * Register your models here so that a table is created for them when the program is built.
 */
public class ModelRegister {
    private final Model[] models = {
        new UserModel(),
    };

    public void makeMigrations() {
        for (Model model : models) {
            try {
                model.createTable();
            } catch (SQLException exception) {
                System.out.println(exception.getSQLState());
            }
        }
    }
}
