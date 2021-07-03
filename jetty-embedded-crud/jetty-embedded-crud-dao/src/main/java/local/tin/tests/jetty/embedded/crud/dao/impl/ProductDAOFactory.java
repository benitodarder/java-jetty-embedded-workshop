package local.tin.tests.jetty.embedded.crud.dao.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import local.tin.tests.jetty.embedded.core.dao.impl.AbstractDAOFactory;
import local.tin.tests.jetty.embedded.core.models.domain.exceptions.DAOException;
import local.tin.tests.jetty.embedded.crud.dao.ProductDAOConfiguration;
import org.apache.log4j.Logger;

public class ProductDAOFactory extends AbstractDAOFactory {

    public static final String UNEXPECTED_DAO_EXCEPTION_CREATING_ENTITY_M = "Unexpected DAOException creating entity manager factory: ";    
    private static final Object LOCK = new Object();
    private static final Logger LOGGER = Logger.getLogger(ProductDAOFactory.class);
    private static volatile EntityManagerFactory entityManagerFactory;

    private ProductDAOFactory() {
    }

    private static class Loader {

        protected static final ProductDAOFactory INSTANCE = new ProductDAOFactory();

        private Loader() {
        }
    }

    public static ProductDAOFactory getInstance() {
        ProductDAOFactory receiptDAOFactory = Loader.INSTANCE;
        EntityManagerFactory emf = entityManagerFactory;
        if (emf == null) {
            synchronized (LOCK) {
                emf = entityManagerFactory;
                if (emf == null) {
                    try {
                        entityManagerFactory = Persistence.createEntityManagerFactory(ProductDAOConfiguration.getInstance().getPersistenceUnit());
                    } catch (DAOException ex) {
                        LOGGER.error(UNEXPECTED_DAO_EXCEPTION_CREATING_ENTITY_M + ex.getLocalizedMessage());
                        LOGGER.debug(UNEXPECTED_DAO_EXCEPTION_CREATING_ENTITY_M + ex.getLocalizedMessage(), ex);
                    }
                }
            }
        }
        return receiptDAOFactory;

    }

    @Override
    protected EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    protected String getDAOBasePackage() throws DAOException {
        return ProductDAOConfiguration.getInstance().getDAOBasePackage();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

}
