package in.cubestack.material.androidmaterial.json;

import java.util.List;


public class Wordlst implements Comparable<Wordlst>{

	private String word;
	
	private List<String> meanings;
	
	private List<String> sentences;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<String> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<String> meanings) {
		this.meanings = meanings;
	}

	public List<String> getSentences() {
		return sentences;
	}

	public void setSentences(List<String> sentences) {
		this.sentences = sentences;
	}
	

	public int compareTo(Wordlst o) {
		return this.getWord().compareTo(o.getWord());
	}
}
