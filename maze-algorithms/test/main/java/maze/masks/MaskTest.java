package maze.masks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class MaskTest {
	
	public static Mask fileMask;
	
	@BeforeClass
	public static void prepare() throws URISyntaxException {
		fileMask = new TextMask("FileMask.txt");
	}

	@Ignore("changed filemask")
	@Test
	public void testMaskCreation() throws URISyntaxException {
		assertThat(fileMask.isCellMasked(0, 0), equalTo(true));
		assertThat(fileMask.isCellMasked(0, 10), equalTo(false));
	}
	
//	@Test
//	public void testMazeCreation() {
//		Maze maskedMaze = new MaskedMaze(fileMask);
//		maskedMaze.
//	}
}
