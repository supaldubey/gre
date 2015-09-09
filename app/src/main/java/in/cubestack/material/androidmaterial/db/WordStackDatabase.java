package in.cubestack.material.androidmaterial.db;

import in.cubestack.android.lib.storm.annotation.Database;
import in.cubestack.material.androidmaterial.model.Deck;
import in.cubestack.material.androidmaterial.model.DeckWordList;
import in.cubestack.material.androidmaterial.model.Meaning;
import in.cubestack.material.androidmaterial.model.Sentence;
import in.cubestack.material.androidmaterial.model.WordList;

/**
 * Created by arunk on 7/10/2015.
 */
@Database(name = "WORDSTACK_DB", tables = {
        WordList.class,
        Meaning.class,
        Sentence.class,
        Deck.class,
        DeckWordList.class
})
public class WordStackDatabase {
}
