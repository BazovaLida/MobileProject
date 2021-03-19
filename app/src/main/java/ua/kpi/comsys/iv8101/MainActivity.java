package ua.kpi.comsys.iv8101;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import ua.kpi.comsys.iv8101.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabber);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_tab1_backgr);
        tabs.getTabAt(1).setIcon(R.drawable.ic_tab2_backgr);
        tabs.getTabAt(2).setIcon(R.drawable.ic_tab3_backgr);
        tabs.getTabAt(3).setIcon(R.drawable.ic_tab4_backgr);
    }
}