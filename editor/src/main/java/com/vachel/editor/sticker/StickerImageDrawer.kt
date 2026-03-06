package com.vachel.editor.sticker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.vachel.editor.R
import com.vachel.editor.databinding.StickerImageDrawerBinding

/**
 * Created by HHY on 2026/3/6 12:24
 * Desc:
 **/
class StickerImageDrawer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mBinding: StickerImageDrawerBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.sticker_image_drawer, this, true)
    }

    init {

    }

}