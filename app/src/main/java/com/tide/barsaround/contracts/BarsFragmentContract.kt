package com.tide.barsaround.contracts

import com.tide.barsaround.contracts.common.BaseListContract
import com.tide.barsaround.data.model.Result

interface BarsFragmentContract {
    interface View : BaseListContract.BaseListView<Result>, LocationPermissionContract
}
