package in.cubestack.material.androidmaterial.model;

import java.io.Serializable;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by arunk on 7/11/2015.
 */

@Table(name="MEANING")
public class Meaning implements Serializable {

    @PrimaryKey
    @Column(name = "MEANING_ID", type = FieldType.LONG)
    protected long id;

    @Column(name = "WORD_LIST_ID", type = FieldType.LONG)
    private long wordListId;

    @Column(name = "MEANING_TEXT", type = FieldType.TEXT)
    private String meaning;

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

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
