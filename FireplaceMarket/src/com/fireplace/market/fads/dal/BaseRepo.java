package com.fireplace.market.fads.dal;

import java.sql.SQLException;
import java.util.List;

import com.fireplace.market.fads.FireplaceApplication;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;

/**
 * This class is the Startup Base Repository that all other repositories should
 * extend.
 */
abstract class BaseRepo {

	protected <T> Dao<T, Integer> getDao(Class<T> objectClass) {
		try {
			return FireplaceApplication.mDbHelper.getDao(objectClass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> int count(Class<T> objectClass) {
		int count = 0;
		try {
			count = (int) getDao(objectClass).countOf();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	protected <T> T refreshObject(T object, Class<T> objectClass) {
		try {
			getDao(objectClass).refresh(object);
			return object;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> Boolean existsById(int ID, Class<T> objectClass) {
		try {
			return getDao(objectClass).idExists(ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	protected <T> T save(T object, Class<T> objectClass) {
		try {
			getDao(objectClass).create(object);
			return object;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method will save or update the object in the data store. This will
	 * decide which action to take, based on the existence of the data objects
	 * ID field. You must have an ID field set that uniquely identifies your
	 * data object, to use this method.
	 * 
	 * @param object
	 *            The object to be persisted.
	 * @param objectClass
	 *            The objects class.
	 * @return true, if the object was persisted.
	 */
	protected <T> Boolean saveOrUpdateById(T object, Class<T> objectClass) {
		try {
			return getDao(objectClass).createOrUpdate(object)
					.getNumLinesChanged() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	protected <T> Boolean saveCollection(List<T> objects, Class<T> objectClass) {
		int createdObjects = 0;
		for (T object : objects) {
			save(object, objectClass);
			createdObjects++;
		}
		return objects.size() == createdObjects;
	}

	/**
	 * This method will save or update the objects in the data store. This will
	 * decide which action to take, based on the existence of the data objects'
	 * ID field. You must have an ID field set that uniquely identifies your
	 * data objects, to use this method.
	 * 
	 * @param object
	 *            The object to be persisted.
	 * @param objectClass
	 *            The objects class.
	 * @return true, if the objects were persisted.
	 */
	protected <T> Boolean saveOrUpdateCollectionByIds(List<T> objects,
			Class<T> objectClass) {
		int persistedItems = 0;
		for (T object : objects) {
			persistedItems = (saveOrUpdateById(object, objectClass)) ? persistedItems++
					: persistedItems;
		}
		return persistedItems == objects.size();
	}

	protected <T> List<T> getEntireCollection(Class<T> objectClass) {
		try {
			return getDao(objectClass).queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> List<T> getCollectionByMatchingValue(String columnName,
			Object columnValue, Class<T> objectClass) {
		try {
			return getDao(objectClass).queryForEq(columnName, columnValue);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> List<T> getCollectionByMatchingValuesInSingleColumn(
			String columnName, List<?> columnValues, Class<T> objectClass) {
		try {
			return getDao(objectClass).queryBuilder().where()
					.in(columnName, columnValues).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> List<T> getCollectionByMatchingProperties(T object,
			Class<T> objectClass) {
		try {
			return getDao(objectClass).queryForMatchingArgs(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> T update(T object, Class<T> objectClass) {
		try {
			getDao(objectClass).update(object);
			return object;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> Boolean updateCollection(List<T> objects, Class<T> objectClass) {
		int updatedObjects = 0;
		for (T object : objects) {
			update(object, objectClass);
			updatedObjects++;
		}
		return objects.size() == updatedObjects;
	}

	protected <T> Boolean delete(T object, Class<T> objectClass) {
		try {
			return getDao(objectClass).delete(object) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> Boolean deleteEntireCollection(Class<T> objectClass) {
		try {
			Dao<T, Integer> dao = getDao(objectClass);
			return dao.deleteBuilder().delete() == (int) dao.countOf();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected <T> Boolean deleteItemsFromCollectionByMatchingValue(
			String columnName, Object columnValue, Class<T> objectClass) {
		try {
			Dao<T, Integer> dao = getDao(objectClass);
			return dao.delete((PreparedDelete<T>) dao.deleteBuilder().where()
					.eq(columnName, columnValue).prepare()) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
