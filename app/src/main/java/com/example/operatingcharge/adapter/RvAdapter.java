package com.example.operatingcharge.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.operatingcharge.R;
import com.example.operatingcharge.entity.HairBean;
import java.util.List;

/**
 * Author : 赵彬彬
 * Date   : 2019/9/29
 * Time   : 23:20
 * Desc   :
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.TaskViewHolder> {

    private Context mContext;
    private List<HairBean> mTaskItemBeanList;
    private LayoutInflater mLayoutInflater;

    public RvAdapter(Context context, List<HairBean> taskItemBeanList) {
        mContext = context;
        mTaskItemBeanList = taskItemBeanList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(mLayoutInflater.inflate(R.layout.cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        // TODO: 2018/3/21 赋值
        // TODO: 2018/3/21 赋值
        // TODO: 2018/3/21 赋值
        // 这个是子项的左上角的数量标记
        final HairBean bean = mTaskItemBeanList.get(position);
        if (null == bean) return;
        holder.username.setText(TextUtils.isEmpty(bean.getUsername()) ? "无" : bean.getUsername());                   // 姓名
        holder.gender.setText(TextUtils.isEmpty(bean.getSex()) ? "无" : bean.getSex());                               // 性别
        holder.worknumber.setText(TextUtils.isEmpty(bean.getWorkNumber()) ? "无" : bean.getWorkNumber());             // 工号
        holder.apartment.setText(TextUtils.isEmpty(bean.getApartment()) ? "无" : bean.getApartment());                // 部门
        holder.scheduleItem.setText(TextUtils.isEmpty(bean.getBookProject()) ? "无" : bean.getBookProject());         // 预定项目
        holder.scheduleTime.setText(TextUtils.isEmpty(bean.getTime()) ? "无" : bean.getTime());                       // 预定时间
        holder.telephone.setText(TextUtils.isEmpty(bean.getPhone()) ? "无" : bean.getPhone());                        // 电话号码
        /**
         * 容器点击事件
         */
        holder.baseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mTaskItemBeanList ? 0 : mTaskItemBeanList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private CardView baseContainer;                    // 整个cardView
        private TextView username;                         // 用户名
        private TextView gender;                           // 性别
        private TextView worknumber;                       // 工号
        private TextView apartment;                        // 部门
        private TextView scheduleItem;                     // 预定项目
        private TextView scheduleTime;                      // 预定时间
        private TextView telephone;                        // 联系电话

        public TaskViewHolder(View v) {
            super(v);
            baseContainer = (CardView) v.findViewById(R.id.baseContainer);
            username = (TextView) v.findViewById(R.id.username);
            gender = (TextView) v.findViewById(R.id.gender);
            worknumber = (TextView) v.findViewById(R.id.worknumber);
            apartment = (TextView) v.findViewById(R.id.apartment);
            scheduleItem = (TextView) v.findViewById(R.id.schedule_item);
            scheduleTime = (TextView) v.findViewById(R.id.schedule_time);
            telephone = (TextView) v.findViewById(R.id.telephone);
        }
    }

    /**
     * 点击事件
     */
    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
