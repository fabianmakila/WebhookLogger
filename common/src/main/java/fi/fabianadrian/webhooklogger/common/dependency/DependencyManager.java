package fi.fabianadrian.webhooklogger.common.dependency;

import java.util.ArrayList;
import java.util.List;

public final class DependencyManager {
	private final List<Dependency> presentDependenciesList = new ArrayList<>();

	public boolean isPresent(Dependency dependency) {
		return presentDependenciesList.contains(dependency);
	}

	public void markAsPresent(Dependency dependency) {
		presentDependenciesList.add(dependency);
	}
}
