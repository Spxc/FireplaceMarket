package com.fireplace.market.fads.bll;

import java.io.Serializable;
import java.util.List;

import com.fireplace.market.fads.dal.AppRepository;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class App implements Serializable, Comparable<App> {

	private static final long serialVersionUID = -7288122970652260255L;

	public App() {
	}

	public App(String label, String path) {
		this.label = label;
		this.path = path;
	}

	public App(Integer id, String label, String path, Integer ptype,
			String icon, String description, String devel, Integer status) {
		this.id = id;
		this.label = label;
		this.path = path;
		this.ptype = ptype;
		this.icon = icon;
		this.description = description;
		this.devel = devel;
		this.status = status;
	}

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private String label;
	@DatabaseField(canBeNull = false)
	private String path;
	@DatabaseField(canBeNull = false)
	private Integer ptype;
	@DatabaseField(canBeNull = false)
	private String icon;
	@DatabaseField
	private String description;
	@DatabaseField
	private String devel;
	@DatabaseField(canBeNull = false)
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDevel() {
		return devel;
	}

	public void setDevel(String devel) {
		this.devel = devel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static String toResourceName() {
		return "getdata.php";
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof App) && ((App) o).getId() == this.getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public int compareTo(App another) {
		return label.compareTo(another.label);
	}

	public Boolean exists() {
		return new AppRepository().applicationExists(this);
	}

	public App saveOrUpdate() {
		return new AppRepository().saveOrUpdate(this);
	}

	public static Boolean saveOrUpdateAll(List<App> appList) {
		return new AppRepository().saveOrUpdateApps(appList);
	}

	public App refresh() {
		return new AppRepository().refreshApp(this);
	}

	public Boolean delete() {
		return new AppRepository().deleteApp(this);
	}

	public static List<App> getAll() {
		return new AppRepository().getAllApps();
	}

	public static App getById(Integer id) {
		return new AppRepository().getByIdentifier(id);
	}

	public static List<App> getByPtype(Integer ptype) {
		return new AppRepository().getByPtype(ptype);
	}

	public static List<App> getByDeveloper(String developer) {
		return new AppRepository().getByDeveloper(developer);
	}

	public static List<App> getByStatus(Integer status) {
		return new AppRepository().getByStatus(status);
	}

}