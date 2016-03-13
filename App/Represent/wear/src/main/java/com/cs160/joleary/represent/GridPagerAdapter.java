package com.cs160.joleary.represent;

import android.graphics.drawable.ColorDrawable;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.CardFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Created by yannie on 3/2/16.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {
    private static final int TRANSITION_DURATION_MILLIS = 100;

    private final Context mContext;
    private List<Row> mRows;
    private ColorDrawable mDefaultBg;
    private ColorDrawable mClearBg;

    public GridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        mRows = new ArrayList<GridPagerAdapter.Row>();

        mRows.add(new Row(
                (new OverviewFragment()),
                (new OverviewFragment1()),
                (new OverviewFragment2())));
//        mRows.add(new Row(
//                cardFragment(R.string.rep1_name, R.string.rep1_party, R.drawable.barbara_boxer),
//                cardFragment(R.string.rep2_name, R.string.rep2_party, R.drawable.dianne_feinstein),
//                cardFragment(R.string.rep3_name, R.string.rep3_party, R.drawable.barbara_lee)));
        mRows.add(new Row(new VoteFragment()));
//        mRows.add(new Row(new VoteFragment1()));
        mDefaultBg = new ColorDrawable(R.color.white);
        mClearBg = new ColorDrawable(R.color.white);
    }

//    LruCache<Integer, Drawable> mRowBackgrounds = new LruCache<Integer, Drawable>(3) {
//        @Override
//        protected Drawable create(final Integer row) {
//            int resid = BG_IMAGES[row % BG_IMAGES.length];
//            new DrawableLoadingTask(mContext) {
//                @Override
//                protected void onPostExecute(Drawable result) {
//                    TransitionDrawable background = new TransitionDrawable(new Drawable[] {
//                            mDefaultBg,
//                            result
//                    });
//                    mRowBackgrounds.put(row, background);
//                    notifyRowBackgroundChanged(row);
//                    background.startTransition(TRANSITION_DURATION_MILLIS);
//                }
//            }.execute(resid);
//            return mDefaultBg;
//        }
//    };
//
//    LruCache<Point, Drawable> mPageBackgrounds = new LruCache<Point, Drawable>(3) {
//        @Override
//        protected Drawable create(final Point page) {
//            // place bugdroid as the background at row 2, column 1
//            if (page.y == 2 && page.x == 1) {
//                int resid = R.drawable.food_bg_160;
//                new DrawableLoadingTask(mContext) {
//                    @Override
//                    protected void onPostExecute(Drawable result) {
//                        TransitionDrawable background = new TransitionDrawable(new Drawable[] {
//                                mClearBg,
//                                result
//                        });
//                        mPageBackgrounds.put(page, background);
//                        notifyPageBackgroundChanged(page.y, page.x);
//                        background.startTransition(TRANSITION_DURATION_MILLIS);
//                    }
//                }.execute(resid);
//            }
//            return android.support.wearable.view.GridPagerAdapter.BACKGROUND_NONE;
//        }
//    };

    private Fragment cardFragment(int titleRes, int textRes, int picture) {
        Resources res = mContext.getResources();
        CardFragment fragment;
        fragment = CardFragment.create(res.getText(titleRes), res.getText(textRes), picture);
        // Add some extra bottom margin to leave room for the page indicator
//        fragment.setCardMarginBottom(
//                res.getDimensionPixelSize(R.dimen.card_margin_bottom));
        return fragment;
    }

    static final int[] BG_IMAGES = new int[] {
            R.color.white
    };

    /** A convenient container for a row of fragments. */
    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>();

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f);
            }
        }

        public void add(Fragment f) {
            columns.add(f);
        }

        Fragment getColumn(int i) {
            return columns.get(i);
        }

        public int getColumnCount() {
            return columns.size();
        }
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

//    @Override
//    public Drawable getBackgroundForRow(final int row) {
//        return mRowBackgrounds.get(row);
//    }
//
//    @Override
//    public Drawable getBackgroundForPage(final int row, final int column) {
//        return mPageBackgrounds.get(new Point(column, row));
//    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }
//
//    class DrawableLoadingTask extends AsyncTask<Integer, Void, Drawable> {
//        private static final String TAG = "Loader";
//        private Context context;
//
//        DrawableLoadingTask(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected Drawable doInBackground(Integer... params) {
//            Log.d(TAG, "Loading asset 0x" + Integer.toHexString(params[0]));
//            return context.getResources().getDrawable(params[0]);
//        }
//    }
}


