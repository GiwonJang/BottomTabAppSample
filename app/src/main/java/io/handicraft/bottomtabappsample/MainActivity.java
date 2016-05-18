package io.handicraft.bottomtabappsample;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends RxAppCompatActivity implements BaseFragment.FragmentNavigation {
	private BottomBar         mBottomBar;
	private FragNavController mNavController;

	private final int BOTTOM_TAB_INDEX_TODAY    = FragNavController.TAB1;
	private final int BOTTOM_TAB_INDEX_OLDER    = FragNavController.TAB2;
	private final int BOTTOM_TAB_INDEX_SETTINGS = FragNavController.TAB3;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		List<Fragment> fragments = new ArrayList<>(5);

		fragments.add(TodayFragment.newInstance());
		fragments.add(OlderFragment.newInstance());
		fragments.add(SettingsFragment.newInstance());

		mNavController =
				new FragNavController(savedInstanceState, getSupportFragmentManager(), R.id.container, fragments);

		//mBottomBar = BottomBar.attach(this, savedInstanceState);
		mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.myCoordinatorLayout),
				findViewById(R.id.myScrollingContent), savedInstanceState);
		// Disable the left bar on tablets and behave exactly the same on mobile and tablets instead.
		//mBottomBar.noTabletGoodness();

		// Show all titles even when there's more than three tabs.
		mBottomBar.useFixedMode();

		// Use the dark theme.
		//mBottomBar.useDarkTheme();

		// Set the color for the active tab. Ignored on mobile when there are more than three tabs.
		//mBottomBar.setActiveTabColor("#FFFFFF");

		// Use custom text appearance in tab titles.
		//mBottomBar.setTextAppearance(R.style.MyTextAppearance);

		// Use custom typeface that's located at the "/src/main/assets" directory. If using with
		// custom text appearance, set the text appearance first.
		//mBottomBar.setTypeFace("MyFont.ttf");

		//mBottomBar.noTopOffset();
		//mBottomBar.noNavBarGoodness();

		mBottomBar.setItemsFromMenu(R.menu.menu_bottombar, new OnMenuTabClickListener() {
			@Override
			public void onMenuTabSelected(@IdRes int menuItemId) {
				switch (menuItemId) {
					case R.id.bb_menu_today:
						mNavController.switchTab(BOTTOM_TAB_INDEX_TODAY);
						break;
					case R.id.bb_menu_older:
						mNavController.switchTab(BOTTOM_TAB_INDEX_OLDER);
						break;
					case R.id.bb_menu_settings:
						mNavController.switchTab(BOTTOM_TAB_INDEX_SETTINGS);
						break;
				}
			}

			@Override
			public void onMenuTabReSelected(@IdRes int menuItemId) {
				mNavController.clearStack();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (mNavController.getCurrentStack().size() > 1) {
			mNavController.pop();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Necessary to restore the BottomBar's state, otherwise we would
		// lose the current tab on orientation change.
		mBottomBar.onSaveInstanceState(outState);
		mNavController.onSaveInstanceState(outState);
	}

	@Override
	public void pushFragment(Fragment fragment) {
		mNavController.push(fragment);
	}
}
