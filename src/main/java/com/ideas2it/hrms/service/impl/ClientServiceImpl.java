package com.ideas2it.hrms.service.impl;

import java.util.List;

import com.ideas2it.hrms.dao.ClientDao;
import com.ideas2it.hrms.dao.impl.ClientDaoImpl;
import com.ideas2it.hrms.exception.AppException;
import com.ideas2it.hrms.model.Client;
import com.ideas2it.hrms.service.ClientService;

public class ClientServiceImpl implements ClientService {
    
    private ClientDao clientDao = new ClientDaoImpl();
    
    /** {@inheritDoc}*/
    public Boolean createClient(Client client) throws AppException {
        return clientDao.createClient(client);

    }
    
    /** {@inheritDoc}*/
    public Boolean updateClient(Client client) throws AppException {
        return clientDao.updateClient(client);

    }
    
    /** {@inheritDoc}*/
    public List<Client> displayClients() throws AppException {
        return clientDao.retrieveClients();
    }
    
    /** {@inheritDoc}*/
    public Boolean deleteClient(Integer id) throws AppException {
        return clientDao.deleteClient(id);
    }
    
    public Boolean isClientExist(String email) throws AppException {
        return (null == clientDao.searchClient(email));
    }
}