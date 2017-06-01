package com.traburiss.mytools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.TextView
import com.traburiss.tools.recycle.itemdecoration.CommonItemDecoration
import com.traburiss.tools.recycle.RecycleViewAdapter
import com.traburiss.tools.recycle.RecycleViewViewHolder
import com.traburiss.tools.view.refresh.RefreshRecycleView
import java.util.*
import kotlin.collections.ArrayList

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        initView()
    }

    private fun initView(){

        val rrv = findViewById(R.id.rrv_list) as RefreshRecycleView
        rrv.setLayoutManager(object :StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL){})
        rrv.addItemDecoration(object : CommonItemDecoration(this,10f){})
        val adapter = object: RecycleViewAdapter<TestData>(this, R.layout.item, arrayListOf<TestData>()){

            override fun bindDataWitHView(holder: RecycleViewViewHolder, position: Int, viewType: Int) {

                val tv: TextView = holder.getView(R.id.tv_item)
                val data = getData(position)
                tv.text = data.data
                holder.convertView.setBackgroundColor(data.color)
            }
        }
        rrv.adapter = adapter
        var position = 1
        val range = 40
        rrv.setOnLoadListener {isRefresh: Boolean ->

            object : AsyncTask<Unit, Unit, Unit>(){

                override fun doInBackground(vararg params: Unit?) {

                    Thread.sleep(1000)
                }

                override fun onPostExecute(result: Unit?) {

                    if (isRefresh) adapter.setDatas(getDatas(position,range))
                    else adapter.addDatas(getDatas(position,range))
                    position+=range
                    if (adapter.dataNum > 200)rrv.finishLoad(true)
                    else rrv.finishLoad(false)
                }
            }.execute()
        }

        rrv.startRefresh()
    }

    fun getDatas(index: Int, range:Int):ArrayList<TestData>{

        return (index until  index+range).mapTo(arrayListOf<TestData>()) {

            object :TestData((Math.random() * 0xFFFFFF).toInt() + 0xFF000000.toInt(), "item$it  ${(object : Random(){}.nextDouble()*10000000000000).toInt()}"){}
        }
    }

    open class TestData(val color: Int, val data:String)
}
