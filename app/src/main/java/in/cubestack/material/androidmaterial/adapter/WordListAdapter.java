package in.cubestack.material.androidmaterial.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.model.Meaning;
import in.cubestack.material.androidmaterial.model.Sentence;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.ui.AbstractCubeStackActivity;
import in.cubestack.material.androidmaterial.ui.MainApplication;
import in.cubestack.material.androidmaterial.util.LogUtils;

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
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordListViewHolder> {

    private static final String TAG = LogUtils.makeLogTag(WordListAdapter.class);

    private static final String WORD_DELIMITER = ", ";
    private static final String SENTENCE_DELIMITER = "\n\n";

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private List<WordList> mItems = new ArrayList<WordList>();

    private Context mContext;

    private Resources mRes;

    private boolean toggling = false;

    SparseArray<Boolean> selectedItems = new SparseArray<Boolean>();

    public WordListAdapter(Context context) {
        mContext = context;
        mRes = context.getResources();
    }

    @Override
    public WordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View wordCardView = LayoutInflater.from(mContext).inflate(R.layout.word_card_view, parent, false);
        return new WordListViewHolder(wordCardView);
    }

    @Override
    public void onBindViewHolder(final WordListViewHolder viewHolder, final int position) {
        viewHolder.wordView.setText(mItems.get(position).getWord());
        viewHolder.meaningsView.setText(formatMeanings(mItems.get(position).getMeanings()));
        viewHolder.sentencesView.setText(formatSentences(mItems.get(position).getSentences()));
        if (mItems.get(position).getFavorite() == 1) {
            viewHolder.btnMarkFavorite.setImageResource(R.drawable.star_yellow);
        } else {
            viewHolder.btnMarkFavorite.setImageResource(R.drawable.star_outline_gray);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class WordListViewHolder extends RecyclerView.ViewHolder {

        View mView;

        @Bind(R.id.word)
        TextView wordView;

        @Bind(R.id.meaningsView)
        TextView meaningsView;

        @Bind(R.id.sentencesView)
        TextView sentencesView;

        @Bind(R.id.btnMarkFavorite)
        ImageButton btnMarkFavorite;

        public WordListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

            btnMarkFavorite.setSelected(false);
            btnMarkFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Do something here
                    int pos = getAdapterPosition();
                    toggleFav(btnMarkFavorite, pos);
                }
            });

        }
    }

    private void toggleFav(final ImageButton favBtn, final int pos) {
        if (!toggling) {
            toggling = true;
            WordList wordList = mItems.get(pos);
            runToggleAnim(favBtn, wordList.getFavorite() == 0);
            wordList.setFavorite(wordList.getFavorite() == 0 ? 1 : 0);
            wordList.setModifiedDate(new Date().getTime());
            final long s = System.currentTimeMillis();
            MainApplication.service().update(wordList, new StormCallBack<WordList>() {
                @Override
                public void onUpdate(int updated) {
                    toggling = false;
                    final long e = System.currentTimeMillis();
                    final AbstractCubeStackActivity activity = (AbstractCubeStackActivity) mContext;
                    activity.toast("Updated in " + (e - s) + " ms");
                }
            });
            notifyItemChanged(pos);
        }
    }

    private void runToggleAnim(final ImageButton favBtn, final boolean markAsFav) {
        if (markAsFav) {
            //With animation
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(favBtn, "rotation", 0f, 360f);
            rotationAnim.setDuration(500);
            rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(favBtn, "scaleX", 0.2f, 1f);
            bounceAnimX.setDuration(500);
            bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(favBtn, "scaleY", 0.2f, 1f);
            bounceAnimY.setDuration(500);
            bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
            bounceAnimY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    favBtn.setImageResource(R.drawable.star_yellow);
                }
            });

            animatorSet.play(rotationAnim);
            animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                }
            });

            animatorSet.start();
        } else {
            // Without animation
            favBtn.setImageResource(R.drawable.star_outline_gray);
        }
    }

    private String formatMeanings(List<Meaning> meanings) {
        StringBuilder sb = new StringBuilder();
        if (meanings != null && !meanings.isEmpty()) {
            for (int i = 0; i < meanings.size(); i++) {
                if (i == meanings.size() - 1) {
                    sb.append(meanings.get(i).getMeaning());
                } else {
                    sb.append(meanings.get(i).getMeaning()).append(WORD_DELIMITER);
                }
            }
        }
        return sb.toString();
    }

    private String formatSentences(List<Sentence> sentences) {
        StringBuilder sb = new StringBuilder();
        if (sentences != null && !sentences.isEmpty()) {
            for (int i = 0; i < sentences.size(); i++) {
                if (i == sentences.size() - 1) {
                    sb.append(sentences.get(i).getSentence());
                } else {
                    sb.append(sentences.get(i).getSentence()).append(SENTENCE_DELIMITER);
                }
            }
        }
        return sb.toString();
    }

    public void clear() {
        this.mItems.clear();
    }


    public void addNewItems(List<WordList> items) {
        if (mItems == null) return;
        int posStart = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(posStart, items.size());

    }

    public void clearAndAddNewItems(List<WordList> items) {
        if (this.mItems == null) return;
        clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

}
