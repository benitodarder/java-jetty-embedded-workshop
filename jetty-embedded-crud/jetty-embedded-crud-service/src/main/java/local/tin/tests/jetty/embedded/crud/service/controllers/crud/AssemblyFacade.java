package local.tin.tests.jetty.embedded.crud.service.controllers.crud;

import javax.ws.rs.Path;
import local.tin.tests.jetty.embedded.core.models.domain.exceptions.ServiceException;
import local.tin.tests.jetty.embedded.crud.models.domain.product.Assembly;
import local.tin.tests.jetty.embedded.crud.service.crud.impl.AbstractCRUDService;
import local.tin.tests.jetty.embedded.crud.service.crud.impl.AssemblyCRUD;
import local.tin.tests.jetty.embedded.crud.service.crud.impl.ProductCRUDServiceFactory;


/**
 *
 * @author benitodarder
 */
@Path("/crud/assembly")
public class AssemblyFacade extends AbstractCRUDResource<Assembly> {

    private final AssemblyCRUD crud;

    public AssemblyFacade() throws ServiceException {
        this.crud = (AssemblyCRUD) ProductCRUDServiceFactory.getInstance().getCRUD(Assembly.class);
    }
   
    @Override
    protected Class<Assembly> getCRUDClass() {
        return Assembly.class;
    }

    @Override
    protected AbstractCRUDService getCRUDService() {
        return crud;
    }
    
}
