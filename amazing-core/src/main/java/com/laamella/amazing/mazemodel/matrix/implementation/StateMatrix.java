package com.laamella.amazing.mazemodel.matrix.implementation;

import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.ENTRANCE;
import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.EXIT;
import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.OPEN;

import java.util.HashSet;
import java.util.Set;

import com.laamella.amazing.generators.MazeGenerator.GeneratorState;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.matrix.Matrix;
import com.laamella.amazing.mazemodel.matrix.Matrix.UtilityWrapper.MatrixVisitor;
import static com.laamella.amazing.solvers.Solver.SolutionState.*;

public class StateMatrix extends ListMatrix<Set<Object>> {
	public StateMatrix(Size size) {
		super(size);
	}

	@Override
	protected Set<Object> newItem() {
		return new HashSet<Object>(4);
	}

	public String getPrintableMaze() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<Set<Object>>(this).visitAllSquares(new MatrixVisitor<Set<Object>>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(final Position position, final Set<Object> states) {
				if (states.contains(ENTRANCE)) {
					maze.append(">");
				} else if (states.contains(EXIT)) {
					maze.append("E");
				} else if (states.contains(SOLUTION)) {
					maze.append('.');
				} else if (states.contains(GeneratorState.VISITED)) {
					maze.append(' ');
				} else if (states.contains(OPEN)) {
					maze.append(' ');
				} else {
					maze.append('#');
				}
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

	@Override
	public String toString() {
		return getPrintableMaze();
	}
}
