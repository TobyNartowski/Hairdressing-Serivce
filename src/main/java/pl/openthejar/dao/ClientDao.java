package pl.openthejar.dao;

import pl.openthejar.model.Client;

import javax.persistence.TypedQuery;

public class ClientDao extends EntityDao<Client> {

    public ClientDao() {
        super(Client.class);
    }

    public Client authenticateClient(String login, String hash) {
        // TODO: Fix here, something is broken ( ͡° ʖ̯ ͡°)
        final String query = "SELECT c FROM " + tableName + " c WHERE c.login = :login AND c.hash = :hash";
        TypedQuery<Client> authQuery = entityManager.createQuery(query, Client.class);
        authQuery.setParameter("login", login);
        authQuery.setParameter("hash", hash);
        return authQuery.getSingleResult();
    }
}
