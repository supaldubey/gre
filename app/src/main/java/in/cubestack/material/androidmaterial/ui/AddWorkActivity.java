package in.cubestack.material.androidmaterial.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.model.Meaning;
import in.cubestack.material.androidmaterial.model.Sentence;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.util.UiUtils;

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
public class AddWorkActivity extends AbstractCubeStackActivity {

    private boolean exit = false;

    private ActionBarDrawerToggle drawerToggle;

    @Bind(R.id.view_nav_drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.view_nav)
    NavigationView navigationView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.edit_meanings)
    EditText meaning;

    @Bind(R.id.edit_usage)
    EditText usage;

    @Bind(R.id.edit_word)
    EditText word;

    boolean save = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavDrawer();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
initSave();
            }
        });
    }

    private void initSave() {
        if(TextUtils.isEmpty(word.getText())) {
            wobble(word);
            return;
        }


        if(TextUtils.isEmpty(meaning.getText())) {
            wobble(meaning);
            return;
        }

        if(TextUtils.isEmpty(usage.getText())) {
            wobble(usage);
            return;
        }

        WordList wordList = new WordList();
        wordList.setWord(word.getText().toString());

        String meanings = meaning.getText().toString();

        List<Meaning> meaningsList = new LinkedList<>();

            for(String str: meanings.split("\n")) {
                Meaning m = new Meaning();
                m.setMeaning(str);
                meaningsList.add(m);
            }


        List<Sentence> sentenceList = new LinkedList<>();

        for(String str: usage.getText().toString().split("\n")) {
            Sentence s = new Sentence();
            s.setSentence(str);
            sentenceList.add(s);
        }
        wordList.setMeanings(meaningsList);
        wordList.setSentences(sentenceList);

        final long startTime = System.currentTimeMillis();

        MainApplication.service().save(wordList, new StormCallBack<WordList>() {
            @Override
            public void onSave(WordList entity) {
                save = true;
                toast(String.format("Inserted in %s milliseconds", (System.currentTimeMillis() - startTime) ));
                finish();
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    @Override
    public void finish() {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("success", save);
        setResult(RESULT_OK, resultIntent);
        super.finish();
    }


    private void initNavDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(drawerToggle);
    }


    @Override
    Drawable getToolBarIcon() {
        return UiUtils.getDrawable(this, R.drawable.ic_close_white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_add_word;
    }
}
