
public class countingArray {
	private String word;
	private int count;
	private int[][] medMatrix;
	
	public countingArray(String word, int count, int[][] medMatrix) {
		super();
		this.word = word;
		this.count = count;
		this.medMatrix = medMatrix;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int[][] getMedMatrix() {
		return medMatrix;
	}
	public void setMedMatrix(int[][] medMatrix) {
		this.medMatrix = medMatrix;
	}
	
	
}
