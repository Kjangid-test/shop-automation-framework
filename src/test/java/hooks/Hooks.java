package hooks;

import io.cucumber.java.AfterStep;

public class Hooks {
	@AfterStep
	public void waitAftetStep() {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
