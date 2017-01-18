package teo2sm.view.wizard;

import java.util.function.Consumer;

import teo2sm.model.ScenarioData;

public class NewScenarioWizardCallback implements Consumer<ScenarioData> {
	ScenarioData scenario;

	@Override
	public void accept(ScenarioData s) {
		scenario = s;
	}
	
	public ScenarioData getScenario() {
		return scenario;
	}
}
