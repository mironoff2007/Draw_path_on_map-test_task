package ru.mironov.drawpathonmaptesttask

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(
    context: Context,
    titles: List<String>,
) : ArrayAdapter<String?>(context, R.layout.spinner_item) {

    var spinnerTitles: ArrayList<String> = titles as ArrayList<String>
    var mContext: Context = context

    fun getIndexOf(name:String):Int{
        return spinnerTitles.indexOf(name)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return spinnerTitles.size
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var mViewHolder = ViewHolder()
        if (convertView == null) {
            val mInflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = mInflater.inflate(R.layout.spinner_item, null, false)
            mViewHolder.mName = convertView.findViewById<View>(R.id.parseTypeText) as TextView
            convertView.tag = mViewHolder
        } else {
            mViewHolder = convertView.tag as ViewHolder
        }
        mViewHolder.mName!!.text = spinnerTitles[position]
        return convertView!!
    }

    private class ViewHolder {
        var mName: TextView? = null
    }
}
