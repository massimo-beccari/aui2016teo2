package teo2sm.view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import teo2sm.Constants;

public class TimeSlider extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private int maxTime;
	private int minTime;
	private JSlider slider;
	
	public TimeSlider(int maxTime) {
		minTime = 0;
		this.maxTime = maxTime;
		slider = new JSlider(JSlider.HORIZONTAL, minTime, this.maxTime, minTime);
		slider.setPreferredSize(new Dimension(Constants.DEFAULT_WINDOW_WIDTH - 16, 32));
		add(slider);
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
	}

}
