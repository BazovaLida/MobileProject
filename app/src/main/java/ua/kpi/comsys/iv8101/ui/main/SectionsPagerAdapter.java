package ua.kpi.comsys.iv8101.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ua.kpi.comsys.iv8101.R;
import ua.kpi.comsys.iv8101.ui.books.BooksFragment;
import ua.kpi.comsys.iv8101.ui.gallery.GalleryFragment;
import ua.kpi.comsys.iv8101.ui.graph.GraphFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.title_home, R.string.title_graph,
            R.string.title_books, R.string.title_gallery};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new GraphFragment();
            case 2:
                return new BooksFragment();
            case 3:
                return new GalleryFragment();
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // How many pages.
        return 4;
    }
}