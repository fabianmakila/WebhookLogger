package fi.fabianadrian.webhookchatlogger.common.dependency;

import java.util.ArrayList;
import java.util.List;

public class DependencyManager {
	private final List<Dependency> presentDependenciesList = new ArrayList<>();

	public boolean isPresent(Dependency dependency) {
		return this.presentDependenciesList.contains(dependency);
	}

	public void markAsPresent(Dependency dependency) {
		this.presentDependenciesList.add(dependency);
	}
}
