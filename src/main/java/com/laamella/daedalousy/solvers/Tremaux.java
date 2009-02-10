package com.laamella.daedalousy.solvers;

/**
 * This Maze solving method is designed to be able to be used by a human inside
 * of the Maze. It's similar to the recursive backtracker and will find a
 * solution for all Mazes: As you walk down a passage, draw a line behind you to
 * mark your path. When you hit a dead end turn around and go back the way you
 * came. When you encounter a junction you haven't visited before, pick a new
 * passage at random. If you're walking down a new passage and encounter a
 * junction you have visited before, treat it like a dead end and go back the way
 * you came. (That last step is the key which prevents you from going around in
 * circles or missing passages in braid Mazes.) If walking down a passage you
 * have visited before (i.e. marked once) and you encounter a junction, take any
 * new passage if one is available, otherwise take an old passage (i.e. one
 * you've marked once). All passages will either be empty, meaning you haven't
 * visited it yet, marked once, meaning you've gone down it exactly once, or
 * marked twice, meaning you've gone down it and were forced to backtrack in the
 * opposite direction. When you finally reach the solution, paths marked exactly
 * once will indicate a direct way back to the start. If the Maze has no
 * solution, you'll find yourself back at the start with all passages marked
 * twice.
 */
public class Tremaux {

}
