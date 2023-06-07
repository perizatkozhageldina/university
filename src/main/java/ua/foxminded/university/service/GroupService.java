package ua.foxminded.university.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.GroupJdbcRepository;

import javax.validation.Valid;

@Service
@Validated
public class GroupService {
    private GroupJdbcRepository dao;

    @Autowired
    public GroupService(GroupJdbcRepository dao) {
        this.dao = dao;
    }

    public Group save(@Valid Group group) throws ServiceException {
        return dao.save(group);
    }

    public void deleteById(long id) throws ServiceException {
        dao.deleteById(id);
    }

    public Group getById(long id) throws ServiceException {
        Optional<Group> groupOptional = dao.findById(id);
        return groupOptional.orElseThrow(() -> new ServiceException("Group not found with ID:" + id));
    }

    public List<Group> getAll() throws ServiceException {
        return dao.findAll();
    }
}