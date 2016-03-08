package com.laamella.amazingmazes;

import java.util.*;
import java.util.Map.Entry;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.State.ObservableObjectSetState;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix.UtilityWrapper.MatrixVisitor;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.solvers.Solver.*;

public class StateMatrixPrettyPrinter {
	private final List<Entry<Object, Character>> mappings = new ArrayList<Entry<Object, Character>>();
	private final char defaultCharacter;

	public StateMatrixPrettyPrinter() {
		map(ENTRANCE, '>');
		map(EXIT, 'E');
		map(SOLUTION, '.');
		map(PASSAGE, ' ');
		defaultCharacter = '#';
	}

	public StateMatrixPrettyPrinter(char defaultCharacter) {
		this.defaultCharacter = defaultCharacter;
	}

	public void map(final Object state, final Character character) {
		mappings.add(new AbstractMap.SimpleEntry<Object, Character>(state, character));
	}

	public void map(final Object state) {
		map(state, null);
	}

	public String getPrintableMaze(final StateMatrix stateMatrix) {
		final StringBuffer maze = new StringBuffer("\n");
		new Matrix.UtilityWrapper<ObservableObjectSetState>(stateMatrix).visitAllSquares(new MatrixVisitor<ObservableObjectSetState>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(final Position position, final ObservableObjectSetState states) {
				for (final Map.Entry<Object, Character> mapping : mappings) {
					if (states.hasState(mapping.getKey())) {
						if (mapping.getValue() == null) {
							maze.append(states.getState(mapping.getKey())%10);
						} else {
							maze.append(mapping.getValue());
						}
						return;

					}
				}
				maze.append(defaultCharacter);
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

}
