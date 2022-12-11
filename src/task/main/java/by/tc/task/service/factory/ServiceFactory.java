package by.tc.task.service.factory;

import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.service.ArchiveService;
import by.tc.task.service.exception.ServiceException;
import by.tc.task.service.impl.ArchiveServiceImpl;

import java.util.List;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final ArchiveService archiveService = new ArchiveServiceImpl();
    private ServiceFactory() {}
    public ArchiveService getArchiveService() {
        return archiveService;
    }
    public static ServiceFactory getInstance() { return instance; }

}
