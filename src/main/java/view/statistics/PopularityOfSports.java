package view.statistics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PopularityOfSports extends JPanel {

    public PopularityOfSports(Presenter presenter) {
        setLayout(new BorderLayout());
        HashMap<String, Integer> sports = null;
        try {
            sports = presenter.getSportsmenOfSportsCount();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        DefaultPieDataset dataset = new DefaultPieDataset( );
        sports.forEach(dataset::setValue);
        JFreeChart chart = ChartFactory.createPieChart(
                "Соотношение спортсменов по видам спорта", dataset, true, true, true);
        add(new ChartPanel(chart), BorderLayout.CENTER);
    }

}
