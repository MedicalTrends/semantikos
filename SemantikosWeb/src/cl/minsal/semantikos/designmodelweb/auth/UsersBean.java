package cl.minsal.semantikos.designmodelweb.auth;

import cl.minsal.semantikos.kernel.auth.UserManager;
import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.User;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 14-07-2016.
 */

@ManagedBean(name = "users")
@ViewScoped
public class UsersBean {

    static private final Logger logger = LoggerFactory.getLogger(UsersBean.class);

    @EJB
    UserManager userManager;

    User selectedUser;

    List<User> allUsers;

    List<Profile> allProfiles;

    DualListModel<Profile> selectedUserProfileModel;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {

        this.selectedUser = userManager.getUser(selectedUser.getIdUser());

        //se debe actualizar la lista del picklist con los perfiles del usuario
        updateAvailableProfiles(selectedUser);

    }

    private void updateAvailableProfiles(User selectedUser) {
        selectedUserProfileModel = new DualListModel<Profile>();
        selectedUserProfileModel.setTarget(selectedUser.getProfiles());

        List<Profile> availableProfiles = new ArrayList<Profile>();


        availableProfiles.addAll(userManager.getAllProfiles());

        for (Profile p: selectedUser.getProfiles()){
            availableProfiles.remove(p);
        }

        selectedUserProfileModel.setSource(availableProfiles);
    }


    public List<User> getAllUsers(){

        if(allUsers==null)
            allUsers = userManager.getAllUsers();

        return allUsers;
    }


    public void newUser(){
        setSelectedUser( new User());
    }

    public void saveUser(){

        try {
            userManager.updateUser(selectedUser);
        }catch (Exception e){

        }
    }

    public DualListModel<Profile> getSelectedUserProfileModel(){

        return selectedUserProfileModel;
    }

    public void setSelectedUserProfileModel(DualListModel<Profile> selectedUserProfileModel) {
        this.selectedUserProfileModel = selectedUserProfileModel;
    }
}
