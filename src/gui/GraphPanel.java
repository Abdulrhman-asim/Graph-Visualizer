package gui;

import javax.swing.*;
import org.apache.commons.collections15.Transformer;
import algorithms.MyGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import java.awt.*;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int windowH, windowW;
	private static MyGraph graph = null;
	VisualizationViewer<String, String> vv;

	public GraphPanel(MyGraph a) {
		graph = a;
		updateView();
	}

	public void changeColor(final MyGraph tree) {
		
		Transformer<String, Paint> edgePaint = new Transformer<String, Paint>() {

			public Paint transform(String s) { // s represents the edge
				if(tree.containsEdge(s))
					return Color.BLUE;
				else
					return Color.BLACK;

			}
		};
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
	}

	public void updateView() {
		this.setLayout(null);
		windowW = 580 - 240;
		windowH = 460 - 20;
		setBounds(230, 10, windowW, windowH);
		setBackground(Color.darkGray);
		initialize();
		vv.setBounds(10, 10, windowW - 20, windowH - 20);
		repaint();
	}

	public static MyGraph getGraph() {
		return graph;
	}

	public void setGraph(MyGraph graph) {
		GraphPanel.graph = graph;
		updateView();
	}

	public void initialize() {

		vv = new VisualizationViewer<String, String>(
				new FRLayout<String, String>(graph), new Dimension(280, 280));

		Transformer<String, String> transformer = new Transformer<String, String>() {
			public String transform(String arg0) {
				return arg0;
			}
		};
		vv.getRenderContext().setVertexLabelTransformer(transformer);
		transformer = new Transformer<String, String>() {
			public String transform(String arg0) {
				return arg0;
			}
		};
		vv.getRenderContext().setEdgeLabelTransformer(transformer);
		final DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<String, Number>();
		vv.setGraphMouse(graphMouse);
		graphMouse.setMode(DefaultModalGraphMouse.Mode.PICKING);
		add(vv);
	}

	void addNode(String name) {
		graph.addVertex(name);
		repaint();

	}

	void deleteNode(String name) {
		graph.removeVertex(name);
		repaint();
	}

	void deleteEdge(String from, String to) {
		graph.removeEdge(graph.findEdge(from, to));
		repaint();
	}

	void addEdge(String from, String to, String cost) {
		graph.addEdge(from, to, cost);
		repaint();
	}

	void clear() {
		graph.removeAll();
		repaint();

	}
}
