package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class PoemDAO implements IDAO<Poem> {

	private final EntityManagerFactory emf;

	public PoemDAO() {
		this.emf = HibernateConfig.getEntityManagerFactory("poem");
	}

	@Override
	public Poem create(Poem poem) {
		try (EntityManager entityManager = emf.createEntityManager()) {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				entityManager.persist(poem);
				transaction.commit();
				return poem;
			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	@Override
	public Poem read(Object id) {
		try (EntityManager entityManager = emf.createEntityManager()) {
			return entityManager.find(Poem.class, id);
		}
	}

	@Override
	public Poem update(Poem poem) {
		try (EntityManager entityManager = emf.createEntityManager()) {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				Poem updatedPoem = entityManager.merge(poem);
				transaction.commit();
				return updatedPoem;
			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	@Override
	public boolean delete(Object id) {
		try (EntityManager entityManager = emf.createEntityManager()) {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				Poem poem = entityManager.find(Poem.class, id);
				if (poem != null) {
					entityManager.remove(poem);
					transaction.commit();
					return true;
				} else {
					transaction.rollback();
					return false;
				}
			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	@Override
	public List<Poem> getAll() {
		try (EntityManager entityManager = emf.createEntityManager()) {
			return entityManager.createQuery("SELECT p FROM Poem p", Poem.class).getResultList();
		}
	}

	@Override
	public List<Poem> getById (Poem poem) {
		Long id = poem.getId();
		try (EntityManager entityManager = emf.createEntityManager()) {
			return entityManager.createQuery("SELECT p FROM Poem p WHERE p.id = :id", Poem.class)
					.setParameter("id", poem.getId())
					.getResultList();
		}
	}
}
