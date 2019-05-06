package maze.parent;

public abstract interface Grid {
	public abstract int getRows();
	public abstract int getCols();
	public abstract Cell getCellAt(int x, int y);
}
