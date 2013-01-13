package com.fireplace.market.fads.dal;

import java.sql.SQLException;
import java.util.List;

import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.bll.Repo;
import com.j256.ormlite.dao.Dao;

public class RepoRepository extends BaseRepo {

	public static final String ID_FIELD_NAME = "id";
	public static final String NAME_FIELD_NAME = "name";
	public static final String URL_FIELD_NAME = "url";

	@Override
	protected <T> Dao<T, Integer> getDao(Class<T> objectClass) {
		try {
			return FireplaceApplication.mDbHelper.getDao(Repo.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean repoExists(Repo repository) {
		return existsById(repository.getId(), Repo.class);
	}

	public Repo saveRepo(Repo repository) {
		return save(repository, Repo.class);
	}

	public Boolean saveRepositories(List<Repo> repoList) {
		int count = 0;
		for (Repo repository : repoList) {
			save(repository, Repo.class);
			count++;
		}
		return count == repoList.size();
	}

	public Repo saveOrUpdate(Repo repository) {
		if (!repoExists(repository)) {
			return saveRepo(repository);
		} else {
			return updateRepo(repository);
		}
	}

	public Boolean saveOrUpdateRepo(List<Repo> repoList) {
		int count = 0;
		for (Repo repository : repoList) {
			saveOrUpdate(repository);
			count++;
		}
		return count == repoList.size();
	}

	public Repo updateRepo(Repo repository) {
		update(repository, Repo.class);
		return repository;
	}

	public Boolean updateRepositories(List<Repo> repoList) {
		int count = 0;
		for (Repo repository : repoList) {
			update(repository, Repo.class);
			count++;
		}
		return count == repoList.size();
	}

	public Repo refreshRepo(Repo repository) {
		return refreshObject(repository, Repo.class);
	}

	public List<Repo> getAllRepositories() {
		return getEntireCollection(Repo.class);
	}

	public Repo getByIdentifier(Integer id) {
		List<Repo> repoList = getCollectionByMatchingValue(ID_FIELD_NAME, id,
				Repo.class);
		return (repoList != null && repoList.size() > 0) ? repoList.get(0)
				: null;
	}

	public List<Repo> getByName(String name) {
		List<Repo> repoList = getCollectionByMatchingValue(NAME_FIELD_NAME,
				name, Repo.class);
		return (repoList != null && repoList.size() > 0) ? repoList : null;
	}

	public Boolean deleteRepo(Repo repository) {
		return delete(repository, Repo.class);
	}

}
