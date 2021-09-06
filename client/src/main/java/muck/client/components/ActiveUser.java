package muck.client.components;
import muck.client.MuckClient;
import muck.core.interfaces.IActiveUser;
import muck.core.structures.UserStructure;

public class ActiveUser implements IActiveUser {

    private static UserStructure userStructure;

    private static ActiveUser instance = null;

    private ActiveUser() {}

    public static ActiveUser getInstance() {
        if (ActiveUser.instance == null) {
            ActiveUser.instance = new ActiveUser();
        }
        return ActiveUser.instance;
    }

    public UserStructure getUser() {
        return ActiveUser.userStructure;
    }

    public void setUserStructure(UserStructure userStructure) {
        ActiveUser.userStructure = userStructure;
    }
}
