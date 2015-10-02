package in.cubestack.material.androidmaterial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.cubestack.android.lib.storm.CascadeTypes;
import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Relation;
import in.cubestack.android.lib.storm.annotation.Table;

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
@Table(name="DECK")
public class Deck implements Serializable, Comparable<Deck> {

    @PrimaryKey
    @Column(name = "DECK_ID", type = FieldType.LONG)
    protected long id;

    @Column(name = "DECK_NAME", type = FieldType.TEXT)
    private String name;

    @Column(name = "MOD_DATE", type = FieldType.LONG)
    private long modifiedDate;

    @Relation(joinColumn = "deckId", targetEntity = DeckWordList.class, cascade = {CascadeTypes.PERSIST, CascadeTypes.DELETE})
    private List<DeckWordList> wordLists = new ArrayList<DeckWordList>();

    @Column(name = "DECK_COLOR_IDX", type = FieldType.INTEGER)
    private int colorIndex;

    public Deck(){}

    public Deck(String name, int colorIndex) {
        this.name = name;
        this.colorIndex = colorIndex;
        this.modifiedDate = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<DeckWordList> getWordLists() {
        return wordLists;
    }

    public void setWordLists(List<DeckWordList> wordLists) {
        this.wordLists = wordLists;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    @Override
    public int compareTo(Deck deck) {
        return ((Long) deck.getModifiedDate()).compareTo(this.getModifiedDate());
    }
}
