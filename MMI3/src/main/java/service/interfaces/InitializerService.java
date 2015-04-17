package service.interfaces;

import dao.interfaces.GenericDao;
import entity.Entity;
import exceptions.EntityNotExistsException;
import exceptions.RoleNotExistException;
import exceptions.UserAlreadyExistException;

/**
 * Created by Nick on 23/01/2015.
 */
public interface InitializerService {

    public void initRoles(Object[] roles);

    public void initResourceTypes(Object[] resTypes);

    public void initProcessStatuses(Object[] procStatuses);

    public void initProcessTypes(Object[] procTypes);

    public void initPropertyTypes(Object[] propTypes);

    public void clearRole() throws EntityNotExistsException;

    public void clearProcessType() throws EntityNotExistsException;

    public void clearProcessStatus() throws EntityNotExistsException;

    public void clearResourceType() throws EntityNotExistsException;

    public void clearPropertyType() throws EntityNotExistsException;

    public void clearUsers();

    public void initProperties(Object[] props);

    public void initTypes(boolean clearAll) throws EntityNotExistsException, RoleNotExistException, UserAlreadyExistException;

}
