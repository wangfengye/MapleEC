package com.ascend.wangfeng.mapleec;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public class ExampleDelegate {/*extends LatteDelegate{
    @Override
    public Object setLayout() {
        return com.ascend.wangfeng.mapleec.R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        testRx() ;
        startActivity(new Intent(getActivity(), BaseActivity.class));
    }
    private void  testRestClient(){
        RestClient.builder()
                .url("user")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(_mActivity, response, Toast.LENGTH_SHORT).show();
                    }
                })
                .loader(getContext())
                .build().get();


    }
    //TODO:
    void  testRx(){
        RxRestClient.builder()
                .url("s")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public Context getShowContext() {
                        return getContext();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
}
