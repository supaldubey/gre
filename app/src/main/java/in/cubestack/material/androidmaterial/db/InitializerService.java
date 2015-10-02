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
 * Sample application for Storm ORM.
 *
 * Check on Google play: https://play.google.com/store/apps/developer?id=Cube+Stack
 * Storm on GIT: https://github.com/supald/storm
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
