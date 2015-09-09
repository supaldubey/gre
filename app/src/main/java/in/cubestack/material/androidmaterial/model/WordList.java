package in.cubestack.material.androidmaterial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.cubestack.android.lib.storm.CascadeTypes;
import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Relation;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by arunk on 7/11/2015.
 */

@Table(name = "WORD_LIST")
public class WordList implements Serializable {

    @PrimaryKey
    @Column(name = "WORD_LIST_ID", type = FieldType.LONG)
    protected long id;

    @Column(name = "WORD", type = FieldType.TEXT)
    private String word;

    @Column(name = "MOD_DATE", type = FieldType.LONG)
    private long modifiedDate;

    @Relation(joinColumn = "wordListId", targetEntity = Meaning.class, cascade = {CascadeTypes.PERSIST, CascadeTypes.DELETE})
    private List<Meaning> meanings = new ArrayList<Meaning>();

    @Relation(joinColumn = "wordListId", targetEntity = Sentence.class, cascade = {CascadeTypes.PERSIST, CascadeTypes.DELETE})
    private List<Sentence> sentences = new ArrayList<Sentence>();

    @Column(name = "FAVORITE", type = FieldType.INTEGER)
    private int favorite = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
