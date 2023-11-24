package com.payment.ayushdigitils.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.ui.utils.DensityUtil
import java.util.Random


class CircleImageView : androidx.appcompat.widget.AppCompatImageView {
    private val DEFAULT_VIEW_SIZE: Int = DensityUtil.dp2px(context, 48f)
    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()
    private val mShaderMatrix = Matrix()
    private val mBitmapPaint: Paint? = Paint()
    private val mBorderPaint = Paint()
    private val mFillPaint = Paint()
    private var mBorderColor = DEFAULT_BORDER_COLOR
    private var mBorderWidth = DEFAULT_BORDER_WIDTH
    private var mFillColor = DEFAULT_FILL_COLOR
    private var mBitmap: Bitmap? = null
    private var mBitmapShader: BitmapShader? = null
    private var mBitmapWidth = 0
    private var mBitmapHeight = 0
    private var mDrawableRadius = 0f
    private var mBorderRadius = 0f
    private var mColorFilter: ColorFilter? = null
    private var mReady = false
    private var mSetupPending = false
    private var mBorderOverlay = false
    private var mDisableCircularTransformation = false
    private val mBackgroundColor = Color.CYAN
    private val mTextColor = Color.WHITE
    private var mTitleText = ""
    private val mTitleSize: Int = DensityUtil.sp2px(context, 32f)
    private var mTitleTextPaint: TextPaint? = null
    private var mBackgroundPaint: Paint? = null
    private var mInnerRectF: RectF? = null
    private var mViewSize = 0
    private var mRandom: Random? = null
    private val mFont = Typeface.defaultFromStyle(Typeface.NORMAL)

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0)
        mBorderWidth = a.getDimensionPixelSize(
            R.styleable.CircleImageView_civ_border_width,
            DEFAULT_BORDER_WIDTH
        )
        mBorderColor =
            a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR)
        mBorderOverlay =
            a.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY)
        mFillColor = a.getColor(R.styleable.CircleImageView_civ_fill_color, DEFAULT_FILL_COLOR)
        a.recycle()
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec)
        val height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec)
        mViewSize = Math.min(width, height)
        setMeasuredDimension(width, height)
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        mReady = true
        mRandom = Random()
        val position = mRandom!!.nextInt(6)
        //Title TextPaint
        mTitleTextPaint = TextPaint()
        mTitleTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        mTitleTextPaint!!.typeface = mFont
        mTitleTextPaint!!.textAlign = Paint.Align.CENTER
        mTitleTextPaint!!.isLinearText = true
        mTitleTextPaint!!.color = mTextColor
        mTitleTextPaint!!.textSize = mTitleSize.toFloat()

        //Background Paint
        mBackgroundPaint = Paint()
        mBackgroundPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        mBackgroundPaint!!.style = Paint.Style.FILL
        mBackgroundPaint!!.color = resources.getColor(colors[position])
        mInnerRectF = RectF()
        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) { String.format("ScaleType %s not supported.", scaleType) }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    override fun onDraw(canvas: Canvas) {
        if (mDisableCircularTransformation) {
            super.onDraw(canvas)
            return
        }
        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(
                mDrawableRect.centerX(),
                mDrawableRect.centerY(),
                mDrawableRadius,
                mFillPaint
            )
        }
        if (mTitleText != "") {
            //改变颜色
            val position = mRandom!!.nextInt(6)
            mBackgroundPaint!!.color = resources.getColor(colors[position])
            mInnerRectF!![0f, 0f, mViewSize.toFloat()] = mViewSize.toFloat()
            mInnerRectF!!.offset(
                ((width - mViewSize) / 2).toFloat(),
                ((height - mViewSize) / 2).toFloat()
            )
            val centerX = mInnerRectF!!.centerX()
            val centerY = mInnerRectF!!.centerY()
            val xPos = centerX.toInt()
            val yPos =
                (centerY - (mTitleTextPaint!!.descent() + mTitleTextPaint!!.ascent()) / 2).toInt()
            canvas.drawOval(mInnerRectF!!, mBackgroundPaint!!)
            canvas.drawText(
                mTitleText,
                xPos.toFloat(),
                yPos.toFloat(),
                mTitleTextPaint!!
            )
        } else {
            canvas.drawCircle(
                mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius,
                mBitmapPaint!!
            )
            if (mBorderWidth > 0) {
                canvas.drawCircle(
                    mBorderRect.centerX(),
                    mBorderRect.centerY(),
                    mBorderRadius,
                    mBorderPaint
                )
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    fun setName(name: String) {
        mTitleText = name
        invalidate()
    }

    var borderColor: Int
        get() = mBorderColor
        set(borderColor) {
            if (borderColor == mBorderColor) {
                return
            }
            mBorderColor = borderColor
            mBorderPaint.color = mBorderColor
            invalidate()
        }

    @Deprecated("Use {@link #setBorderColor(int)} instead")
    fun setBorderColorResource(@ColorRes borderColorRes: Int) {
        borderColor = context.resources.getColor(borderColorRes)
    }

    @get:Deprecated("Fill color support is going to be removed in the future")
    @set:Deprecated("Fill color support is going to be removed in the future")
    var fillColor: Int
        /**
         * Return the color drawn behind the circle-shaped drawable.
         *
         * @return The color drawn behind the drawable
         *
         */
        get() = mFillColor
        /**
         * Set a color to be drawn behind the circle-shaped drawable. Note that
         * this has no effect if the drawable is opaque or no drawable is set.
         *
         * @param fillColor The color to be drawn behind the drawable
         *
         */
        set(fillColor) {
            if (fillColor == mFillColor) {
                return
            }
            mFillColor = fillColor
            mFillPaint.color = fillColor
            invalidate()
        }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     * drawn behind the drawable
     *
     */
    @Deprecated("Fill color support is going to be removed in the future")
    fun setFillColorResource(@ColorRes fillColorRes: Int) {
        fillColor = context.resources.getColor(fillColorRes)
    }

    var borderWidth: Int
        get() = mBorderWidth
        set(borderWidth) {
            if (borderWidth == mBorderWidth) {
                return
            }
            mBorderWidth = borderWidth
            setup()
        }
    var isBorderOverlay: Boolean
        get() = mBorderOverlay
        set(borderOverlay) {
            if (borderOverlay == mBorderOverlay) {
                return
            }
            mBorderOverlay = borderOverlay
            setup()
        }
    var isDisableCircularTransformation: Boolean
        get() = mDisableCircularTransformation
        set(disableCircularTransformation) {
            if (mDisableCircularTransformation == disableCircularTransformation) {
                return
            }
            mDisableCircularTransformation = disableCircularTransformation
            initializeBitmap()
        }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }
        mColorFilter = cf
        applyColorFilter()
        invalidate()
    }

    override fun getColorFilter(): ColorFilter {
        return mColorFilter!!
    }

    private fun applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.colorFilter = mColorFilter
        }
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap: Bitmap
            bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG)
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun initializeBitmap() {
        mBitmap = if (mDisableCircularTransformation) {
            null
        } else {
            getBitmapFromDrawable(drawable)
        }
        setup()
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }
        if (width == 0 && height == 0) {
            return
        }
        if (mBitmap == null) {
            invalidate()
            return
        }
        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBitmapPaint!!.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader
        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()
        mFillPaint.style = Paint.Style.FILL
        mFillPaint.isAntiAlias = true
        mFillPaint.color = mFillColor
        mBitmapHeight = mBitmap!!.height
        mBitmapWidth = mBitmap!!.width
        mBorderRect.set(calculateBounds())
        mBorderRadius = Math.min(
            (mBorderRect.height() - mBorderWidth) / 2.0f,
            (mBorderRect.width() - mBorderWidth) / 2.0f
        )
        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)
        applyColorFilter()
        updateShaderMatrix()
        invalidate()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val sideLength = Math.min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f
        mShaderMatrix.set(null)
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + mDrawableRect.left,
            (dy + 0.5f).toInt() + mDrawableRect.top
        )
        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    companion object {
        private val SCALE_TYPE = ScaleType.CENTER_CROP
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLORDRAWABLE_DIMENSION = 2
        private const val DEFAULT_BORDER_WIDTH = 0
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_FILL_COLOR = Color.TRANSPARENT
        private const val DEFAULT_BORDER_OVERLAY = false
        private val colors = intArrayOf(
            R.color.blue,
            R.color.orange,
            R.color.yellow,
            R.color.green,
            R.color.purple,
            R.color.blue_dark,
            R.color.green_light
        )
    }
}