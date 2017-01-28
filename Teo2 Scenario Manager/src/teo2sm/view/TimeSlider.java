package teo2sm.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import teo2sm.Constants;

public class TimeSlider extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private static final int INTER_SCENE_SPACING_TIME = 10;
	private int maxTime;
	private int minTime;
	private JSlider slider;
	
	public TimeSlider(ArrayList<Integer> times) {
		minTime = 0;
		this.maxTime = times.get(0) + (times.size() - 2)*10;
		slider = new JSlider(JSlider.HORIZONTAL, minTime, this.maxTime, minTime);
		slider.setPreferredSize(new Dimension(Constants.DEFAULT_WINDOW_WIDTH - 16, 64));
		add(slider);
		setLabels(times);
		slider.setMajorTickSpacing(30);
		slider.setPaintTicks(true);
	}

	private void setLabels(ArrayList<Integer> times) {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		times.remove(0);
		int pos = 0, scene = 1;
		for(int t : times) {
			JLabel label = new JLabel(new ImageIcon("res/images/play_small.png"), JLabel.CENTER);
			labelTable.put(new Integer(pos), label);
			pos = pos + t;
			label = new JLabel(new ImageIcon("res/images/stop_small.png"), JLabel.CENTER);
			labelTable.put(new Integer(pos), label);
			pos = pos + INTER_SCENE_SPACING_TIME;
			scene = scene + 1;
		}
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
	}

	public void setValue(int value, int sceneNumber) {
		slider.setValue(value + (sceneNumber - 1)*INTER_SCENE_SPACING_TIME);
		updateUI();
	}
}
