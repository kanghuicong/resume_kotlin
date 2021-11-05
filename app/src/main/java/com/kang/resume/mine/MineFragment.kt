package com.kang.resume.mine

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.common.base.Objects
import com.kang.resume.MainViewModel
import com.kang.resume.R
import com.kang.resume.base.BaseFragment
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.MineFragmentBinding
import java.util.Objects.requireNonNull


/**
 * 类描述：
 * author:kanghuicong
 */
class MineFragment : BaseFragment<MineFragmentBinding>(), View.OnClickListener {

    override fun initLayout(): Int {
        return R.layout.mine_fragment
    }

    override fun initViewCreated(view:View) {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, MineModel(activity))

        mBinding.itemSetting.setOnClickListener(this)
        mBinding.tvMineName.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.item_setting -> {
                Toast.makeText(activity, "Setting", Toast.LENGTH_SHORT).show()
            }
            R.id.tv_mine_name -> {
                mBinding.vm?.doLogin()
            }
        }

    }


}