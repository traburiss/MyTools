package com.traburiss.mytools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.traburiss.tools.recycle.itemdecoration.CommonItemDecoration;
import com.traburiss.tools.recycle.RecycleViewAdapter;
import com.traburiss.tools.recycle.RecycleViewHolder;
import com.traburiss.tools.utils.UnitUtils;
import com.traburiss.tools.view.headerlayout.TitleBarSetter;
import com.traburiss.tools.view.headerlayout.TitleBar;
import com.traburiss.tools.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Object> list = new ArrayList<>();
    RecycleViewAdapter<Object> adapter;
    int index = 0;
    RecyclerView rv_list;
    TextView textView;
    int s = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        GlideApp.with(this).load("http://img1.gamersky.com/image2017/05/20170531_zy_164_4/001_S.jpg")
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into((ImageView) findViewById(R.id.iv_test));

        TitleBar hl_title = (TitleBar) findViewById(R.id.hl_title);
        new TitleBarSetter(hl_title)
                .setTitle("标题")
                .addButton("替换", TitleBarSetter.ViewLocation.LEFT, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final EditText editText = new EditText(MainActivity.this);
                        editText.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        editText.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        new AlertDialog
                                .Builder(MainActivity.this)
                                .setTitle("num:" + adapter.getDataNum())
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        int position = Integer.valueOf(editText.getText().toString());
                                        adapter.changeData(position, "replace data" + position);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create().show();
                    }
                })
                .addButton("插入", R.mipmap.ic_launcher_round, 20, 0xffffccff, TitleBarSetter.ViewLocation.RIGHT, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(MainActivity.this, KotlinActivity.class));
                    }
                })
                .addImageButton(R.mipmap.ic_launcher_round, TitleBarSetter.ViewLocation.LEFT, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final EditText editText = new EditText(MainActivity.this);
                        editText.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        editText.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        new AlertDialog
                                .Builder(MainActivity.this)
                                .setTitle("num:" + adapter.getDataNum())
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        int position = Integer.valueOf(editText.getText().toString());
                                        adapter.insertData(position, "insert data" + position);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create().show();
                    }
                })
                .addButton("换结构", TitleBarSetter.ViewLocation.RIGHT, new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        switch (index){

                            case 0:
                                textView.setText("linear v 顺");
                                rv_list.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                                index = 1;
                                break;
                            case 1:
                                textView.setText("linear h 顺");
                                rv_list.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                index = 2;
                                break;
                            case 2:
                                textView.setText("g h 顺");
                                rv_list.setLayoutManager(getGrid(MainActivity.this, s, GridLayoutManager.HORIZONTAL, false));
                                index = 3;
                                break;
                            case 3:
                                textView.setText("g v 顺");
                                rv_list.setLayoutManager(getGrid(MainActivity.this, s, GridLayoutManager.VERTICAL, false));
                                index = 4;
                                break;
                            case 4:
                                textView.setText("sg v 3");
                                rv_list.setLayoutManager(new StaggeredGridLayoutManager(s, StaggeredGridLayoutManager.VERTICAL));
                                index = 5;
                                break;
                            case 5:
                                textView.setText("sg h 3");
                                rv_list.setLayoutManager(new StaggeredGridLayoutManager(s, StaggeredGridLayoutManager.HORIZONTAL));
                                index = 0;
                                break;
                        }
                    }
                })
                .addText("2text", 20, 0xffffcccc, TitleBarSetter.ViewLocation.CENTER)
                .Set();
        initList();
    }

    private GridLayoutManager getGrid(Context context,int spanCount, int ori, boolean res){

        final GridLayoutManager manager = new GridLayoutManager(context, spanCount, ori, res);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return adapter.isFooterOrHeader(i) ? manager.getSpanCount() : 1;
            }
        });
        return manager;
    }

    private void initList(){

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        int ps = UnitUtils.dp2px(this,15);
//        rv_list.addItemDecoration(new SpacesItemDecoration(ps,ps));
        rv_list.addItemDecoration(new CommonItemDecoration(this, 8,16, android.R.color.holo_blue_bright));
        textView.setText("g h 顺");
        rv_list.setLayoutManager(getGrid(MainActivity.this, s, GridLayoutManager.HORIZONTAL, false));
        int i = 0;
        for (i = 0; i < 10; i++) {

            list.add("item " + i);
        }
        for (; i < 31; i++) {

            ArrayList<String> strings = new ArrayList<>();
            strings.add("item1 " + i);
            strings.add("item2 " + i);
            strings.add("item3 " + i);
            list.add(strings);

        }
        for (; i < 51; i++) {

            list.add("item " + i);
        }
        RecycleViewAdapter.DifferentItemSetter support = new RecycleViewAdapter.DifferentItemSetter() {
            @Override
            public int getViewIdByType(int viewType) {

                if (viewType == 0) return R.layout.item;
                else return R.layout.item3;
            }

            @Override
            public int getTypeByPosition(int position) {

                if (list.get(position) instanceof String) return 0;
                else if (list.get(position) instanceof ArrayList)return 1;
                return -1;
            }
        };
        adapter = new RecycleViewAdapter<Object>(this, support, list) {
            @Override
            public void bindDataWitHView(RecycleViewHolder holder, int position, int viewType) {

                holder.getConvertView().setBackgroundColor((int)(Math.random() * 0xFFFFFF) + 0xFF000000);
                if (viewType == 0){

                    TextView tv_item = holder.getView(R.id.tv_item);
                    tv_item.setText(getData(position).toString());
                }else if(viewType == 1){

                    TextView tv_item1 = holder.getView(R.id.tv_item1);
                    TextView tv_item2 = holder.getView(R.id.tv_item2);
                    TextView tv_item3 = holder.getView(R.id.tv_item3);
                    ArrayList<String> strings = (ArrayList) getData(position);
                    tv_item1.setText(strings.get(0));
                    tv_item2.setText(strings.get(1));
                    tv_item3.setText(strings.get(2));
                }
            }
        };
        adapter.setOnItemClickListener(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, RecycleViewAdapter adapter) {

                ToastUtils.showShort(getBaseContext(), adapter.getData(position).toString());
            }
        });

        adapter.setOnItemLongClickListener(new RecycleViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position, RecycleViewAdapter adapter) {

                ToastUtils.showShort(getBaseContext(), "long:::" + adapter.getData(position).toString());
                adapter.removeData(position);
                return true;
            }
        });

        final View head = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        head.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RecyclerView headRV = (RecyclerView) head.findViewById(R.id.rv_head);
        headRV.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> headdata = new ArrayList<>();
        for (int j = 0; j < 6; j++) {

            headdata.add("head" + j);
        }
        RecycleViewAdapter<String> headerAdapter = new RecycleViewAdapter<String>(this, R.layout.item, headdata) {
            @Override
            public void bindDataWitHView(RecycleViewHolder holder, int position, int viewType) {

                TextView textview = holder.getView(R.id.tv_item);
                textview.setText(headdata.get(position));
            }
        };
        headRV.setAdapter(headerAdapter);



        final View foot = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        foot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RecyclerView footRv = (RecyclerView) foot.findViewById(R.id.rv_head);
        footRv.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> footDa = new ArrayList<>();
        for (int j = 0; j < 6; j++) {

            footDa.add("foot" + j);
        }
        RecycleViewAdapter<String> footAd = new RecycleViewAdapter<String>(this, R.layout.item, footDa) {
            @Override
            public void bindDataWitHView(RecycleViewHolder holder, int position, int viewType) {

                TextView textview = holder.getView(R.id.tv_item);
                textview.setText(footDa.get(position));
            }
        };
        footRv.setAdapter(footAd);

        View textHeader = LayoutInflater.from(this).inflate(R.layout.item, null);
        textHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView headtv = (TextView) textHeader.findViewById(R.id.tv_item);
        headtv.setText("header");
        View textFooter = LayoutInflater.from(this).inflate(R.layout.item, null);
        textFooter.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView foottv = (TextView) textFooter.findViewById(R.id.tv_item);
        foottv.setText("header");

//        adapter.setHeaderView(head);
        adapter.setFooterView(foot);

        ArrayList<String> data = new ArrayList<>();
        for (int j = 0; j < 100; j++){

            data.add("data " + j);
        }
        NormalAdapter adapter2 = new NormalAdapter(this, data);

        rv_list.setAdapter(adapter);
    }

    private class NormalAdapter extends RecyclerView.Adapter<RecycleViewHolder>{

        List<String> data;
        private LayoutInflater layoutInflater;

        public NormalAdapter(Context context, List<String> data){

            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public RecycleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            return new RecycleViewHolder(layoutInflater.inflate(R.layout.item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(RecycleViewHolder recycleViewViewHolder, int i) {

            TextView textView = recycleViewViewHolder.getView(R.id.tv_item);
            textView.setText(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
