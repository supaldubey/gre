package in.cubestack.material.androidmaterial.db;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import in.cubestack.material.androidmaterial.json.MasterWordList;
import in.cubestack.material.androidmaterial.json.Wordlst;
import in.cubestack.material.androidmaterial.model.Meaning;
import in.cubestack.material.androidmaterial.model.Sentence;
import in.cubestack.material.androidmaterial.model.WordList;

/**
 * Created by supal on 11/7/15.
 */


public class InitializerService {

    public long[] initialize(Context context) throws Exception {
        Gson gson = new Gson();
        MasterWordList masterWordList =  gson.fromJson(new InputStreamReader(context.getAssets().open("gre.json")), MasterWordList.class);

        List<WordList> words = new LinkedList<WordList>();
        int size = 0;
        for(Wordlst wl: masterWordList.getWordlists()) {
            WordList wordList = new WordList();
            wordList.setWord(wl.getWord() != null ? wl.getWord().trim() : "");

            Meaning meaning;
            Sentence sentence;
            if(wl.getMeanings() != null) {
                for (String mean : wl.getMeanings()) {
                    meaning = new Meaning();
                    meaning.setMeaning(mean != null ? mean.trim() : "");
                    wordList.getMeanings().add(meaning);
                    size++;
                }
            }
            if(wl.getSentences() != null) {
                for (String sent : wl.getSentences()) {
                    sentence = new Sentence();
                    sentence.setSentence(sent != null ? sent.trim() : "");
                    wordList.getSentences().add(sentence);
                    size++;
                }
            }
            words.add(wordList);
            size++;
        }
        // Done loading / reading .. save
        long start = System.currentTimeMillis();
        new WordStackSyncService(context).save(words);
        long end = System.currentTimeMillis();
        return new long[] {size, words.size(), end - start};
    }
}
