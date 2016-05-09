package io.handicraft.bottomtabappsample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

	// constants
	private static final String TAG = "MainActivityFragment";

	// rx
	//private Subscription _subscription;

	// views
	private Unbinder _unbinder;

	@BindView(R.id.tv_hello)
	TextView tv_hello;

	public MainActivityFragment() {
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
				.just("Hello RxAndroid!!")
				.compose(this.<String>bindToLifecycle())
				.filter(s -> !s.isEmpty())
				.map(String::toUpperCase)
				.take(1)
				.subscribe(s -> {
					tv_hello.setText(s);
				});

		RxView.clicks(tv_hello)
				.subscribe(value -> {
					Toast.makeText(MainActivityFragment.this.getActivity(), value.toString(), Toast.LENGTH_SHORT).show();
				}, throwable -> {
					Log.e(TAG, "onCreateView: " + throwable.getMessage(), throwable);
				});


		return view;
	}

	@Override
	public void onDestroyView() {

		//_subscription.unsubscribe();

		super.onDestroyView();
		_unbinder.unbind();
	}
}
