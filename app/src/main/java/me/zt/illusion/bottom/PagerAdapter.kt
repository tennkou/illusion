package me.zt.illusion.bottom

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import me.zt.illusion.R
import me.zt.illusion.log
import kotlin.math.abs
import kotlin.random.Random

class PagerAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)

        return object : RecyclerView.ViewHolder(root) {}
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rv: RecyclerView = holder.itemView.findViewById(R.id.recycler_view)
        val tv: TextView = holder.itemView.findViewById(R.id.text)
        tv.text = "$position"
        rv.adapter = RecyclerViewAdapter()
        rv.layoutManager = LinearLayoutManager(holder.itemView.context)
    }
}

class PagerAdapter1 : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val root = LayoutInflater.from(container.context).inflate(R.layout.pager_item, container, false)
        val rv: RecyclerView = root.findViewById(R.id.recycler_view)
        val tv: TextView = root.findViewById(R.id.text)
        tv.text = "$position"
        rv.adapter = RecyclerViewAdapter()
        rv.layoutManager = LinearLayoutManager(root.context)
        container.addView(root)
        return root
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun getCount(): Int {
        return 20
    }
}


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = TextView(parent.context)
        v.gravity = Gravity.CENTER
        return object : RecyclerView.ViewHolder(v) {}
    }

    override fun getItemCount(): Int = 40

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val lp = holder.itemView.layoutParams ?: RecyclerView.LayoutParams(-1, -1)
        lp.height = abs(Random(System.currentTimeMillis()).nextInt()) % 300 + 150
        holder.itemView.layoutParams = lp
        (holder.itemView as TextView).text = "rv: $position"
        val view = holder.itemView
        holder.itemView.setOnClickListener {
            log("sfsfs $view")
        }
    }
}