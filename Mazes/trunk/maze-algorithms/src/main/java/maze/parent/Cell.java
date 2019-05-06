package maze.parent;

public class Cell {

	public int row, col;
	public Cell north, south, east, west;
	private boolean visited;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setVisited() {
		visited = true;
	}
	
	public boolean hasBeenVisited() {
		return visited;
	}
	
	public boolean isDeadEnd() {
		int connections = 0;
		if (north != null)
			connections++;
		if (south != null)
			connections++;
		if (east != null)
			connections++;
		if (west != null)
			connections++;
		if (connections == 1)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + (visited ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (visited != other.visited)
			return false;
		return true;
	}
}
