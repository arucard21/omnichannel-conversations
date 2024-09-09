package omnichannel_conversations;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RequestScoped
public class EntityManagerProducer {
	@PersistenceContext
	private EntityManager entityManager;

	@Produces
	public EntityManager entityManager() {
		return entityManager;
	}
}
