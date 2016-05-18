package io.handicraft.bottomtabappsample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodayFragment extends BaseFragment {

	// constants
	private static final String TAG = "TodayFragment";

	// rx
	//private Subscription _subscription;

	// views
	@BindView(R.id.btn_detail)
	Button   btn_detail;
	@BindView(R.id.tv_hello)
	TextView tv_hello;

	public static TodayFragment newInstance() {
		//Bundle args = new Bundle();
		//args.putInt(ARGS_INSTANCE, instance);
		TodayFragment fragment = new TodayFragment();
		//fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_main, container, false);
		_unbinder = ButterKnife.bind(this, view);

		/*Observable<String> simpleObservable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				Log.d(TAG, "call: ");
				subscriber.onNext("Hello RxAndroid");
				subscriber.onCompleted();
			}
		});

		_subscription = simpleObservable
				.subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				Log.d(TAG, "onCompleted: ");
			}

			@Override
			public void onError(Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onNext(String s) {
				Log.d(TAG, "onNext: ");
				TextView tv_hello = ButterKnife.findById(view, R.id.tv_hello);
				tv_hello.setText(s);
			}
		});*/

		Observable
				.just("Today Tab Selected")
				.compose(this.<String>bindToLifecycle())
				.filter(s -> !s.isEmpty())
				.map(String::toUpperCase)
				.take(1)
				.subscribe(s -> {
					tv_hello.setText(s);
				});

		RxView.clicks(btn_detail)
				.subscribe(value -> {
					if (_fragmentNavigation != null) {
						_fragmentNavigation.pushFragment(DetailFragment.newInstance());
					}
				}, throwable -> {
					Logger.e(throwable, throwable.getMessage());
				});

		return view;
	}

	@Override
	public void onDestroyView() {

		//_subscription.unsubscribe();

		super.onDestroyView();
	}

	/*@OnClick(R.id.btn_detail)
	void onBtnDetailClick() {
		if (_fragmentNavigation != null) {
			_fragmentNavigation.pushFragment(DetailFragment.newInstance());
		}
	}*/
}
