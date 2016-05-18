package io.handicraft.bottomtabappsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.Unbinder;


public class BaseFragment extends RxFragment {
	FragmentNavigation _fragmentNavigation;

	// butterknife unbinder
	protected Unbinder _unbinder;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
		}
	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		mButton = (Button) view.findViewById(R.id.button);
		return view;
	}*/

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

		if (_unbinder != null)
			_unbinder.unbind();
	}
}
