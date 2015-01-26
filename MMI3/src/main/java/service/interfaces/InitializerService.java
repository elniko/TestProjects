package service.interfaces;

import dao.interfaces.GenericDao;
import entity.Entity;

/**
 * Created by Nick on 23/01/2015.
 */
public interface InitializerService {

    public void initRoles(Object[] roles);

    public void initResourceTypes(Object[] resTypes);

    public void initProcessStatuses(Object[] procStatuses);

    public void initProcessTypes(Object[] procTypes);

    public void initPropertyTypes(Object[] propTypes);

    public void clearRole() ;

    public void clearProcessType();

    public void clearProcessStatus();

    public void clearResourceType();

    public void clearPropertyType();

    public void clearUsers();

}
