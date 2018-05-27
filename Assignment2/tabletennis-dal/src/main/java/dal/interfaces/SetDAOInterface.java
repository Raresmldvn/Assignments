package dal.interfaces;

import dal.model.Set;

public interface SetDAOInterface {

	public Set findById(int id);
	public int insertSet(Set toBeInserted);
	public void updateSet(Set toBeUpdated);
	public void deleteSet(int id);
}
