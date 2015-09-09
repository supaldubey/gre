package in.cubestack.material.androidmaterial.model;

import java.io.Serializable;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by arunk on 7/11/2015.
 */
@Table(name="DECK_WORD_LIST")
public class DeckWordList implements Serializable{

    @PrimaryKey
    @Column(name = "DECK_WORD_LIST_ID", type = FieldType.LONG)
    protected long id;

    @Column(name = "DECK_ID", type = FieldType.LONG)
    private long deckId;

    @Column(name = "WORD_LIST_ID", type = FieldType.LONG)
    private long wordListId;
}
