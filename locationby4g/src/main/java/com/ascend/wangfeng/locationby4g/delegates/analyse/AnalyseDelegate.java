package com.ascend.wangfeng.locationby4g.delegates.analyse;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;
import com.ascend.wangfeng.locationby4g.util.ExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/4/3.
 * email 1040441325@qq.com
 */

public class AnalyseDelegate extends BottomItemDelegate {

    @BindView(R.id.rv_file)
    RecyclerView mRvFile;
    @BindView(R.id.rv_result)
    RecyclerView mRvList;
    @BindView(R.id.tv_imsi)
    TextView mTvImsi;
    private FileAdapter mFileAdapter;
    private ResultAdapter mResultAdapter;

    @OnClick(R.id.tv_choose_file)
    void chooseFile() {
        showChooseFileDialog();
    }

    @OnClick(R.id.tv_imsi)
    void setImsi() {
        showImsiDialog();
    }

    @OnClick(R.id.tv_crash)
    void crash() {
        // 添加标题
        mResultAdapter.removeAllHeaderView();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_searh_result, null);
        mResultAdapter.addHeaderView(view);
        mRvList.setVisibility(View.VISIBLE);
        // 动画
        LatteLoader.showLoading(getActivity());
        mEntries.clear();
        for (FileContent c : mFileContents) {
            for (CellMeaureAckEntry e : c.getEntries()) {
                // 是否已存在
                boolean has = false;
                for (int i = 0; i < mEntries.size(); i++) {
                    if (mEntries.get(i).getImsi().equals(e.getImsi())) {
                        mEntries.get(i).addCrash();
                        has = true;
                    }
                }
                if (!has) {
                    mEntries.add(e);
                }
            }
        }
        // 过滤碰撞系数过低的
        for (int i = mEntries.size() - 1; i >= 0; i--) {
            // 未在全部文件中找到的
            mEntries.get(i).setItemType(CellMeaureAckEntry.TYPE_NORMAL);
            if (mEntries.get(i).getCrashCounter() < mFileContents.size()) {
                mEntries.remove(i);
            }
        }
        mResultAdapter.notifyDataSetChanged();
        LatteLoader.stopLoading();
    }

    @OnClick(R.id.tv_imsi_search)
    void searchImsi() {
        mResultAdapter.removeAllHeaderView();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_search_imsi, null);
        mResultAdapter.addHeaderView(view);
        mRvList.setVisibility(View.VISIBLE);
        LatteLoader.showLoading(getActivity());
        mEntries.clear();
        for (FileContent c : mFileContents) {
            for (CellMeaureAckEntry e : c.getEntries()) {
                if (mImsi.equals(e.getImsi())) {
                    e.setItemType(CellMeaureAckEntry.TYPE_IMSI_RESULT);
                    e.setFileName(c.getFileName());
                    mEntries.add(e);
                }
            }
        }
        mResultAdapter.notifyDataSetChanged();
        LatteLoader.stopLoading();
    }

    // 文件列表
    private ArrayList<File> mFiles;
    private ArrayList<FileContent> mFileContents;
    // 查询结果
    private ArrayList<CellMeaureAckEntry> mEntries;
    private String mImsi ="";

    @Override
    public Object setLayout() {
        return R.layout.delegate_analyse;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initData();
        initRv();

    }

    private void initRv() {
        // 文件列表
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRvFile.setLayoutManager(manager);
        mFileAdapter = new FileAdapter(mFileContents);
        mRvFile.setAdapter(mFileAdapter);
        mRvFile.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.colorAccent), 1));
        mFileAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_file, null));
        // 结果集
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getActivity());
        mRvList.setLayoutManager(manager2);
        mResultAdapter = new ResultAdapter(mEntries);
        mRvList.setAdapter(mResultAdapter);
        mRvList.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.colorAccent), 1));
        mResultAdapter.setListener(new ResultAdapter.OnClickListener() {
            @Override
            public void onclick(CellMeaureAckEntry entry) {
                mImsi = entry.getImsi();
                mTvImsi.setText(mImsi);
            }
        });
    }

    private void initData() {
        mFiles = new ArrayList<>();
        mEntries = new ArrayList<>();
        mFileContents = new ArrayList<>();
    }

    /**
     * 文件选择dialog
     */
    private void showChooseFileDialog() {
        final File[] files = FileUtil.getFileList(getString(R.string.base_path));
        final boolean[] choices = new boolean[files.length];
        final String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            choices[i] = false;
            fileNames[i] = files[i].getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("选择文件");
        builder.setMultiChoiceItems(fileNames, choices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                choices[which] = isChecked;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFiles.clear();
                for (int i = 0; i < choices.length; i++) {
                    if (choices[i]) {
                        mFiles.add(files[i]);
                    }
                }
                showFile();
            }
        });
        builder.show();
    }

    private void showFile() {
        mFileContents.clear();
        for (File file : mFiles) {
            FileContent content = new FileContent();
            content.setFileName(file.getName());
            List<CellMeaureAckEntry> entries = ExcelUtil.readExcel(file);
            content.setEntries(entries);
            mFileContents.add(content);
        }
        mFileAdapter.notifyDataSetChanged();
    }

    // imis 设置
    private void showImsiDialog() {
        final EditText editText = new EditText(getActivity());
        if (mImsi != null && !mImsi.isEmpty()) {
            editText.setText(mImsi);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("IMSI设置");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mImsi = editText.getText().toString();
                mTvImsi.setText(mImsi);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
