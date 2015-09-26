package in.cubestack.material.androidmaterial.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;
import in.cubestack.android.lib.storm.criteria.StormRestrictions;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.adapter.WordListAdapter;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.ui.AbstractCubeStackActivity;
import in.cubestack.material.androidmaterial.ui.MainActivity;
import in.cubestack.material.androidmaterial.ui.MainApplication;
import in.cubestack.material.androidmaterial.util.DividerDecoration;
import in.cubestack.material.androidmaterial.util.EndlessRecyclerOnScrollListener;
import in.cubestack.material.androidmaterial.util.LogUtils;
import in.cubestack.material.androidmaterial.util.UiUtils;

/**
 * Sample application for Storm ORM.
 *
 * Check on Google play: https://play.google.com/store/apps/developer?id=Cube+Stack
 * Storm on GIT: https://github.com/supaldubey/storm
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
public class MainFragment extends AbstractFragment {

    private static final String TAG = LogUtils.makeLogTag(MainFragment.class);

    int lastSelectedIndex = 0;

    @Bind(R.id.alphabet_scroller)
    LinearLayout alphabetScrollerLayout;


    @Bind(R.id.alphabet_rv)
    RecyclerView recyclerView;

    WordListAdapter wordListAdapter;

    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        f.setTitle("MAIN");
        return f;
    }

    public void updateData() {
        loadData(UiUtils.ALPHABETS.get(lastSelectedIndex), true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setupViews() {
        TextView alphaView;
        LinearLayout.LayoutParams lp;
        Resources res = getActivity().getResources();
        int idx = 0;
        for (final String alpha : UiUtils.ALPHABETS) {
            alphaView = new TextView(getActivity());
            alphaView.setText(alpha);
            //alphaView.setTextSize(16, TypedValue.COMPLEX_UNIT_SP);
            alphaView.setGravity(Gravity.CENTER);
            lp = new LinearLayout.LayoutParams(UiUtils.dpToPx(48),
                    UiUtils.dpToPx(48));
            lp.setMargins(0, 0, 50, 0);
            if (idx == 0) {
                alphaView.setTextColor(Color.WHITE);
                alphaView.setBackgroundResource(R.drawable.alphabet_pressed);
            } else {
                alphaView.setBackgroundResource(R.drawable.alphabet_selector);
                alphaView.setTextColor(res.getColor(R.color.primary));
            }
            final int index = idx;
            alphaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelect(index, alpha);
                }
            });
            alphabetScrollerLayout.addView(alphaView, lp);
            idx++;
        }

        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager alphabetLayoutManager = new LinearLayoutManager(getActivity());
        alphabetLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(alphabetLayoutManager);
        wordListAdapter = new WordListAdapter(getActivity());
        recyclerView.setAdapter(wordListAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager)
                recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                loadData(UiUtils.ALPHABETS.get(lastSelectedIndex), false);
            }
        });
        loadData(UiUtils.ALL, false);
    }

    int pageNumber = 0;

    private void onSelect(int idx, String word) {
        if (lastSelectedIndex != idx) {
            Resources res = getActivity().getResources();
            TextView lv = (TextView) alphabetScrollerLayout.getChildAt(lastSelectedIndex);
            lv.setBackgroundResource(R.drawable.alphabet_selector);
            lv.setTextColor(res.getColor(R.color.primary));

            TextView v = (TextView) alphabetScrollerLayout.getChildAt(idx);
            v.setBackgroundResource(R.drawable.alphabet_pressed);
            v.setTextColor(Color.WHITE);
            pageNumber = 0;
            lastSelectedIndex = idx;
            loadData(word, true);
        }
    }

    private void loadData(final String word, final boolean triggeredFromAlphabetFilter) {
        try {

            if (triggeredFromAlphabetFilter) {
                pageNumber = 0;
            }
            int pageNo = ++pageNumber;

            Restrictions restrictions = StormRestrictions.restrictionsFor(WordList.class);
            Restriction restriction = null;
            if (UiUtils.ALL.equals(word)) {
                restriction = restrictions.forAll();
            } else {
                restriction = restrictions.like("word", word.toLowerCase() + "%");
            }
            final long s = System.currentTimeMillis();
            final AbstractCubeStackActivity activity = (AbstractCubeStackActivity) getActivity();
            MainApplication.service().find(WordList.class, restriction.page(pageNo), Order.orderFor(WordList.class, new String[]{"word"}, SortOrder.ASC),
                    new StormCallBack<WordList>() {
                        @Override
                        public void onResults(final List<WordList> results) {
                            final long e = System.currentTimeMillis();
                            if (results != null && !results.isEmpty()) {
                                ((MainActivity) activity).showSnackBar("Fetched " + results.size() + " records in " + (e - s) + " ms");
                                if (triggeredFromAlphabetFilter) {
                                    wordListAdapter.clearAndAddNewItems(results);
                                    recyclerView.smoothScrollToPosition(0);
                                } else {
                                    wordListAdapter.addNewItems(results);
                                }
                            } else {
                                activity.toast(0, e - s);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            super.onError(throwable);
                        }
                    });
        } catch (Exception e) {
            LogUtils.LOGE(TAG, "", e);
        }
    }


}
