package in.cubestack.material.androidmaterial.model;

import java.io.Serializable;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by arunk on 7/11/2015.
 */

@Table(name="SENTENCE")
public class Sentence implements Serializable {

    @PrimaryKey
    @Column(name = "SENTENCE_ID", type = FieldType.LONG)
    protected long id;

    @Column(name = "WORD_LIST_ID", type = FieldType.LONG)
    private long wordListId;

    @Column(name = "SENTENCE_TEXT", type = FieldType.TEXT)
    private String sentence;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWordListId() {
        return wordListId;
    }

    public void setWordListId(long wordListId) {
        this.wordListId = wordListId;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
