package com.bottomtabbasestructuredemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bottomtabbasestructuredemo.R;
import com.bottomtabbasestructuredemo.crashhandler.CrashReportHandler;
import com.bottomtabbasestructuredemo.utilities.AppConstant;
import com.bottomtabbasestructuredemo.utilities.Utility;

import static com.bottomtabbasestructuredemo.BottomTabApplication.tabPosition;


public abstract class BaseActivity extends AppCompatActivity {

    private View view;

    private LinearLayout lnrContentMain;
    private Toolbar toolbar;

    private FloatingActionButton floatingActionButton;

    private TextView textViewTitle;
    private ImageView imageViewLeftMenuIcon;
    private ImageView imageViewLeftBackIcon;
    private ImageView imageViewRightIcon;
    private TabLayout tabLayout;


    public final int toolbarMenuIconId = R.id.image_view_toolbar_left_menu_icon;
    public final int toolbarBackIconId = R.id.image_view_toolbar_left_back_icon;
    public final int toolbarRightIconId = R.id.image_view_toolbar_right_menu_icon;

    private Activity myActivity;

    private boolean isTabShown;


    private boolean isFromSetTabSelection;

    private int selectedTabPosition;


    // Define activity tab here
    private String[] TabTitles = {"One", "Two", "Three", "Four", "Five"};
    private Integer[] TabIcons = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_share, R.drawable.ic_menu_send, R.drawable.ic_menu_slideshow};
    private Integer[] TabIconsSelected = {R.drawable.ic_menu_camera_selected, R.drawable.ic_menu_gallery_selected, R.drawable.ic_menu_share_seleted,
            R.drawable.ic_menu_send_selected, R.drawable.ic_menu_slideshow_selected};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_tabs);
        lnrContentMain = (LinearLayout) findViewById(R.id.content_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        textViewTitle = (TextView) findViewById(R.id.text_view_toolbar_title);
        imageViewLeftMenuIcon = (ImageView) findViewById(toolbarMenuIconId);
        imageViewLeftBackIcon = (ImageView) findViewById(toolbarBackIconId);
        imageViewRightIcon = (ImageView) findViewById(toolbarRightIconId);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        setSupportActionBar(toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(myActivity instanceof ChildNotabActivity)) {
                    startActivity(new Intent(BaseActivity.this, ChildNotabActivity.class));
                }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        imageViewLeftMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickToolBarOption(v.getId());
            }
        });

        imageViewLeftBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolBarOption(v.getId());
            }
        });

        imageViewRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolBarOption(v.getId());
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(null);

        toolbar.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        toolbar.setContentInsetsAbsolute(0, 0);

        view = LayoutInflater.from(this).inflate(setLayout(), lnrContentMain);

        myActivity = getMyActivity();

        //Check the activity is child of tab or not
        isFromSetTabSelection = false;

        // 1) For setup FAB position a
        isTabShown = isShowTab();
        if (isTabShown) {

            floatingActionButton.setTranslationY(Utility.convertDpToPixel(-35, this));
            setupTabLayout();
            bindWidgetsWithAnEvent();
        } else {
            tabLayout.setVisibility(View.GONE);

        }


        // 2) For setup data
        findViewById(view);
        initialization();
        setupData();
        setListeners();


        // 3) For tab selection
        selectedTabPosition = setTabPostion();
        setTabSelection(selectedTabPosition);

        // 4) set crash handler
        setCrashLogHandler();


    }

    /**
     * This method used to set toolbar tile for each screen
     *
     * @param title screen title
     */
    public void setToolbarTitle(String title) {
        textViewTitle.setText(title);
    }


    /**
     * This method used to hide slide menu icon (tool bar left icon)
     */
    public void hideSlideMenu() {
        imageViewLeftMenuIcon.setVisibility(View.GONE);
    }

    /**
     * This method used to shown Back icon at (tool bar left icon)
     *
     * @param icon optional back icon or directly define here if back icon same for all screen
     */
    public void showBackMenu(int icon) {
        imageViewLeftBackIcon.setVisibility(View.VISIBLE);
        if (icon != 0) {
            imageViewLeftBackIcon.setImageResource(icon);
        } else {
            imageViewLeftBackIcon.setImageResource(R.drawable.ic_launcher);
        }
    }

    /**
     * This method used to show tool bar right icon
     */
    public void showToolbarRightMenuIcon() {
        imageViewRightIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Simple method to set tabs
     * This method create the tabs according declaration
     */
    private void setupTabLayout() {

        for (int i = 0; i < TabTitles.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            generateCustomeTabs(i);
        }

    }

    /**
     * Set tab selection listner,
     */
    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!isFromSetTabSelection) {
                    setCurrentTabClass(tab.getPosition());
                } else {
                    isFromSetTabSelection = false;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * To do here when tab position clicked
     *
     * @param Position for index of position
     */
    private void setCurrentTabClass(int Position) {
        tabPosition = Position;
        switch (Position) {
            case 0:
                if (!(myActivity instanceof FirstActivity)) {
                    replaceClass(FirstActivity.class);
                }
                break;
            case 1:
                if (!(myActivity instanceof SecondActivity)) {
                    replaceClass(SecondActivity.class);
                }

                break;
            case 2:
                if (!(myActivity instanceof ThirdActivity)) {
                    replaceClass(ThirdActivity.class);
                }

                break;
            case 3:
                if (!(myActivity instanceof FourthActivity)) {
                    replaceClass(FourthActivity.class);
                }

                break;
            case 4:
                if (!(myActivity instanceof FifthActivity)) {
                    replaceClass(FifthActivity.class);
                }

                break;
        }


    }

    /**
     * Intent to new activity
     *
     * @param cls
     */
    public void replaceClass(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();

    }


    /**
     * Set here custom view for tab
     *
     * @param position
     */
    public void generateCustomeTabs(int position) {


        TabLayout.Tab tab = tabLayout.getTabAt(position);
        tab.setCustomView(R.layout.custom_tab); // set custome layout xml
        View v = tab.getCustomView();
        TextView title = (TextView) v.findViewById(R.id.text_view);
        TextView badgeCount = (TextView) v.findViewById(R.id.text_base);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
        title.setText(TabTitles[position]);
        imageView.setImageResource(TabIcons[position]);


    }


    /**
     * Change the counter of tab
     *
     * @param position
     * @param count
     */
    public void changeTabBadge(int position, int count) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View v = tab.getCustomView();
        TextView badgeCount = (TextView) v.findViewById(R.id.text_base);
        badgeCount.setText(count + "");
    }

    /**
     * This method used to handle crashreport
     */
    public void setCrashLogHandler() {
        if (AppConstant.isCrashReportOn) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CrashReportHandler.attach(getApplicationContext());
                }
            }).start();
        }
    }

    /**
     * Set Tab selection on position
     *
     * @param position
     */
    public void setTabSelection(int position) {
        if (isTabShown) {
            if (position != 0) {
                isFromSetTabSelection = true;
            }

            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View v = tab.getCustomView();
            tab.select();

            TextView title = (TextView) v.findViewById(R.id.text_view);
            TextView badgeCount = (TextView) v.findViewById(R.id.text_base);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                badgeCount.setBackground(ContextCompat.getDrawable(this, R.drawable.badge_circle_selected));
            } else {
                badgeCount.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.badge_circle_selected));
            }


            title.setTextColor(ContextCompat.getColor(this, R.color.selected_color));
            ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
            imageView.setImageResource(TabIconsSelected[position]);
        }

    }


    /**
     * This method used to set activity layout
     *
     * @return activity custom layout
     */
    public abstract int setLayout();

    /**
     * This method used to hide/show tab
     */
    public abstract boolean isShowTab();

    /**
     * This method used to find activity custom layout all views
     *
     * @param view activity custom layout reference for find custom layout views
     */
    public abstract void findViewById(View view);

    /**
     * This method used to initialize all activity variables,classes,interfaces etc.
     */
    public abstract void initialization();

    /**
     * This method used to setup or bind data with activity views
     */
    public abstract void setupData();

    /**
     * This method used to set all listener
     */
    public abstract void setListeners();

    /**
     * This method used to getactivity instance
     */
    public abstract Activity getMyActivity();

    /**
     * This method used to set your selected tab position
     */
    public abstract int setTabPostion();

    /**
     * This method used to get BaseActivity views click listener callback
     *
     * @param viewId clicked view id
     */
    public abstract void onClickToolBarOption(int viewId);
}
