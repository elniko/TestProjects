package test;

import dao.interfaces.GenericDao;
import entity.*;
import exceptions.NoRoleException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.interfaces.*;

/**
 * Created by Nick on 21/01/2015.
 */
public class Init {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);
    GenericDaoService<UserRoleEntity> roleService =  ctx.getBean(RoleService.class);
    GenericDaoService<ResourceTypeEntity> resourceTypeService =  ctx.getBean(ResourceTypeService.class);
    GenericDaoService<ProcessTypeEntity> procTypeService = ctx.getBean(ProcessTypeService.class);
    GenericDaoService<ProcessStatusEntity> procStatusService = ctx.getBean(ProcessStatusService.class);
    UserService userService = ctx.getBean(UserService.class);

    enum Roles {ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_USER, ROLE_GUEST}
    enum Files {TAXO, INPUT_XCT, INPUT_XRT, INPUT_XVT}
    enum Process {XCT, XRT, XVT, MERGE, SPLIT, BULK}
    enum ProcessStatus {SHEDULED, RUNNING, STOPPED, ERROR, FINISHED}



    public void addUser(String name, String pass, String mail ,String role) throws NoRoleException {
        userService.addUserWithRole(name, pass, mail, role);
    }



    public void initTypes() {

        Roles[] roles = Roles.values();
        for(int i =0; i< roles.length; i++) {
            UserRoleEntity role = new UserRoleEntity();
            role.setRole(roles[i].toString());
            roleService.saveEntity(role);
        }

        Files[] files = Files.values();
        for(int i =0; i< files.length; i++) {
            ResourceTypeEntity resource = new ResourceTypeEntity();
            resource.setName(files[i].toString());
            resourceTypeService.saveEntity(resource);
        }

        Process[] proc = Process.values();
        for(int i =0; i< proc.length; i++) {
            ProcessTypeEntity resource = new ProcessTypeEntity();
            resource.setName(proc[i].toString());
            procTypeService.saveEntity(resource);
        }

        ProcessStatus[] procStatus = ProcessStatus.values();
        for(int i =0; i< procStatus.length; i++) {
            ProcessStatusEntity ps = new ProcessStatusEntity();
            ps.setName(proc[i].toString());
            procStatusService.saveEntity(ps);
        }

    }








}
