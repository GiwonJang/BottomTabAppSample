package io.handicraft.bottomtabappsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends BaseFragment {

	// rx
	private Subscription _subscription;

	// views
	@BindView(R.id.tv_hello)
	TextView tv_hello;

	public static DetailFragment newInstance() {
		//Bundle args = new Bundle();
		//args.putInt(ARGS_INSTANCE, instance);
		DetailFragment fragment = new DetailFragment();
		//fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_main, container, false);
		_butterknifeUnbinder = ButterKnife.bind(this, view);

		_subscription = Observable
				.just("Detail Fragment Selected")
				.filter(s -> !s.isEmpty())
				.map(String::toUpperCase)
				.take(1)
				.subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						// do nothing
					}

					@Override
					public void onError(Throwable e) {
						Logger.e(e, e.getMessage());
					}

					@Override
					public void onNext(String s) {
						tv_hello.setText(s);
					}
				});

		return view;
	}

	@Override
	public void onDestroyView() {

		_subscription.unsubscribe();

		super.onDestroyView();
	}
}
