package shuffle;

public class Shuffle {

	private ShuffleUI ui;
	private ShuffleLogic logic;
	public static void main(String[] args) {
		Shuffle s = new Shuffle();
		s.work();
	}

	private void work() {
		ui = new ShuffleUI();
		logic = new ShuffleLogic();
		ui.setLogic(logic);
		logic.setUI(ui);
		
		ui.init();
		ui.run();
		logic.randomize();
	}

}
