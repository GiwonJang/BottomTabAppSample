package io.handicraft.bottomtabappsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import butterknife.Unbinder;


public class BaseFragment extends Fragment {// RxFragment {
	FragmentNavigation _fragmentNavigation;

	// butterknife unbinder
	protected Unbinder _butterknifeUnbinder;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof FragmentNavigation) {
			_fragmentNavigation = (FragmentNavigation) context;
		}
	}

	public interface FragmentNavigation {
		void pushFragment(Fragment fragment);
	}

	@Override
	public void onDestroyView() {

		//_subscription.unsubscribe();

		super.onDestroyView();

		if (_butterknifeUnbinder != null)
			_butterknifeUnbinder.unbind();
	}
}
