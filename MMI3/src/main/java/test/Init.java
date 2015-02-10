package test;

import entity.*;
import exceptions.RoleNotExistException;
import exceptions.UserAlreadyExistException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.interfaces.*;

/**
 * Created by Nick on 21/01/2015.
 */
public class Init {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);
    InitializerService initService = ctx.getBean(InitializerService.class);
    UserService userService = ctx.getBean(UserService.class);

    enum Roles {ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_USER, ROLE_GUEST}
    enum Files {TAXO, INPUT_XCT, INPUT_XRT, INPUT_XVT}
    enum Process {XCT, XRT, XVT, MERGE, SPLIT, BULK}
    enum ProcessStatus {SHEDULED, RUNNING, STOPPED, ERROR, FINISHED}
    enum PropertTypes {STRING, INTEGER, BOOLEAN, DATE}

    public void addUser(String name, String pass, String mail ,String role) throws RoleNotExistException, UserAlreadyExistException {
        userService.addUserWithRole(name, pass, mail, role);
    }



    public void initTypes(boolean clearAll) {

        if(clearAll) {
            initService.clearRole();
            initService.clearProcessStatus();
            initService.clearProcessType();
            initService.clearPropertyType();
            //initService.clearResourceType();
        }
        initService.initRoles(Roles.values());
        initService.initProcessStatuses(ProcessStatus.values());
        initService.initProcessTypes(Process.values());
        //initService.initResourceTypes(Files.values());
        initService.initPropertyTypes(PropertTypes.values());


    }








}
