package com.fireplace.market.fads.bll;

import java.io.Serializable;
import java.util.List;

import com.fireplace.market.fads.dal.RepoRepository;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Repo implements Serializable, Comparable<Repo> {

	private static final long serialVersionUID = -4127230968140139604L;

	public Repo() {
	}

	public Repo(Integer id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String url;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Repo) && ((Repo) o).getId() == this.getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public int compareTo(Repo another) {
		return name.compareTo(another.name);
	}

	public Boolean exists() {
		return new RepoRepository().repoExists(this);
	}

	public Repo save() {
		return new RepoRepository().saveRepo(this);
	}

	public Repo update() {
		return new RepoRepository().updateRepo(this);
	}

	public Repo saveOrUpdate() {
		return new RepoRepository().saveOrUpdate(this);
	}

	public Repo refresh() {
		return new RepoRepository().refreshRepo(this);
	}

	public Boolean delete() {
		return new RepoRepository().deleteRepo(this);
	}

	public static List<Repo> getAll() {
		return new RepoRepository().getAllRepositories();
	}

	public static Repo getById(Integer id) {
		return new RepoRepository().getByIdentifier(id);
	}

	public static List<Repo> getByName(String name) {
		return new RepoRepository().getByName(name);
	}

}
